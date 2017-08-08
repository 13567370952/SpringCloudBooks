package com.wujunshen.web.controller;

import com.wujunshen.entity.Book;
import com.wujunshen.exception.ResponseStatus;
import com.wujunshen.service.BookConsumerService;
import com.wujunshen.util.Constants;
import com.wujunshen.web.vo.response.BaseResponse;
import com.wujunshen.web.vo.security.LoginParameter;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2016-10-24 <br>
 * Time:14:42 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@RestController
@Api(value = "/")
public class BookConsumerController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookConsumerController.class);
    @Resource
    private BookConsumerService bookConsumerService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate<String, LoginParameter> redisTemplate;

    /**
     * @param book 传入的book对象实例
     * @return 成功或失败信息，json格式封装
     */
    @PostMapping(value = "/consumer/books", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "添加某本书籍", httpMethod = "POST",
            notes = "添加成功返回bookId",
            response = BaseResponse.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BaseResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public BaseResponse saveBook(@Validated @ApiParam(value = "添加的某本书籍信息", required = true) @RequestBody Book book) {
        String jwtToken = stringRedisTemplate.opsForValue().get(Constants.BEARER);

        ValueOperations<String, LoginParameter> operations = redisTemplate.opsForValue();
        LoginParameter loginParameter = operations.get(Constants.LOGIN_PARAMETER);

        if (!ObjectUtils.isEmpty(jwtToken)) {
            BaseResponse baseResponse = bookConsumerService.saveBook(Constants.BEARER + " " + jwtToken, book);

            if (baseResponse.getCode() == ResponseStatus.OK.getCode()) {
                return baseResponse;
            }
        }
        return saveBook(book, loginParameter);
    }

    /**
     * @return 成功或失败信息，json格式封装
     */
    @GetMapping(value = "/consumer/books", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "查询所有书籍", httpMethod = "GET",
            notes = "查询所有书籍",
            response = BaseResponse.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BaseResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public BaseResponse getBooks() {
        String jwtToken = stringRedisTemplate.opsForValue().get(Constants.BEARER);

        ValueOperations<String, LoginParameter> operations = redisTemplate.opsForValue();
        LoginParameter loginParameter = operations.get(Constants.LOGIN_PARAMETER);

        if (!ObjectUtils.isEmpty(jwtToken)) {
            BaseResponse baseResponse = bookConsumerService.getBooks(Constants.BEARER + " " + jwtToken);

            if (baseResponse.getCode() == ResponseStatus.OK.getCode()) {
                return baseResponse;
            }
        }
        return getBooks(loginParameter);
    }

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
    public BaseResponse getBook(@ApiParam(value = "书籍ID", required = true) @PathVariable Integer bookId) {
        //1.从redis取token，没有取到token就调用getBaseResponse方法，有就执行getBook方法
        //2.执行getBook方法后，如果发现token无效，则也调用getBaseResponse方法，有效就返回getBook方法的响应消息
        String jwtToken = stringRedisTemplate.opsForValue().get(Constants.BEARER);

        ValueOperations<String, LoginParameter> operations = redisTemplate.opsForValue();
        LoginParameter loginParameter = operations.get(Constants.LOGIN_PARAMETER);

        if (!ObjectUtils.isEmpty(jwtToken)) {
            BaseResponse baseResponse = bookConsumerService.getBook(Constants.BEARER + " " + jwtToken, bookId);

            if (baseResponse.getCode() == ResponseStatus.OK.getCode()) {
                return baseResponse;
            }
        }
        return getBook(bookId, loginParameter);
    }

    @PutMapping(value = "/consumer/books/{bookId:[0-9]*}")
    @ApiOperation(value = "更新某本书籍", httpMethod = "PUT",
            notes = "更新的某本书籍信息",
            response = BaseResponse.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BaseResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public BaseResponse updateBook(@ApiParam(value = "要更新的某本书籍ID", required = true) @PathVariable("bookId") Integer bookId, @ApiParam(value = "要更新的某本书籍信息", required = true) @RequestBody Book book) {
        String jwtToken = stringRedisTemplate.opsForValue().get(Constants.BEARER);

        ValueOperations<String, LoginParameter> operations = redisTemplate.opsForValue();
        LoginParameter loginParameter = operations.get(Constants.LOGIN_PARAMETER);

        if (!ObjectUtils.isEmpty(jwtToken)) {
            BaseResponse baseResponse = bookConsumerService.updateBook(Constants.BEARER + " " + jwtToken, bookId, book);

            if (baseResponse.getCode() == ResponseStatus.OK.getCode()) {
                return baseResponse;
            }
        }
        return updateBook(bookId, book, loginParameter);
    }

    @DeleteMapping(value = "/consumer/books/{bookId:[0-9]*}")
    @ApiOperation(value = "删除某本书籍信息", httpMethod = "DELETE",
            notes = "删除某本书籍信息",
            response = BaseResponse.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BaseResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public BaseResponse deleteBook(@ApiParam(value = "要删除的某本书籍ID", required = true) @PathVariable("bookId") Integer bookId) {
        String jwtToken = stringRedisTemplate.opsForValue().get(Constants.BEARER);

        ValueOperations<String, LoginParameter> operations = redisTemplate.opsForValue();
        LoginParameter loginParameter = operations.get(Constants.LOGIN_PARAMETER);

        if (!ObjectUtils.isEmpty(jwtToken)) {
            BaseResponse baseResponse = bookConsumerService.deleteBook(Constants.BEARER + " " + jwtToken, bookId);

            if (baseResponse.getCode() == ResponseStatus.OK.getCode()) {
                return baseResponse;
            }
        }
        return deleteBook(bookId, loginParameter);
    }


    /**
     * 调用获取token的方法，成功就直接请求getBook方法，否则就返回出错的响应消息
     *
     * @param bookId
     * @param loginParameter
     * @return
     */
    private BaseResponse getBook(Integer bookId, LoginParameter loginParameter) {
        BaseResponse baseResponse = bookConsumerService.getToken(loginParameter);

        if (baseResponse.getCode() == ResponseStatus.OK.getCode()) {
            Map tokenMap = (HashMap) baseResponse.getData();
            LOGGER.info("token_type is: {}", tokenMap.get("token_type"));
            LOGGER.info("access_token is: {}", tokenMap.get("access_token"));
            LOGGER.info("expires_in is: {}", tokenMap.get("expires_in"));

            stringRedisTemplate.opsForValue().set(Constants.BEARER, (String) tokenMap.get("access_token"), ((Integer) tokenMap.get("expires_in")).longValue(), TimeUnit.SECONDS);
            return bookConsumerService.getBook(Constants.BEARER + " " + tokenMap.get("access_token"), bookId);
        }

        return baseResponse;
    }

    /**
     * 调用获取token的方法，成功就直接请求getBook方法，否则就返回出错的响应消息
     *
     * @param book
     * @param loginParameter
     * @return
     */
    private BaseResponse saveBook(Book book, LoginParameter loginParameter) {
        BaseResponse baseResponse = bookConsumerService.getToken(loginParameter);

        if (baseResponse.getCode() == ResponseStatus.OK.getCode()) {
            Map tokenMap = (HashMap) baseResponse.getData();
            LOGGER.info("token_type is: {}", tokenMap.get("token_type"));
            LOGGER.info("access_token is: {}", tokenMap.get("access_token"));
            LOGGER.info("expires_in is: {}", tokenMap.get("expires_in"));

            stringRedisTemplate.opsForValue().set(Constants.BEARER, (String) tokenMap.get("access_token"), ((Integer) tokenMap.get("expires_in")).longValue(), TimeUnit.SECONDS);
            return bookConsumerService.saveBook(Constants.BEARER + " " + tokenMap.get("access_token"), book);
        }

        return baseResponse;
    }

    /**
     * 调用获取token的方法，成功就直接请求getBook方法，否则就返回出错的响应消息
     *
     * @param loginParameter
     * @return
     */
    private BaseResponse getBooks(LoginParameter loginParameter) {
        BaseResponse baseResponse = bookConsumerService.getToken(loginParameter);

        if (baseResponse.getCode() == ResponseStatus.OK.getCode()) {
            Map tokenMap = (HashMap) baseResponse.getData();
            LOGGER.info("token_type is: {}", tokenMap.get("token_type"));
            LOGGER.info("access_token is: {}", tokenMap.get("access_token"));
            LOGGER.info("expires_in is: {}", tokenMap.get("expires_in"));

            stringRedisTemplate.opsForValue().set(Constants.BEARER, (String) tokenMap.get("access_token"), ((Integer) tokenMap.get("expires_in")).longValue(), TimeUnit.SECONDS);
            return bookConsumerService.getBooks(Constants.BEARER + " " + tokenMap.get("access_token"));
        }

        return baseResponse;
    }

    /**
     * 调用获取token的方法，成功就直接请求getBook方法，否则就返回出错的响应消息
     *
     * @param loginParameter
     * @return
     */
    private BaseResponse updateBook(Integer bookId, Book book, LoginParameter loginParameter) {
        BaseResponse baseResponse = bookConsumerService.getToken(loginParameter);

        if (baseResponse.getCode() == ResponseStatus.OK.getCode()) {
            Map tokenMap = (HashMap) baseResponse.getData();
            LOGGER.info("token_type is: {}", tokenMap.get("token_type"));
            LOGGER.info("access_token is: {}", tokenMap.get("access_token"));
            LOGGER.info("expires_in is: {}", tokenMap.get("expires_in"));

            stringRedisTemplate.opsForValue().set(Constants.BEARER, (String) tokenMap.get("access_token"), ((Integer) tokenMap.get("expires_in")).longValue(), TimeUnit.SECONDS);
            return bookConsumerService.updateBook(Constants.BEARER + " " + tokenMap.get("access_token"), bookId, book);
        }

        return baseResponse;
    }

    /**
     * 调用获取token的方法，成功就直接请求getBook方法，否则就返回出错的响应消息
     *
     * @param loginParameter
     * @return
     */
    private BaseResponse deleteBook(Integer bookId, LoginParameter loginParameter) {
        BaseResponse baseResponse = bookConsumerService.getToken(loginParameter);

        if (baseResponse.getCode() == ResponseStatus.OK.getCode()) {
            Map tokenMap = (HashMap) baseResponse.getData();
            LOGGER.info("token_type is: {}", tokenMap.get("token_type"));
            LOGGER.info("access_token is: {}", tokenMap.get("access_token"));
            LOGGER.info("expires_in is: {}", tokenMap.get("expires_in"));

            stringRedisTemplate.opsForValue().set(Constants.BEARER, (String) tokenMap.get("access_token"), ((Integer) tokenMap.get("expires_in")).longValue(), TimeUnit.SECONDS);
            return bookConsumerService.deleteBook(Constants.BEARER + " " + tokenMap.get("access_token"), bookId);
        }

        return baseResponse;
    }
}