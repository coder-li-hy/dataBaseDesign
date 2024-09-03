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
 * 用户请求申请表
 * </p>
 *
 * @author lihy
 * @since 2024-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("request")
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 请求权限类型
     */
    private String type;

    /**
     * 请求权限等级
     */
    private Integer rank;

    /**
     * 本次请求介绍
     */
    private String intro;

    /**
     * 本次请求状态，0待审核，1审核通过，2审核失败
     */
    private Integer status;

    /**
     * 权限请求请求对象（用户id）
     */
    private Integer to;

    /**
     * 用户id外键
     */
    private Integer Eid;


    /**
     * 申请成功后的jwt令牌
     */
    private String jwt;

}
