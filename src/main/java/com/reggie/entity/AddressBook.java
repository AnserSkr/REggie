package com.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.apache.ibatis.javassist.SerialVersionUID;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 98248
 * @Date: 2022/10/1 - 10 - 01 - 15:47
 * @Description: com.reggie.entity
 * @version: 1.0
 */
@Data
public class AddressBook implements Serializable {
    private static final Long serialVersionUID = 1L;
    /**
     * 数据ID
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 收货人
     */
    private String consignee;
    /**
     * 性别 0女，1男
     */
    private Integer sex;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 省级区划编号
     */
    private String provinceCode;
    /**
     * 省级名称
     */
    private String provinceName;
    /**
     * 市级区划编号
     */
    private String cityCode;
    /**
     * 市级名称
     */
    private String cityName;
    /**
     * 区级区划编号
     */
    private String districtCode;
    /**
     * 区级名称
     */
    private String districtName;
    /**
     * 详细地址
     */
    private String detail;
    /**
     * 标签
     */
    private String label;
    /**
     * 是否为默认地址 0否，1是
     */
    private Integer isDefault;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * 是否删除
     */
    private Integer isDeleted;
}
