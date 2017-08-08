package com.wujunshen.service.facade;

import com.alibaba.dubbo.config.annotation.Service;
import com.wujunshen.entity.Book;
import com.wujunshen.exception.ResponseStatus;
import com.wujunshen.service.BookService;
import com.wujunshen.web.vo.response.BaseResponse;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * User:Administrator(吴峻申)
 * Date:2015-12-2
 * Time:14:02
 * Mail:frank_wjs@hotmail.com
 */
@Service
//@EnableAutoConfiguration
@Component
public class BookRest4DubboServiceImpl implements BookRest4DubboService, ApplicationContextAware {
    private static final Logger LOGGER = Logger.getLogger(BookRest4DubboServiceImpl.class);
    private static ApplicationContext applicationContext = null;
    @Resource
    private BookService bookService;
    @Autowired
    private DiscoveryClient discoveryClient;

    //获取applicationContext
    private static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (BookRest4DubboServiceImpl.applicationContext == null) {
            BookRest4DubboServiceImpl.applicationContext = applicationContext;
        }
    }

    //通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    @Override
    public ServiceInstance showInfo() {
        return this.discoveryClient.getLocalServiceInstance();
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public BaseResponse saveBook(final Book book) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(bookService.saveBook(book));
        baseResponse.setMessage(ResponseStatus.OK.getMessage());
        baseResponse.setCode(ResponseStatus.OK.getCode());
        return baseResponse;
    }

    public BaseResponse getBooks() {
        final List<Book> books = bookService.getBooks();
        BaseResponse baseResultVo = new BaseResponse();
        if (books != null && books.size() != 0) {
            baseResultVo.setData(books);
            baseResultVo.setMessage("成功");
        } else {
            baseResultVo.setCode(99);
            baseResultVo.setData("");
            baseResultVo.setMessage("没有查询到任何书籍");
        }

        return baseResultVo;
    }

    public BaseResponse getBook(int bookId) {
        final Book book = bookService.getBook(bookId);
        BaseResponse baseResponse = new BaseResponse();
        if (book != null) {
            baseResponse.setData(book);
            baseResponse.setMessage("成功");
        } else {
            baseResponse.setCode(99);
            baseResponse.setData("");
            baseResponse.setMessage("没有查询到书籍ID为" + bookId + "书籍");
        }

        return baseResponse;
    }

    @ApiOperation(value = "查询所有书籍", httpMethod = "GET", notes = "查询所有书籍", response = BaseResponse.class)
    public BaseResponse updateBook(int bookId, final Book book) {
        BaseResponse baseResponse = new BaseResponse();
        if (book == null) {
            baseResponse.setCode(99);
            baseResponse.setData("");
            baseResponse.setMessage("要修改的书籍ID为" + bookId + "书籍不存在");
            return baseResponse;
        }

        Book updatedBook = bookService.updateBook(bookId, book);

        if (updatedBook != null) {
            baseResponse.setData(updatedBook);
            baseResponse.setMessage("成功");
        } else {
            baseResponse.setCode(99);
            baseResponse.setData("");
            baseResponse.setMessage("修改书籍ID为" + bookId + "书籍失败");
        }

        return baseResponse;
    }

    public BaseResponse deleteBook(int bookId) {
        BaseResponse baseResponse = new BaseResponse();
        if (bookService.deleteBook(bookId) == 1) {
            baseResponse.setData("Deleted book id=" + bookId);
            baseResponse.setMessage("成功");
        } else {
            baseResponse.setCode(99);
            baseResponse.setData("Deleted book failed id=" + bookId);
            baseResponse.setMessage("删除书籍ID为" + bookId + "的书籍失败");
        }
        return baseResponse;
    }
}