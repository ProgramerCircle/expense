package com.circle.expense.expenseApplication.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.circle.core.util.CalendarUtils;
import com.circle.expense.expenseApplication.dto.ExpenseApplicationDTO;
import com.circle.expense.expenseApplication.entity.ExpenseApplication;
import com.circle.expense.expenseApplication.mapper.ExpenseApplicationMapper;
import com.circle.expense.expenseApplication.service.ExpenseApplicationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.circle.expense.expenseApprove.entity.ExpenseApprove;
import com.circle.expense.expenseApprove.service.ExpenseApproveService;
import com.circle.expense.expenseType.entity.ExpenseType;
import com.circle.expense.expenseType.mapper.ExpenseTypeMapper;
import com.circle.expense.expenseType.service.ExpenseTypeService;
import com.circle.expense.project.entity.Project;
import com.circle.expense.project.mapper.ProjectMapper;
import com.circle.expense.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuan,liu
 * @since 2019-05-08
 */
@Service
public class ExpenseApplicationServiceImpl extends ServiceImpl<ExpenseApplicationMapper, ExpenseApplication> implements ExpenseApplicationService {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ExpenseTypeService expenseTypeService;

    @Autowired
    private ExpenseApproveService expenseApproveService;

    @Override
    public ExpenseApplication createExpenseApplication(ExpenseApplication expenseApplication) {
        Project project = projectService.selectById(expenseApplication.getProjectId());
        ExpenseType expenseType = expenseTypeService.selectById(expenseApplication.getExpenseTypeId());
        Integer seq = baseMapper.getExpenseNo();
        String expenseNo = project.getShortName()+expenseType.getShortName()+ CalendarUtils.getYear() + String.format("%02d", CalendarUtils.getMonth()) + String.format("%02d", CalendarUtils.getDate()) + String.format("%05d", seq);
        String name = project.getName()+"-"+expenseType.getName()+"申请单";

        expenseApplication.setId(IdWorker.getId());
        expenseApplication.setName(name);
        expenseApplication.setExpenseNo(expenseNo);
        baseMapper.insert(expenseApplication);

        return null;
    }

    @Override
    public ExpenseApplication getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public List<ExpenseApplication> listExpenseApplicationByCondition(Long applicationUser, Long projectId, Long expenseTypeId, Long status, String expenseNo) {
        List<ExpenseApplication> expenseApplications = baseMapper.selectList(new QueryWrapper<ExpenseApplication>()
                .eq("APPLICATION_USER", applicationUser)
                .eq(projectId != null, "PROJECT_ID", projectId)
                .eq(expenseTypeId != null, "EXPENSE_TYPE_ID", expenseTypeId)
                .eq(status != null, "STATUS", status)
                .like(expenseNo != null, "EXPENSE_NO", expenseNo)
        );
        return expenseApplications;
    }

    @Override
    public List<ExpenseApplicationDTO> listDTOByCondition(Long approveUser, Long applicationUser, Long expenseTypeId, Long projectId, Long status, String expenseNo, String startDate, String endDate, Boolean approveRecordSearchFlag, List<Long> ids) {
        List<ExpenseApplicationDTO> result = baseMapper.listDTOByCondition(approveUser,applicationUser,expenseTypeId,projectId,status,expenseNo,startDate,endDate,approveRecordSearchFlag,ids);
        return result;
    }

    @Override
    public Page<ExpenseApplicationDTO> pageDTOByCondition(Long approveUser, Long applicationUser, Long expenseTypeId, Long projectId, Long status, String expenseNo, String startDate, String endDate, Boolean approveRecordSearchFlag, List<Long> ids, Page page) {
        Page<ExpenseApplicationDTO> result = new Page<>();
        List<ExpenseApplicationDTO> records = baseMapper.pageDTOByCondition(approveUser, applicationUser, expenseTypeId, projectId, status, expenseNo, startDate, endDate, approveRecordSearchFlag, ids, page);
        result.setRecords(records);
        result.setTotal(page.getTotal());
        return  result;

    }

    @Override
    @Transactional
    public void deleteExpenseApplication(Long id) {
        ExpenseApplication expenseApplication = baseMapper.selectById(id);
        if(expenseApplication.getStatus() == 1){
            expenseApproveService.delete(new QueryWrapper<ExpenseApprove>().eq("EXPENSE_APPLICATION_ID",id));
        }
        baseMapper.deleteById(id);
    }
}
