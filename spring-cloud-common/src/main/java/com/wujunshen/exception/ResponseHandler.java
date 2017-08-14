package com.wujunshen.exception;

import com.wujunshen.util.Constants;
import com.wujunshen.web.vo.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 响应对象处理器
 */
@Component
@Slf4j
public class ResponseHandler {
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