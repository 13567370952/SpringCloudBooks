package com.wujunshen.web.vo.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2016-11-3 <br>
 * Time:16:46 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@Data
public class AccessToken {
    @JsonProperty("access_token")
    private String token;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private long expiresIn;
}