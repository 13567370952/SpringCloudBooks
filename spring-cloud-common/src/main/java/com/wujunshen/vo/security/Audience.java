package com.wujunshen.vo.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2016-11-3 <br>
 * Time:15:50 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@Data
@ConfigurationProperties(prefix = "audience")
public class Audience {
    private String clientId;
    private String base64Secret;
    private String name;
    private int expiresSecond;
}
