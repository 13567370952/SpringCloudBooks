package com.wujunshen.web.vo.response;

import lombok.Data;

/**
 * User:Administrator(吴峻申)
 * Date:2015-11-30
 * Time:11:12
 * Mail:frank_wjs@hotmail.com
 */
@Data
public class BaseResponse {
    private int code;
    private String message;
    // 返回数据
    private Object data;
}