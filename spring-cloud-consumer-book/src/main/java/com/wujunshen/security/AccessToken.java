package com.wujunshen.security;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2016-11-3 <br>
 * Time:16:46 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
public class AccessToken {
    @JsonProperty("access_token")
    private String token;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private long expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}