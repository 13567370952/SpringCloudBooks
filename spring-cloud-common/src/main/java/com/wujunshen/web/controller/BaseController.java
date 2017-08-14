package com.wujunshen.web.controller;

import com.wujunshen.entity.User;
import com.wujunshen.exception.ResponseStatus;
import com.wujunshen.util.Constants;
import com.wujunshen.util.MD5Utils;
import com.wujunshen.web.vo.response.BaseResponse;
import com.wujunshen.web.vo.security.Audience;
import com.wujunshen.web.vo.security.LoginParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.text.MessageFormat;
import java.util.List;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2017-2-22 <br>
 * Time:11:04 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@Slf4j
public class BaseController {
    public BaseResponse getValidatedResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            BaseResponse baseResponse = new BaseResponse();

            List<FieldError> errors = bindingResult.getFieldErrors();
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError error : errors) {
                stringBuilder.append(error.getField()).append(":").append(error.getDefaultMessage()).append(" ,");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);

            String formattedMessage = MessageFormat.format(ResponseStatus.PARAMETER_VALIDATION.getMessage(), stringBuilder);
            baseResponse.setCode(ResponseStatus.PARAMETER_VALIDATION.getCode());
            baseResponse.setMessage(formattedMessage);
            baseResponse.setData(Constants.NULL_DATA);
            return baseResponse;
        }
        return null;
    }

    /**
     * 验证clientID是否一致
     *
     * @param loginParameter
     * @param audience
     * @return
     */
    public BaseResponse isValidClientID(LoginParameter loginParameter, Audience audience) {
        if (loginParameter.getClientId().compareTo(audience.getClientId()) != 0) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setCode(ResponseStatus.INVALID_CLIENT_ID.getCode());
            baseResponse.setMessage(ResponseStatus.INVALID_CLIENT_ID.getMessage());
            baseResponse.setData(Constants.NULL_DATA);
            return baseResponse;
        }

        return null;
    }

    /**
     * 验证用户名是否存在
     *
     * @param user
     * @return
     */
    public BaseResponse isValidUserName(User user) {
        if (user == null) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setCode(ResponseStatus.INVALID_USER_NAME.getCode());
            baseResponse.setMessage(ResponseStatus.INVALID_USER_NAME.getMessage());
            baseResponse.setData(Constants.NULL_DATA);

            return baseResponse;
        }

        return null;
    }

    /**
     * 验证密码是否正确
     *
     * @param loginParameter
     * @param user
     * @return
     */
    public BaseResponse isValidPassword(LoginParameter loginParameter, User user) {
        String md5Password = MD5Utils.getMD5(loginParameter.getPassword() + user.getSalt());
        log.info("md5Password is: {}", md5Password);
        if (md5Password.compareTo(user.getPassword()) != 0) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setCode(ResponseStatus.INVALID_PASSWORD.getCode());
            baseResponse.setMessage(ResponseStatus.INVALID_PASSWORD.getMessage());
            baseResponse.setData(Constants.NULL_DATA);
            return baseResponse;
        }

        return null;
    }
}
