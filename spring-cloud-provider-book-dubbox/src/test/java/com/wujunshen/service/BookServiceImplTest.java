package com.wujunshen.service;

import com.wujunshen.entity.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2016-10-28 <br>
 * Time:16:17 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceImplTest {
    @Autowired
    private BookService bookService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void saveBook() throws Exception {

    }

    @Test
    public void getBook() throws Exception {

    }

    @Test
    public void getBooks() throws Exception {
        List<Book> books = bookService.getBooks();
        System.out.println("books size is: " + books.size());
    }

    @Test
    public void updateBook() throws Exception {

    }

    @Test
    public void deleteBook() throws Exception {

    }

}