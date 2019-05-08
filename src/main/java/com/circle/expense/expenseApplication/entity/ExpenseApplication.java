package com.circle.expense.expenseApplication.entity;

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
 * @author yuan,liu
 * @since 2019-05-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("SYS_EXPENSE_APPLICATION")
public class ExpenseApplication extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 费用申请单名称，根据项目名，费用类型名生成
     */
    @TableField("NAME")
    private String name;

    /**
     * 费用申请单号，根据项目代码，费用类型代码，日期，序号自动生成
     */
    @TableField("EXPENSE_NO")
    private String expenseNo;

    /**
     * 单据描述
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 金额
     */
    @TableField("AMOUNT")
    private Double amount;

    /**
     * 状态,1编辑中,2审批中,3通过,4驳回
     */
    @TableField("STATUS")
    private Long status;

    /**
     * 费用类型ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("EXPENSE_TYPE_ID")
    private Long expenseTypeId;

    /**
     * 项目ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("PROJECT_ID")
    private Long projectId;

    /**
     * 团队ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("TEAM_ID")
    private Long teamId;

    /**
     * 申请人ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("APPLICATION_USER")
    private Long applicationUser;


}
