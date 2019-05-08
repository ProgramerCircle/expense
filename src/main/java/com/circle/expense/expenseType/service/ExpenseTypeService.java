package com.circle.expense.expenseType.service;

import com.circle.expense.expenseType.dto.ExpenseTypeDTO;
import com.circle.expense.expenseType.entity.ExpenseType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuan.liu
 * @since 2019-05-07
 */
public interface ExpenseTypeService extends IService<ExpenseType> {

    public void createExpenseType(ExpenseType expenseType);

    public ExpenseType getById(Long id);

    public List<ExpenseType> listByProjectId(Long id);

    public List<ExpenseTypeDTO> listDTOByProjectId(Long id);

}
