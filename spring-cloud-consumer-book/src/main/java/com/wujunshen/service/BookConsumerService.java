package com.wujunshen.service;

import com.wujunshen.vo.security.LoginParameter;
import com.wujunshen.vo.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2016-10-24 <br>
 * Time:14:36 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@FeignClient(name = "api-gateway", fallback = BookConsumerService.HystrixClientFallback.class)
public interface BookConsumerService {
    Logger LOGGER = LoggerFactory.getLogger(BookConsumerService.class);

    @RequestMapping(value = "/api-gateway/v1/api/books/{bookId}")
    BaseResponse getBook(@RequestHeader("Authorization") String authorizationToken, @RequestParam("bookId") Integer bookId);

    @RequestMapping(value = "/oauth/token", method = RequestMethod.POST)
    BaseResponse getToken(@RequestBody LoginParameter loginParameter);

    @Component
    class HystrixClientFallback implements BookConsumerService {
        private static final Logger LOGGER = LoggerFactory.getLogger
                (HystrixClientFallback.class);

        /**
         * hystrix fallback方法
         *
         * @param bookId bookId
         * @return 默认的用户
         */
        @Override
        public BaseResponse getBook(String authorizationToken, Integer bookId) {
            HystrixClientFallback.LOGGER.info("异常发生，进入fallback方法，接收的参数：bookId = {}", bookId);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setCode(-99);
            baseResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return baseResponse;
        }

        @Override
        public BaseResponse getToken(LoginParameter loginParameter) {
            HystrixClientFallback.LOGGER.info("异常发生，进入fallback方法，接收的参数：userName = {}", loginParameter.getUserName());
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setCode(-99);
            baseResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return baseResponse;
        }
    }
}