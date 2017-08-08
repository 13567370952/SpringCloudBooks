package com.wujunshen.service.facade;


import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.wujunshen.entity.Book;
import com.wujunshen.web.vo.response.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.GetMapping;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


/**
 * User:Administrator(吴峻申)
 * Date:2015-12-2
 * Time:13:58
 * Mail:frank_wjs@hotmail.com
 */
@Path("/")
@Api(value = "/")
public interface BookRest4DubboService {

    @GetMapping("/instance-info")
    ServiceInstance showInfo();

    @Path("/api/books")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({ContentType.APPLICATION_JSON_UTF_8})
    @ApiOperation(value = "添加某本书籍", httpMethod = "POST",
            notes = "添加成功返回bookId",
            response = BaseResponse.class
    )
    BaseResponse saveBook(@ApiParam(value = "添加的某本书籍信息", required = true) final Book book);
    //BaseResponse saveBook(final Book book);

    @GET
    @Path("/api/books/{bookId : \\d+}")
    @Consumes({ContentType.APPLICATION_JSON_UTF_8})
    @Produces({ContentType.APPLICATION_JSON_UTF_8})
    @ApiOperation(value = "查询某本书籍", httpMethod = "GET",
            notes = "根据bookId，查询到某本书籍",
            response = BaseResponse.class
    )
    BaseResponse getBook(@ApiParam(value = "书籍ID", required = true) @PathParam("bookId") int bookId);
    //BaseResponse getBook(@PathParam("bookId") int bookId);

    @Path("/api/books")
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({ContentType.APPLICATION_JSON_UTF_8})
    @ApiOperation(value = "查询所有书籍", httpMethod = "GET",
            notes = "查询所有书籍",
            response = BaseResponse.class
    )
    BaseResponse getBooks();

    @Path("/api/books/{bookId:[0-9]*}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({ContentType.APPLICATION_JSON_UTF_8})
    @ApiOperation(value = "更新某本书籍", httpMethod = "PUT",
            notes = "更新的某本书籍信息",
            response = BaseResponse.class
    )
    BaseResponse updateBook(@ApiParam(value = "要更新的某本书籍ID", required = true) @PathParam("bookId") int bookId, @ApiParam(value = "要更新的某本书籍信息", required = true) Book book);
    //BaseResponse updateBook(@PathParam("bookId") int bookId, Book book);

    @Path("/api/books/{bookId:[0-9]*}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    @Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_PLAIN_UTF_8})
    @ApiOperation(value = "删除某本书籍信息", httpMethod = "DELETE",
            notes = "删除某本书籍信息",
            response = BaseResponse.class
    )
    BaseResponse deleteBook(@ApiParam(value = "要删除的某本书籍ID", required = true) @PathParam("bookId") int bookId);
    //BaseResponse deleteBook(@PathParam("bookId") int bookId);
}