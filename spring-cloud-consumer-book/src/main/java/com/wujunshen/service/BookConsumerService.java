package com.wujunshen.service;

import com.wujunshen.entity.Book;
import com.wujunshen.web.vo.response.BaseResponse;
import com.wujunshen.web.vo.security.LoginParameter;
import feign.hystrix.FallbackFactory;
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
@FeignClient(name = "api-gateway", fallback = BookConsumerService.FeignClientFallbackFactory.class)
public interface BookConsumerService {
    @PostMapping(value = "/api-gateway/v1/api/books")
    BaseResponse saveBook(@RequestHeader("Authorization") String authorizationToken, @RequestBody Book book);

    @GetMapping(value = "/api-gateway/v1/api/books")
    BaseResponse getBooks(@RequestHeader("Authorization") String authorizationToken);

    @GetMapping(value = "/api-gateway/v1/api/books/{bookId}")
    BaseResponse getBook(@RequestHeader("Authorization") String authorizationToken, @PathVariable("bookId") Integer bookId);

    @PutMapping(value = "/api-gateway/v1/api/books/{bookId}")
    BaseResponse updateBook(@RequestHeader("Authorization") String authorizationToken, @PathVariable("bookId") Integer bookId, @RequestBody Book book);

    @DeleteMapping(value = "/api-gateway/v1/api/books/{bookId}")
    BaseResponse deleteBook(@RequestHeader("Authorization") String authorizationToken, @PathVariable("bookId") Integer bookId);

    @PostMapping(value = "/oauth/token")
    BaseResponse getToken(@RequestBody LoginParameter loginParameter);

    @Component
    class FeignClientFallbackFactory implements FallbackFactory<BookConsumerService> {
        private static final Logger LOGGER = LoggerFactory.getLogger(FeignClientFallbackFactory.class);

        @Override
        public BookConsumerService create(Throwable cause) {
            return new BookConsumerService() {
                /**
                 * hystrix fallback方法
                 *
                 * @param authorizationToken 令牌值
                 * @param book               实体对象
                 * @return 响应消息
                 */
                @Override
                public BaseResponse saveBook(String authorizationToken, Book book) {
                    FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数：bookId = {}", book.getBookId());
                    FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数：bookName = {}", book.getBookName());
                    FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数：publisher = {}", book.getPublisher());
                    return initFallBackResponse();
                }

                /**
                 * hystrix fallback方法
                 *
                 * @param authorizationToken 令牌值
                 * @return 响应消息
                 */
                @Override
                public BaseResponse getBooks(String authorizationToken) {
                    FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法");
                    return initFallBackResponse();
                }

                /**
                 * hystrix fallback方法
                 *
                 * @param authorizationToken 令牌值
                 * @param bookId             实体对象id
                 * @return 响应消息
                 */
                @Override
                public BaseResponse getBook(String authorizationToken, Integer bookId) {
                    FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数：bookId = {}", bookId);
                    return initFallBackResponse();
                }

                /**
                 * hystrix fallback方法
                 *
                 * @param authorizationToken 令牌值
                 * @param bookId             实体对象id
                 * @param book               实体对象
                 * @return 响应消息
                 */
                @Override
                public BaseResponse updateBook(String authorizationToken, Integer bookId, Book book) {
                    FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数：bookId = {}", bookId);
                    FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数：bookName = {}", book.getBookName());
                    FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数：publisher = {}", book.getPublisher());
                    return initFallBackResponse();
                }

                /**
                 * hystrix fallback方法
                 *
                 * @param authorizationToken 令牌值
                 * @param bookId             实体对象id
                 * @return 响应消息
                 */
                @Override
                public BaseResponse deleteBook(String authorizationToken, Integer bookId) {
                    FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数：bookId = {}", bookId);
                    return initFallBackResponse();
                }

                /**
                 * hystrix fallback方法
                 *
                 * @param loginParameter 实体对象
                 * @return 响应消息
                 */
                @Override
                public BaseResponse getToken(LoginParameter loginParameter) {
                    FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数：clientId = {}", loginParameter.getClientId());
                    FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数：userName = {}", loginParameter.getUserName());
                    FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数：password = {}", loginParameter.getPassword());
                    return initFallBackResponse();
                }

                /**
                 * 初始化熔断返回的响应消息
                 *
                 * @return 响应消息
                 */
                private BaseResponse initFallBackResponse() {
                    BaseResponse baseResponse = new BaseResponse();
                    baseResponse.setCode(-99);
                    baseResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
                    return baseResponse;
                }
            };
        }
    }
}