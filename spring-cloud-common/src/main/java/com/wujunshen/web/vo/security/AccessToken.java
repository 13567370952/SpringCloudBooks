package com.wujunshen.web.vo.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2016-11-3 <br>
 * Time:16:46 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@Data
@ApiModel(value = "访问令牌")
public class AccessToken {
    @ApiModelProperty(value = "访问令牌值", required = true)
    @JsonProperty("access_token")
    private String token;
    @ApiModelProperty(value = "访问令牌类型", required = true)
    @JsonProperty("token_type")
    private String tokenType;
    @ApiModelProperty(value = "令牌有效时间，单位为秒", required = true)
    @JsonProperty("expires_in")
    private long expiresIn;
}