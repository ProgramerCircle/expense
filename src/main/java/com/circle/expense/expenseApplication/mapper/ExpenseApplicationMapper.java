package com.circle.expense.expenseApplication.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.circle.expense.expenseApplication.dto.ExpenseApplicationDTO;
import com.circle.expense.expenseApplication.entity.ExpenseApplication;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    public List<ExpenseApplicationDTO> pageDTOByCondition(@Param("approveUser")Long approveUser,
                                                          @Param("applicationUser")Long applicationUser,
                                                          @Param("expenseTypeId")Long expenseTypeId,
                                                          @Param("projectId")Long projectId,
                                                          @Param("status")Long status,
                                                          @Param("expenseNo")String expenseNo,
                                                          @Param("startDate")String startDate,
                                                          @Param("endDate")String endDate,
                                                          @Param("approveRecordSearchFlag")Boolean approveRecordSearchFlag,
                                                          @Param("ids")List<Long> ids,
                                                          Page page);

    public List<ExpenseApplicationDTO> listDTOByCondition(@Param("approveUser")Long approveUser,
                                                          @Param("applicationUser")Long applicationUser,
                                                          @Param("expenseTypeId")Long expenseTypeId,
                                                          @Param("projectId")Long projectId,
                                                          @Param("status")Long status,
                                                          @Param("expenseNo")String expenseNo,
                                                          @Param("startDate")String startDate,
                                                          @Param("endDate")String endDate,
                                                          @Param("approveRecordSearchFlag")Boolean approveRecordSearchFlag,
                                                          @Param("ids")List<Long> ids);


}
