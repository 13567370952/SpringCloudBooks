package com.wujunshen.exception;

import com.wujunshen.util.Constants;
import com.wujunshen.vo.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 响应对象处理器
 */
@Component
public class ResponseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseHandler.class);

    public BaseResponse getBaseResponse(Object obj, ResponseStatus responseStatus) {
        BaseResponse baseResponse = getBaseResponse(responseStatus);
        baseResponse.setData(obj == null ? Constants.NULL_DATA : obj);

        return baseResponse;
    }

    public BaseResponse getBaseResponse(ResponseStatus responseStatus) {
        BaseResponse baseResponse = new BaseResponse();
        if (responseStatus != null) {
            baseResponse.setMessage(responseStatus.getMessage());
            baseResponse.setCode(responseStatus.getCode());
            baseResponse.setData(Constants.NULL_DATA);
        }

        return baseResponse;
    }
}