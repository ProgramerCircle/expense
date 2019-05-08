package com.circle.expense.expenseType.dto;

import com.circle.expense.expenseType.entity.ExpenseType;
import lombok.Data;

@Data
public class ExpenseTypeDTO extends ExpenseType {

    private String approveUserName;

    private String approveStatusName;


}
