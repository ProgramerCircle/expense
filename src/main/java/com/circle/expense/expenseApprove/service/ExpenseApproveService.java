package com.circle.expense.expenseApprove.service;

import com.circle.expense.expenseApplication.entity.ExpenseApplication;
import com.circle.expense.expenseApprove.entity.ExpenseApprove;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuan.liu
 * @since 2019-05-08
 */
public interface ExpenseApproveService extends IService<ExpenseApprove> {

    public List<ExpenseApplication> listMyApprovalExpenseApplication(Long userId,Long applicationUser,Long projectId,Long expenseTypeId,Long status,String expenseNo);

    public void submitApprove(Long expenseApplicationId);

    public void passExpenseApplication(Long expenseApplicationId);

    public void refuseExpenseApplication(Long expenseApplicationId);

}
