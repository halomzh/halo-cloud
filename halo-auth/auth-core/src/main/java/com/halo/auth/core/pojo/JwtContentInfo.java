package com.halo.auth.core.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * jwt存储的内容
 *
 * @author zhihao.mao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtContentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 过期时间
     */
    private Date expireDate;

    /**
     * 账号ID
     */
    private String account;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户信息
     */
    private Map<String, Object> userInfo;

    /**
     * 其他信息
     */
    private Map<String, Object> otherInfo;
}
