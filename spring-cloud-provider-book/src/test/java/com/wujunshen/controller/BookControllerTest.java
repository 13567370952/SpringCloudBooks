package com.wujunshen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wujunshen.ProviderBookApplication;
import com.wujunshen.entity.Book;
import com.wujunshen.entity.Books;
import com.wujunshen.exception.ResultStatusCode;
import com.wujunshen.vo.BaseResultVo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2017/7/25 <br>
 * Time:下午9:55 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProviderBookApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class BookControllerTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void saveBook() throws Exception {
        String requestBody = "{\n" +
                "  \"bookName\": \"《大数据分析：数据驱动的企业绩效优化、过程管理和运营决策》\",\n" +
                "  \"publisher\": \"吴峻申\"\n" +
                "}";

        BaseResultVo actual = template.postForObject("/api/books", OBJECT_MAPPER.readValue(requestBody, Book.class), BaseResultVo.class);

        assertThat(actual.getCode(), equalTo(ResultStatusCode.OK.getCode()));
        assertThat(actual.getMessage(), equalTo(ResultStatusCode.OK.getMessage()));
    }

    @Test
    public void getBooks() throws Exception {
        String expected = "{\n" +
                "    \"bookList\": [\n" +
                "      {\n" +
                "        \"bookId\": 1,\n" +
                "        \"bookName\": \"《JAVA WEB整合开发实例精通:Struts+Hibernate+Spring》\",\n" +
                "        \"publisher\": \"吴峻申\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }";

        BaseResultVo actual = template.getForObject("/api/books", BaseResultVo.class, new HashMap<>());

        assertThat(actual.getCode(), equalTo(ResultStatusCode.OK.getCode()));
        assertThat(actual.getMessage(), equalTo(ResultStatusCode.OK.getMessage()));
        assertThat(OBJECT_MAPPER.writeValueAsString(actual.getData()),
                equalTo(OBJECT_MAPPER.writeValueAsString(OBJECT_MAPPER.readValue(expected, Books.class))));
    }

    @Test
    public void getBook() throws Exception {
        String expected = "{\n" +
                "    \"bookId\": 1,\n" +
                "    \"bookName\": \"《JAVA WEB整合开发实例精通:Struts+Hibernate+Spring》\",\n" +
                "    \"publisher\": \"吴峻申\"\n" +
                "  }";

        Map<String, String> multiValueMap = new HashMap<>();
        multiValueMap.put("bookId", "1");//传值，但要在url上配置相应的参数

        BaseResultVo actual = template.getForObject("/api/books/{bookId}", BaseResultVo.class, multiValueMap);

        assertThat(actual.getCode(), equalTo(ResultStatusCode.OK.getCode()));
        assertThat(actual.getMessage(), equalTo(ResultStatusCode.OK.getMessage()));
        assertThat(OBJECT_MAPPER.writeValueAsString(actual.getData()),
                equalTo(OBJECT_MAPPER.writeValueAsString(OBJECT_MAPPER.readValue(expected, Book.class))));
    }

    @Test
    public void updateBook() throws Exception {
        String editString = "{\n" +
                "        \"bookName\": \"《大数据分析：数据驱动的企业绩效优化、过程管理和运营决策》\",\n" +
                "        \"publisher\": \"吴峻申shi\"\n" +
                "      }";

        Book editedBook = OBJECT_MAPPER.readValue(editString, Book.class);

        Map<String, Object> multiValueMap = new HashMap<>();
        multiValueMap.put("book", editedBook);
        HttpEntity<Map<String, Object>> formEntity = new HttpEntity<>(multiValueMap, null);

        Object[] uriVariables = {11};

        String expected = "Update book id=" + uriVariables[0];
        BaseResultVo actual = template.exchange("/api/books/{bookId}", HttpMethod.PUT, formEntity, BaseResultVo.class, uriVariables).getBody();

        assertThat(actual.getCode(), equalTo(ResultStatusCode.OK.getCode()));
        assertThat(actual.getMessage(), equalTo(ResultStatusCode.OK.getMessage()));
        assertThat(actual.getData(), equalTo(expected));
    }

    @Test
    public void deleteBook() throws Exception {
        Object[] uriVariables = {11};
        BaseResultVo actual = template.exchange("/api/books/{bookId}", HttpMethod.DELETE, null, BaseResultVo.class, uriVariables).getBody();

        String expected = "Deleted book id=" + uriVariables[0];

        assertThat(actual.getCode(), equalTo(ResultStatusCode.OK.getCode()));
        assertThat(actual.getMessage(), equalTo(ResultStatusCode.OK.getMessage()));
        assertThat(actual.getData(), equalTo(expected));
    }
}