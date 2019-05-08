package com.circle.expense.expenseType.entity;

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
 * @since 2019-05-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("SYS_EXPENSE_TYPE")
public class ExpenseType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 费用类型名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 费用类型名称缩写
     */
    @TableField("SHORT_NAME")
    private String shortName;

    /**
     * 金额上限
     */
    @TableField("MAX_AMOUNT")
    private Double maxAmount;

    /**
     * 审批标识0为管理员审批1为指定人审批
     */
    @TableField("APPROVE_STATUS")
    private Boolean approveStatus;

    /**
     * 审批人ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("APPROVE_USER")
    private Long approveUser;

    /**
     * 项目ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("PROJECT_ID")
    private Long projectId;


}
