package com.halo.auth.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 服务异常代码
 *
 * @author shoufeng
 */
@AllArgsConstructor
public enum AuthJwtExceptionCodeEnum {

    /**
     * 失败
     */
    JWT_TOKEN_EXPIRED(6001, "会话超时，请重新登录"),
    JWT_SIGNATURE(6002, "不合法的token，请认真比对 token 的签名"),
    JWT_ILLEGAL_ARGUMENT(6003, "缺少token参数"),
    JWT_GEN_TOKEN_FAIL(6004, "生成token失败"),
    JWT_PARSER_TOKEN_FAIL(6005, "解析token失败"),
    ;

    @Getter
    private int code;
    @Getter
    private String message;
}
