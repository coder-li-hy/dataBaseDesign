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
 * 职位等级表
 * </p>
 *
 * @author lihy
 * @since 2024-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("level")
public class Level implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 逐渐id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 职务等级
     */
    private Integer rank;

    /**
     * 职务外键
     */
    private Integer Pid;

    /**
     * 等级介绍
     */
    private String intro;


}
