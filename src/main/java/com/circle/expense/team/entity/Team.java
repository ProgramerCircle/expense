package com.circle.expense.team.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.circle.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuan.liu
 * @since 2019-05-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("SYS_TEAM")
public class Team extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 团队名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 团队详情
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 创建人ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("BELONG")
    private Long belong;



}
