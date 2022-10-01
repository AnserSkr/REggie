package com.reggie.entity;

import lombok.Data;
import org.apache.ibatis.javassist.SerialVersionUID;

import java.io.Serializable;

/**
 * @author 98248
 * @Date: 2022/9/29 - 09 - 29 - 22:50
 * @Description: com.reggie.entity
 * @version: 1.0
 */
@Data
public class User implements Serializable {
    private static final Long serialVersionUid = 1L;

    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 性别 0 女 1 男
     */
    private String sex;

    /**
     * 身份证号
     */
    private String idNumber;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 状态 0:禁用,1:正常
     */
    private Integer status;

}
