package com.circle.expense.expenseApprove.entity;

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
 * @since 2019-05-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("SYS_EXPENSE_APPROVE")
public class ExpenseApprove extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 费用申请单ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("EXPENSE_APPLICATION_ID")
    private Long expenseApplicationId;

    /**
     * 审批人
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("APPROVE_USER")
    private Long approveUser;



}
