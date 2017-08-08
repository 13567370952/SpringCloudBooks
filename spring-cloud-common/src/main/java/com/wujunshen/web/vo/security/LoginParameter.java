package com.wujunshen.web.vo.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2016-11-3 <br>
 * Time:16:43 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@Data
@AllArgsConstructor
@ApiModel(value = "登录参数")
public class LoginParameter {
    @NotNull(message = "不能为null")
    @NotBlank(message = "不能为空")
    @ApiModelProperty(value = "传入的客户端id，相当于微信的openID", required = true)
    @JsonProperty("client_id")
    private String clientId;
    @NotNull(message = "不能为null")
    @NotBlank(message = "不能为空")
    @ApiModelProperty(value = "访问API网关的用户名", required = true)
    @JsonProperty("user_name")
    private String userName;
    @NotNull(message = "不能为null")
    @NotBlank(message = "不能为空")
    @ApiModelProperty(value = "访问API网关的密码", required = true)
    @JsonProperty("password")
    private String password;
}