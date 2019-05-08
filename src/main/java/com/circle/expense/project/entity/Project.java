package com.circle.expense.project.entity;

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
@TableName("SYS_PROJECT")
public class Project extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 项目名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 项目名称
     */
    @TableField("SHORT_NAME")
    private String shortName;


    /**
     * 项目详情
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 团队ID
     */
    @TableField("TEAM_ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long teamId;

    /**
     * 金额
     */
    @TableField("AMOUNT")
    private Double amount;

    /**
     * 预算额
     */
    @TableField("BUDGET_AMOUNT")
    private Double budgetAmount;


}
