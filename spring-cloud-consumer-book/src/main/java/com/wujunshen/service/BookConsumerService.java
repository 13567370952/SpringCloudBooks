package com.wujunshen.service;

import com.wujunshen.entity.Book;
import com.wujunshen.exception.ResponseStatus;
import com.wujunshen.util.Constants;
import com.wujunshen.web.vo.response.BaseResponse;
import com.wujunshen.web.vo.security.LoginParameter;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
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
    @Slf4j
    class FeignClientFallbackFactory implements FallbackFactory<BookConsumerService> {

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
                    log.info(Constants.FALL_BACK + "bookId = {}", book.getBookId());
                    log.info(Constants.FALL_BACK + "bookName = {}", book.getBookName());
                    log.info(Constants.FALL_BACK + "publisher = {}", book.getPublisher());
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
                    log.info(Constants.FALL_BACK);
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
                    log.info(Constants.FALL_BACK + "bookId = {}", bookId);
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
                    log.info(Constants.FALL_BACK + "bookId = {}", bookId);
                    log.info(Constants.FALL_BACK + "bookName = {}", book.getBookName());
                    log.info(Constants.FALL_BACK + "publisher = {}", book.getPublisher());
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
                    log.info(Constants.FALL_BACK + "bookId = {}", bookId);
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
                    log.info(Constants.FALL_BACK + "clientId = {}", loginParameter.getClientId());
                    log.info(Constants.FALL_BACK + "userName = {}", loginParameter.getUserName());
                    log.info(Constants.FALL_BACK + "password = {}", loginParameter.getPassword());
                    return initFallBackResponse();
                }

                /**
                 * 初始化熔断返回的响应消息
                 *
                 * @return 响应消息
                 */
                private BaseResponse initFallBackResponse() {
                    BaseResponse baseResponse = new BaseResponse();
                    baseResponse.setCode(ResponseStatus.FALL_BACK.getCode());
                    baseResponse.setMessage(ResponseStatus.FALL_BACK.getMessage());
                    return baseResponse;
                }
            };
        }
    }
}