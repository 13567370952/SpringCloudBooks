package com.wujunshen.controller;

import com.wujunshen.security.LoginParameter;
import com.wujunshen.service.BookConsumerService;
import com.wujunshen.vo.BaseResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2016-10-24 <br>
 * Time:14:42 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@RestController
public class BookConsumerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookConsumerController.class);
    @Autowired
    private BookConsumerService bookConsumerService;

    @GetMapping(value = "/consumer/{bookId:[0-9]*}")
    public BaseResultVo getBook(@PathVariable Integer bookId) {
        LoginParameter loginParameter = new LoginParameter();
        loginParameter.setClientId("098f6bcd4621d373cade4e832627b4f6");
        loginParameter.setUserName("wujunshen");
        loginParameter.setPassword("wujunshen");
        BaseResultVo baseResultVo = bookConsumerService.getToken(loginParameter);

        if (baseResultVo.getCode() == 200) {
            Map tokenMap = (HashMap) baseResultVo.getData();
            LOGGER.info("token_type is: " + tokenMap.get("token_type"));
            LOGGER.info("access_token is: " + tokenMap.get("access_token"));
            String authorizationToken = tokenMap.get("token_type") + " " + tokenMap.get("access_token");
            return bookConsumerService.getBook(authorizationToken, bookId);
        } else {
            return baseResultVo;
        }
    }
}