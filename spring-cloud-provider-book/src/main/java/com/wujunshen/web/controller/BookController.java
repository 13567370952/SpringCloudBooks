package com.wujunshen.web.controller;

import com.wujunshen.entity.Book;
import com.wujunshen.exception.ResponseStatus;
import com.wujunshen.service.BookService;
import com.wujunshen.web.vo.response.BaseResponse;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2016-10-17 <br>
 * Time:17:21 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@RestController
@Api(value = "/")
@Slf4j
public class BookController extends BaseController {
    @Resource
    private BookService bookService;

    /**
     * @param book 传入的book对象实例
     * @return 成功或失败信息，json格式封装
     */
    @PostMapping(value = "/api/books", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
    public BaseResponse saveBook(@Valid @ApiParam(value = "添加的某本书籍信息", required = true) @RequestBody Book book, BindingResult bindingResult) {
        BaseResponse baseResponse = getValidatedResult(bindingResult);
        if (baseResponse != null) {
            return baseResponse;
        }

        baseResponse = new BaseResponse();
        bookService.saveBook(book);
        if (book.getBookId() != 0) {
            baseResponse.setCode(ResponseStatus.OK.getCode());
            baseResponse.setMessage(ResponseStatus.OK.getMessage());
            baseResponse.setData(book.getBookId());
        } else {
            baseResponse.setCode(ResponseStatus.DATA_CREATE_ERROR.getCode());
            baseResponse.setMessage(ResponseStatus.DATA_CREATE_ERROR.getMessage());
        }

        return baseResponse;
    }

    /**
     * @return 成功或失败信息，json格式封装
     */
    @GetMapping(value = "/api/books", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
        BaseResponse baseResponse = new BaseResponse();
        List<Book> books = bookService.getBooks();
        if ((books != null) && (!books.isEmpty())) {
            baseResponse.setData(books);
            baseResponse.setCode(ResponseStatus.OK.getCode());
            baseResponse.setMessage(ResponseStatus.OK.getMessage());
        } else {
            baseResponse.setCode(ResponseStatus.DATA_REQUERY_ERROR.getCode());
            baseResponse.setData("Query books failed");
            baseResponse.setMessage(ResponseStatus.DATA_REQUERY_ERROR.getMessage());
        }

        return baseResponse;
    }

    /**
     * @param bookId 传入的bookId
     * @return 成功或失败信息，json格式封装
     */
    @GetMapping(value = "/api/books/{bookId:[0-9]*}")
    @ApiOperation(value = "查询某本书籍", httpMethod = "GET",
            notes = "根据bookId，查询到某本书籍",
            response = BaseResponse.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BaseResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public BaseResponse getBook(@ApiParam(value = "书籍ID", required = true) @PathVariable("bookId") Integer bookId) {
        log.info("请求参数bookId值：{}", bookId);
        Book book = bookService.getBook(bookId);
        BaseResponse baseResponse = new BaseResponse();
        if (book != null) {
            log.info("查询到书籍ID为{}的书籍", bookId);
            baseResponse.setData(book);
            baseResponse.setCode(ResponseStatus.OK.getCode());
            baseResponse.setMessage(ResponseStatus.OK.getMessage());
        } else {
            log.info("没有查询到书籍ID为{}的书籍", bookId);
            baseResponse.setCode(ResponseStatus.DATA_REQUERY_ERROR.getCode());
            baseResponse.setData("Query book failed id=" + bookId);
            baseResponse.setMessage(ResponseStatus.DATA_REQUERY_ERROR.getMessage());
        }

        return baseResponse;
    }

    @PutMapping(value = "/api/books/{bookId:[0-9]*}")
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
    public BaseResponse updateBook(@NotNull @ApiParam(value = "要更新的某本书籍ID", required = true) @PathVariable("bookId") Integer bookId, @Valid @ApiParam(value = "要更新的某本书籍信息", required = true) @RequestBody Book book, BindingResult bindingResult) {
        log.info("请求参数bookId值：{}", bookId);
        BaseResponse baseResponse = getValidatedResult(bindingResult);
        if (baseResponse != null) {
            return baseResponse;
        }

        baseResponse = new BaseResponse();
        if (bookId == null && book == null) {
            baseResponse.setCode(ResponseStatus.DATA_INPUT_ERROR.getCode());
            baseResponse.setMessage(ResponseStatus.DATA_INPUT_ERROR.getMessage());
            return baseResponse;
        }

        if (bookService.getBook(bookId) == null) {
            baseResponse.setCode(ResponseStatus.DATA_REQUERY_ERROR.getCode());
            baseResponse.setData("book id={}" + bookId + " not existed");
            baseResponse.setMessage(ResponseStatus.DATA_REQUERY_ERROR.getMessage());
            return baseResponse;
        }

        if (bookService.updateBook(bookId, book) != 1) {
            baseResponse.setData("Update book failed id=" + book.getBookId());
            baseResponse.setCode(ResponseStatus.DATA_UPDATED_ERROR.getCode());
            baseResponse.setMessage(ResponseStatus.DATA_UPDATED_ERROR.getMessage());
        } else {
            baseResponse.setData("Update book id=" + bookId);
            baseResponse.setCode(ResponseStatus.OK.getCode());
            baseResponse.setMessage(ResponseStatus.OK.getMessage());
        }

        return baseResponse;
    }

    @DeleteMapping(value = "/api/books/{bookId:[0-9]*}")
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
        BaseResponse baseResponse = new BaseResponse();
        if (bookService.deleteBook(bookId) != 1) {
            baseResponse.setData("Deleted book failed id=" + bookId);
            baseResponse.setCode(ResponseStatus.DATA_DELETED_ERROR.getCode());
            baseResponse.setMessage(ResponseStatus.DATA_DELETED_ERROR.getMessage());
        } else {
            baseResponse.setData("Deleted book id=" + bookId);
            baseResponse.setCode(ResponseStatus.OK.getCode());
            baseResponse.setMessage(ResponseStatus.OK.getMessage());
        }
        return baseResponse;
    }
}