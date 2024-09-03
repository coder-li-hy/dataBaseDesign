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
@TableName("position")
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 职位类型id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 职位类型
     */
    private String name;

    /**
     * 部门id外键
     */
    private Integer Did;

    /**
     * 职位类型介绍
     */
    private String intro;


}
