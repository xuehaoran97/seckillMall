package com.xxxx.seckillmall.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"服务端异常"),
    //登陆模块
    MOBILE_ERROR(200211,"手机号错误"),
    LOHGIN_ERROR(200210,"登陆异常"),

    BIND_ERROR(200300,"绑定异常"),

    STOCK_EMPTY(500100,"库存不足"),

    REPEATE_ERROR(500200,"重复下单");


    ;
    private Integer code;
    private String message;





}
