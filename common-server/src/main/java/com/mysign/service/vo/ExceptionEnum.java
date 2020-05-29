package com.mysign.service.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ExceptionEnum {
    INPUT_IS_BLANK(500,"空字段"),
    PASSWORD_IS_WRONG(401,"用户名或密码错误"),
    PASSWORD_IS_NOT_SAME(500,"密码不匹配"),
    CODE_IS_NOT_SAME(500,"验证码不匹配"),
    ROLE_ALREADY_AXISTS(409,"该用户已存在"),
    DATE_PARSE_ERROR(500,"传入日期字符串有误"),
    ROLE_IS_NOT_EXISTS(500,"用户不存在"),
    CODE_GET_ERROR(500,"验证码输入错误"),
    CODE_HAVE_GOT_OUT(500,"验证码已过时" ),
    THE_NUMBER_HAS_REACHED(500,"选择人数已满" ),
    SIGN_MESSAGE_IS_NOT_EXISTS(500,"签到信息已添加,不可重复添加");
;
    private int status;
private String message;
}
