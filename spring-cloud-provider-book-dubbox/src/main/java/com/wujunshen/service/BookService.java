package com.wujunshen.service;


import com.wujunshen.entity.Book;

import java.util.List;

/**
 * User:Administrator(吴峻申)
 * Date:2015-11-30
 * Time:11:12
 * Mail:frank_wjs@hotmail.com
 */
public interface BookService {
    int saveBook(final Book book);

    Book getBook(final int bookId);

    List<Book> getBooks();

    Book updateBook(int bookId, Book book);

    int deleteBook(int bookId);
}