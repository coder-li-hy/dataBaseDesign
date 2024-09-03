package com.smartcompany.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lihy
 * @since 2024-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 员工id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 员工姓名
     */
    private String name;

    /**
     * 员工年龄
     */
    private Integer age;

    /**
     * 员工性别，1为男，0为女
     */
    private Boolean sex;

    /**
     * 员工家庭地址
     */
    private String address;

    /**
     * 员工每月薪资
     */
    private Integer salary;

    /**
     * 员工账号
     */
    private String account;

    /**
     * 员工电话
     */
    private String phone;

    /**
     * 员工状态 有禁用 正常 开除等状态
     */
    private String status;

    /**
     * 员工密码 初始密码为123456
     */
    private String password;


}
