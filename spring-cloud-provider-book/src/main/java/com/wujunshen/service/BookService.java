package com.wujunshen.service;


import com.wujunshen.dao.BookMapper;
import com.wujunshen.entity.Book;
import com.wujunshen.entity.BookCriteria;
import com.wujunshen.entity.Books;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * User:Administrator(吴峻申)
 * Date:2015-11-30
 * Time:11:03
 * Mail:frank_wjs@hotmail.com
 */
@Service
public class BookService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);
    @Resource
    private BookMapper bookMapper;

    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public int saveBook(final Book book) {
        return bookMapper.insert(book);
    }

    public Books getBooks() {
        BookCriteria bookCriteria = new BookCriteria();

        return new Books(bookMapper.selectByExample(bookCriteria));
    }

    public int updateBook(Integer bookId, Book book) {
        BookCriteria bookCriteria = new BookCriteria();
        BookCriteria.Criteria criteria = bookCriteria.createCriteria();
        criteria.andBookIdEqualTo(bookId);
       return bookMapper.updateByExample(book, bookCriteria);
    }

    public int deleteBook(Integer bookId) {
        return bookMapper.deleteByPrimaryKey(bookId);
    }

    public Book getBook(Integer bookId) {
        return bookMapper.selectByPrimaryKey(bookId);
    }
}