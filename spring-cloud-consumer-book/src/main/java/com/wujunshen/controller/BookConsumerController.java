package com.wujunshen.controller;

import com.wujunshen.service.BookConsumerService;
import com.wujunshen.vo.response.BaseResponse;
import com.wujunshen.vo.security.LoginParameter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2016-10-24 <br>
 * Time:14:42 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@RestController
@Api(value = "/")
public class BookConsumerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookConsumerController.class);
    @Resource
    private BookConsumerService bookConsumerService;

    @GetMapping(value = "/consumer/{bookId:[0-9]*}")
    @ApiOperation(value = "获取某本书信息", httpMethod = "GET",
            notes = "成功返回某本书信息",
            response = BaseResponse.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BaseResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public BaseResponse getBook(@PathVariable Integer bookId) {
        //1.从redis取bearer，没有取到就调用getToken方法，有就执行下一步
        //2.放入header请求getBook方法，如果返回无效，则重新调用getToken方法，如果返回有效即继续


        //LoginParameter loginParameter = new LoginParameter();
        //loginParameter.setClientId("098f6bcd4621d373cade4e832627b4f6");
        //loginParameter.setUserName("wujunshen");
        //loginParameter.setPassword("wujunshen");
        LoginParameter loginParameter = new LoginParameter("098f6bcd4621d373cade4e832627b4f6", "wujunshen", "wujunshen");
        BaseResponse baseResponse = bookConsumerService.getToken(loginParameter);

        if (baseResponse.getCode() == 200) {
            Map tokenMap = (HashMap) baseResponse.getData();
            LOGGER.info("token_type is: {}", tokenMap.get("token_type"));
            LOGGER.info("access_token is: {}", tokenMap.get("access_token"));
            String authorizationToken = tokenMap.get("token_type") + " " + tokenMap.get("access_token");
            return bookConsumerService.getBook(authorizationToken, bookId);
        } else {
            return baseResponse;
        }
    }
}