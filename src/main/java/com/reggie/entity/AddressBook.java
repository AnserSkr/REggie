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
    Long id;
    /**
     * 用户ID
     */
    Long userId;
    /**
     * 收货人
     */
    String consignee;
    /**
     * 性别 0女，1男
     */
    Integer sex;
    /**
     * 手机号
     */
    String phone;
    /**
     * 省级区划编号
     */
    String provinceCode;
    /**
     * 省级名称
     */
    String provinceName;
    /**
     * 市级区划编号
     */
    String cityCode;
    /**
     * 市级名称
     */
    String cityName;
    /**
     * 区级区划编号
     */
    String districtCode;
    /**
     * 区级名称
     */
    String districtName;
    /**
     * 详细地址
     */
    String detail;
    /**
     * 标签
     */
    String label;
    /**
     * 是否为默认地址 0否，1是
     */
    Integer isDefault;

    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    Long createUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    Long updateUser;

    /**
     * 是否删除
     */
    Integer isDeleted;
}
