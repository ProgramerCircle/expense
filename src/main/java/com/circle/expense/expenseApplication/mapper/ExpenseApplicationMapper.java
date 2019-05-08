package com.circle.expense.expenseApplication.mapper;

import com.circle.expense.expenseApplication.entity.ExpenseApplication;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yuan,liu
 * @since 2019-05-08
 */
public interface ExpenseApplicationMapper extends BaseMapper<ExpenseApplication> {

    public Integer getExpenseNo();


}
