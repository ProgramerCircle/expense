package com.circle.expense.expenseApplication.service;

import com.circle.expense.expenseApplication.entity.ExpenseApplication;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuan,liu
 * @since 2019-05-08
 */
public interface ExpenseApplicationService extends IService<ExpenseApplication> {

    public ExpenseApplication createExpenseApplication(ExpenseApplication expenseApplication);

    public ExpenseApplication getById(Long id);

    public List<ExpenseApplication> listExpenseApplicationByCondition(Long applicationUser,Long projectId,Long expenseTypeId,Long status,String expenseNo);

}
