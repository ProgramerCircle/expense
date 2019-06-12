package com.circle.expense.expenseType.mapper;

import com.circle.expense.expenseType.dto.ExpenseTypeDTO;
import com.circle.expense.expenseType.entity.ExpenseType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yuan.liu
 * @since 2019-05-07
 */
public interface ExpenseTypeMapper extends BaseMapper<ExpenseType> {

    public List<ExpenseTypeDTO> listAnalyzeExpenseTypeDTO(@Param("projectId")Long id);

}
