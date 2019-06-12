package com.circle.expense.expenseApplication.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.circle.expense.expenseApplication.dto.ExpenseApplicationDTO;
import com.circle.expense.expenseApplication.entity.ExpenseApplication;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

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

    public List<ExpenseApplicationDTO> listDTOByCondition(Long approveUser,
                                                          Long applicationUser,
                                                          Long expenseTypeId,
                                                          Long projectId,
                                                          Long status,
                                                          String expenseNo,
                                                          String startDate,
                                                          String endDate,
                                                          Boolean approveRecordSearchFlag,
                                                          List<Long> ids);

    public Page<ExpenseApplicationDTO> pageDTOByCondition(Long approveUser,
                                                       Long applicationUser,
                                                       Long expenseTypeId,
                                                       Long projectId,
                                                       Long status,
                                                       String expenseNo,
                                                       String startDate,
                                                       String endDate,
                                                       Boolean approveRecordSearchFlag,
                                                       List<Long> ids, Page page);

    public void deleteExpenseApplication(Long id);


}
