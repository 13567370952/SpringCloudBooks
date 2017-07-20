package com.wujunshen.service;


import com.wujunshen.dao.BookMapper;
import com.wujunshen.entity.Book;
import com.wujunshen.entity.BookCriteria;
import com.wujunshen.entity.Books;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * User:Administrator(吴峻申)
 * Date:2015-11-30
 * Time:11:12
 * Mail:frank_wjs@hotmail.com
 */
@Component
public class BookServiceImpl implements BookService, ApplicationContextAware {
    private static final Logger LOGGER = Logger.getLogger(BookServiceImpl.class);
    private static ApplicationContext applicationContext = null;
    @Resource
    private BookMapper bookMapper;

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

    //获取applicationContext
    private static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (BookServiceImpl.applicationContext == null) {
            BookServiceImpl.applicationContext = applicationContext;
        }
    }

    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public int saveBook(final Book book) {
        bookMapper.insert(book);
        return book.getBookId();
    }

    public Book getBook(final int bookId) {
        return bookMapper.selectByPrimaryKey(bookId);
    }

    public Books getBooks() {
        BookCriteria bookCriteria = new BookCriteria();
        BookCriteria.Criteria criteria = bookCriteria.createCriteria();

        return new Books(bookMapper.selectByExample(bookCriteria));
    }

    public Book updateBook(int bookId, Book book) {
        BookCriteria bookCriteria = new BookCriteria();
        BookCriteria.Criteria criteria = bookCriteria.createCriteria();
        criteria.andBookIdEqualTo(bookId);
        bookMapper.updateByExample(book, bookCriteria);
        return book;
    }

    public int deleteBook(int bookId) {
        return bookMapper.deleteByPrimaryKey(bookId);
    }
}