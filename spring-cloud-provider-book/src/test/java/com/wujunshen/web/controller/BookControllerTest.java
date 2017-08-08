package com.wujunshen.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wujunshen.ProviderBookApplication;
import com.wujunshen.entity.Book;
import com.wujunshen.exception.ResponseStatus;
import com.wujunshen.web.vo.response.BaseResponse;
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
import java.util.List;
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
    private int addFlag = 0;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void saveAndDeleteBook() throws Exception {
        saveBook();
        deleteBook();
    }

    private void saveBook() throws Exception {
        String requestBody = "{\n" +
                "  \"bookName\": \"《大数据分析：数据驱动的企业绩效优化、过程管理和运营决策》\",\n" +
                "  \"publisher\": \"吴峻申\"\n" +
                "}";

        BaseResponse actual = template.postForObject("/api/books", OBJECT_MAPPER.readValue(requestBody, Book.class), BaseResponse.class);
        addFlag = (int) actual.getData();
        assertThat(actual.getCode(), equalTo(ResponseStatus.OK.getCode()));
        assertThat(actual.getMessage(), equalTo(ResponseStatus.OK.getMessage()));
    }

    private void deleteBook() throws Exception {
        Object[] uriVariables = {addFlag};
        BaseResponse actual = template.exchange("/api/books/{bookId}", HttpMethod.DELETE, null, BaseResponse.class, uriVariables).getBody();

        String expected = "Deleted book id=" + uriVariables[0];

        assertThat(actual.getCode(), equalTo(ResponseStatus.OK.getCode()));
        assertThat(actual.getMessage(), equalTo(ResponseStatus.OK.getMessage()));
        assertThat(actual.getData(), equalTo(expected));
    }

    @Test
    public void getBooks() throws Exception {
        String expected = "[\n" +
                "    {\n" +
                "      \"bookId\": 1,\n" +
                "      \"bookName\": \"《大数据分析：数据驱动的企业绩效优化、过程管理和运营决策》\",\n" +
                "      \"publisher\": \"吴峻申\"\n" +
                "    }\n" +
                "  ]";

        BaseResponse actual = template.getForObject("/api/books", BaseResponse.class, new HashMap<>());

        assertThat(actual.getCode(), equalTo(ResponseStatus.OK.getCode()));
        assertThat(actual.getMessage(), equalTo(ResponseStatus.OK.getMessage()));
        assertThat(OBJECT_MAPPER.writeValueAsString(actual.getData()),
                equalTo(OBJECT_MAPPER.writeValueAsString(OBJECT_MAPPER.readValue(expected, List.class))));
    }

    @Test
    public void getBook() throws Exception {
        String expected = "{\n" +
                "    \"bookId\": 1,\n" +
                "    \"bookName\": \"《大数据分析：数据驱动的企业绩效优化、过程管理和运营决策》\",\n" +
                "    \"publisher\": \"吴峻申\"\n" +
                "  }";

        Map<String, String> multiValueMap = new HashMap<>();
        multiValueMap.put("bookId", "1");//传值，但要在url上配置相应的参数

        BaseResponse actual = template.getForObject("/api/books/{bookId}", BaseResponse.class, multiValueMap);

        assertThat(actual.getCode(), equalTo(ResponseStatus.OK.getCode()));
        assertThat(actual.getMessage(), equalTo(ResponseStatus.OK.getMessage()));
        assertThat(OBJECT_MAPPER.writeValueAsString(actual.getData()),
                equalTo(OBJECT_MAPPER.writeValueAsString(OBJECT_MAPPER.readValue(expected, Book.class))));
    }

    @Test
    public void updateBook() throws Exception {
        String editString = "{\n" +
                "        \"bookName\": \"《大数据分析：数据驱动的企业绩效优化、过程管理和运营决策》\",\n" +
                "        \"publisher\": \"吴峻申\"\n" +
                "      }";

        Book editedBook = OBJECT_MAPPER.readValue(editString, Book.class);

        HttpEntity<Book> formEntity = new HttpEntity<>(editedBook);

        Object[] uriVariables = {1};

        String expected = "Update book id=" + uriVariables[0];
        BaseResponse actual = template.exchange("/api/books/{bookId}", HttpMethod.PUT, formEntity, BaseResponse.class, uriVariables).getBody();

        assertThat(actual.getCode(), equalTo(ResponseStatus.OK.getCode()));
        assertThat(actual.getMessage(), equalTo(ResponseStatus.OK.getMessage()));
        assertThat(actual.getData(), equalTo(expected));
    }
}