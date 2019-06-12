package com.circle.expense.expenseApplication.dto;

import com.circle.expense.expenseApplication.entity.ExpenseApplication;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
public class ExpenseApplicationDTO extends ExpenseApplication {

    @JsonSerialize
    private String applicationUserName;

    @JsonSerialize
    private String approveUserName;

    @JsonSerialize
    private String projectName;

    @JsonSerialize
    private String expenseTypeName;


}
