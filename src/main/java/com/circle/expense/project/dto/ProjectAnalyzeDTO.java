package com.circle.expense.project.dto;

import com.circle.expense.expenseType.dto.ExpenseTypeDTO;
import com.circle.expense.project.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectAnalyzeDTO {

    Project project;

    List<ExpenseTypeDTO> expenseTypeDTOS;

}
