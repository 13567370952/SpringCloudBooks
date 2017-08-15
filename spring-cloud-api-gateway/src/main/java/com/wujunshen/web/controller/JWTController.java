package com.wujunshen.web.controller;

import com.wujunshen.entity.User;
import com.wujunshen.exception.ResponseStatus;
import com.wujunshen.service.UserService;
import com.wujunshen.util.Constants;
import com.wujunshen.util.JwtUtils;
import com.wujunshen.web.vo.response.BaseResponse;
import com.wujunshen.web.vo.security.AccessToken;
import com.wujunshen.web.vo.security.Audience;
import com.wujunshen.web.vo.security.LoginParameter;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2016-11-3 <br>
 * Time:16:47 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@RestController
@Api(value = "/")
@Slf4j
public class JWTController extends BaseController {
    @Resource
    private UserService userService;

    @Resource
    private Audience audience;

    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "获取access_token", httpMethod = "POST",
            notes = "成功返回access_token",
            response = BaseResponse.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BaseResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public BaseResponse getAccessToken(@ApiParam(value = "登录信息", required = true) @RequestBody @Valid LoginParameter loginParameter, BindingResult bindingResult) {
        BaseResponse baseResponse;

        baseResponse = getValidatedResult(bindingResult);
        if (baseResponse != null) {
            return baseResponse;
        }

        baseResponse = isValidClientID(loginParameter, audience);
        if (baseResponse != null) {
            return baseResponse;
        }

        User user = userService.findUserInfoByName(loginParameter.getUserName());
        baseResponse = isValidUserName(user);
        if (baseResponse != null) {
            return baseResponse;
        }

        baseResponse = isValidPassword(loginParameter, user);
        if (baseResponse != null) {
            return baseResponse;
        }

        //拼装accessToken
        String accessToken = JwtUtils.createJWT(loginParameter, user, audience);

        //返回accessToken
        AccessToken accessTokenEntity = new AccessToken();
        accessTokenEntity.setToken(accessToken);
        accessTokenEntity.setExpiresIn(audience.getExpiresSecond());
        accessTokenEntity.setTokenType(Constants.BEARER);

        baseResponse = new BaseResponse();
        baseResponse.setCode(ResponseStatus.OK.getCode());
        baseResponse.setMessage(ResponseStatus.OK.getMessage());
        baseResponse.setData(accessTokenEntity);
        return baseResponse;
    }
}