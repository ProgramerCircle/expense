package com.circle.expense.expenseType.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.circle.core.exception.BizException;
import com.circle.expense.expenseType.dto.ExpenseTypeDTO;
import com.circle.expense.expenseType.entity.ExpenseType;
import com.circle.expense.expenseType.mapper.ExpenseTypeMapper;
import com.circle.expense.expenseType.service.ExpenseTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.circle.expense.project.entity.Project;
import com.circle.expense.project.mapper.ProjectMapper;
import com.circle.expense.user.entity.User;
import com.circle.expense.user.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuan.liu
 * @since 2019-05-07
 */
@Service
public class ExpenseTypeServiceImpl extends ServiceImpl<ExpenseTypeMapper, ExpenseType> implements ExpenseTypeService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void createExpenseType(ExpenseType expenseType) {
        Integer count = baseMapper.selectCount(new QueryWrapper<ExpenseType>().eq("NAME", expenseType.getName()).eq("PROJECT_ID", expenseType.getId()));
        if (count > 0){
            throw new BizException("同项目下不允许费用类型名称相同！");
        }
        Integer count1 = baseMapper.selectCount(new QueryWrapper<ExpenseType>().eq("SHORT_NAME", expenseType.getName()).eq("PROJECT_ID", expenseType.getId()));
        if (count1 > 0){
            throw new BizException("同项目下不允许费用类型名称简写相同！");
        }
        if(expenseType.getApproveStatus() && expenseType.getApproveUser() == null){
            throw new BizException("为选择指定的审批人！");
        }
        Project project = projectMapper.selectById(expenseType.getProjectId());
        if(expenseType.getMaxAmount() > project.getBudgetAmount()){
            throw new BizException("费用类型金额上限不允许超过项目预算额");
        }
        expenseType.setId(IdWorker.getId());
        baseMapper.insert(expenseType);
    }

    @Override
    public ExpenseType getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public List<ExpenseType> listByProjectId(Long id) {
        return baseMapper.selectList(new QueryWrapper<ExpenseType>().eq("PROJECT_ID",id));
    }

    @Override
    public List<ExpenseTypeDTO> listDTOByProjectId(Long id) {
        List<ExpenseType> expenseTypes = listByProjectId(id);
        List<ExpenseTypeDTO> expenseTypeDTOS = new ArrayList<>();
        expenseTypes.stream().forEach(item -> {
            ExpenseTypeDTO expenseTypeDTO = new ExpenseTypeDTO();
            BeanUtils.copyProperties(item,expenseTypeDTO);
            if(item.getApproveStatus()) {
                expenseTypeDTO.setApproveStatusName("指定人审批");
                User user = userMapper.selectById(item.getApproveUser());
                expenseTypeDTO.setApproveUserName(user.getName());
            }else{
                expenseTypeDTO.setApproveStatusName("管理员审批");
            }
            expenseTypeDTOS.add(expenseTypeDTO);
        });
        return expenseTypeDTOS;
    }

    @Override
    public List<ExpenseTypeDTO> listAnalyzeExpenseTypeDTO(Long id) {
        List<ExpenseTypeDTO> expenseTypeDTOS = baseMapper.listAnalyzeExpenseTypeDTO(id);
        expenseTypeDTOS.stream().forEach(item -> {
            if(item.getApproveStatus()) {
                item.setApproveStatusName("指定人审批");
            }else{
                item.setApproveStatusName("管理员审批");
            }
        });
        return expenseTypeDTOS;
    }
}
