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
 * 具体职位表
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
    private Integer lel;

    /**
     * 职务类型外键
     */
    private Integer pid;

    /**
     * 职务等级介绍
     */
    private String intro;

    /**
     * 职务名称
     */
    private String name;


}
