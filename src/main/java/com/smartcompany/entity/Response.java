package com.smartcompany.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("response")
public class Response implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 审核状态，0代表待审核，
1代表审核通过，
2代表审核拒绝，
默认待审
     */
    private Integer status;

    /**
     * 本次请求审核介绍
     */
    private String intro;

    /**
     * 本次审核提起人id
     */
    private Integer from;

    /**
     * 用户id外键
     */
    private Integer Eid;

    /**
     * 权限类型
     */
    private String type;

    /**
     * 请求权限等级
     */
    private Integer rank;


}
