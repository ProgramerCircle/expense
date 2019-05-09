package com.circle.expense.expenseApprove.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.circle.core.exception.BizException;
import com.circle.expense.expenseApplication.entity.ExpenseApplication;
import com.circle.expense.expenseApplication.service.ExpenseApplicationService;
import com.circle.expense.expenseApprove.entity.ExpenseApprove;
import com.circle.expense.expenseApprove.mapper.ExpenseApproveMapper;
import com.circle.expense.expenseApprove.service.ExpenseApproveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.circle.expense.expenseType.entity.ExpenseType;
import com.circle.expense.expenseType.service.ExpenseTypeService;
import com.circle.expense.project.entity.Project;
import com.circle.expense.project.service.ProjectService;
import com.circle.expense.team.entity.TeamManager;
import com.circle.expense.team.service.TeamManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuan.liu
 * @since 2019-05-08
 */
@Service
public class ExpenseApproveServiceImpl extends ServiceImpl<ExpenseApproveMapper, ExpenseApprove> implements ExpenseApproveService {

    @Autowired
    private ExpenseApplicationService expenseApplicationService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ExpenseTypeService expenseTypeService;

    @Autowired
    private TeamManagerService teamManagerService;


    @Override
    public List<ExpenseApplication> listMyApprovalExpenseApplication(Long userId, Long applicationUser, Long projectId, Long expenseTypeId, Long status, String expenseNo) {
        List<Long> expenseApplicationIds = baseMapper.selectList(new QueryWrapper<ExpenseApprove>().eq("APPROVE_USER", userId)).stream().map(ExpenseApprove::getExpenseApplicationId).collect(Collectors.toList());
        if(expenseApplicationIds.size() > 0) {
            List<ExpenseApplication> expenseApplications = expenseApplicationService.selectList(new QueryWrapper<ExpenseApplication>()
                    .eq(applicationUser != null, "APPLICATION_USER", applicationUser)
                    .eq(projectId != null, "PROJECT_ID", projectId)
                    .eq(expenseTypeId != null, "EXPENSE_TYPE_ID", expenseTypeId)
                    .eq(status != null, "STATUS", 2)
                    .like(expenseNo != null, "EXPENSE_NO", expenseNo)
                    .in("ID", expenseApplicationIds)
            );
            return expenseApplications;
        }
       else return new ArrayList<>();
    }

    @Override
    @Transactional
    public void submitApprove(Long expenseApplicationId) {
        ExpenseApplication expenseApplication = expenseApplicationService.selectById(expenseApplicationId);
        Project project = projectService.selectById(expenseApplication.getProjectId());
        ExpenseType expenseType = expenseTypeService.selectById(expenseApplication.getExpenseTypeId());
        if(project.getBudgetAmount() - project.getAmount() - expenseApplication.getAmount() < 0){
            throw new BizException("超出预算额度，无法提交！");
        }
        Boolean approveStatus = expenseType.getApproveStatus();
        if(approveStatus){
            ExpenseApprove expenseApprove = new ExpenseApprove();
            expenseApprove.setId(IdWorker.getId());
            expenseApprove.setApproveUser(expenseType.getApproveUser());
            expenseApprove.setExpenseApplicationId(expenseApplicationId);
            baseMapper.insert(expenseApprove);
        }else {
            List<Long> managerIds = teamManagerService.selectList(new QueryWrapper<TeamManager>().eq("TEAM_ID", expenseApplication.getTeamId())).stream().map(TeamManager::getUserId).collect(Collectors.toList());
            managerIds.stream().forEach(item -> {
                if(!item.equals(expenseApplication.getApplicationUser())){
                    ExpenseApprove expenseApprove = new ExpenseApprove();
                    expenseApprove.setId(IdWorker.getId());
                    expenseApprove.setApproveUser(item);
                    expenseApprove.setExpenseApplicationId(expenseApplicationId);
                    baseMapper.insert(expenseApprove);
                }
            });
        }
        expenseApplication.setStatus(2L);
        expenseApplicationService.updateById(expenseApplication);
    }

    @Override
    @Transactional
    public void passExpenseApplication(Long expenseApplicationId) {
        ExpenseApplication expenseApplication = expenseApplicationService.selectById(expenseApplicationId);
        Project project = projectService.selectById(expenseApplication.getProjectId());
        ExpenseType expenseType = expenseTypeService.selectById(expenseApplication.getExpenseTypeId());
        if(project.getBudgetAmount() - project.getAmount() - expenseApplication.getAmount() < 0){
            throw new BizException("超出预算额度，无法通过！");
        }
        expenseApplication.setStatus(3L);
        expenseApplicationService.updateById(expenseApplication);
        project.setAmount(project.getAmount() + expenseApplication.getAmount());
        projectService.updateById(project);
        List<Long> expenseApproveIds = baseMapper.selectList(new QueryWrapper<ExpenseApprove>().eq("EXPENSE_APPLICATION_ID", expenseApplicationId)).stream().map(ExpenseApprove::getId).collect(Collectors.toList());
        baseMapper.deleteBatchIds(expenseApproveIds);
    }

    @Override
    @Transactional
    public void refuseExpenseApplication(Long expenseApplicationId) {
        ExpenseApplication expenseApplication = expenseApplicationService.selectById(expenseApplicationId);
        Project project = projectService.selectById(expenseApplication.getProjectId());
        ExpenseType expenseType = expenseTypeService.selectById(expenseApplication.getExpenseTypeId());
        expenseApplication.setStatus(4L);
        expenseApplicationService.updateById(expenseApplication);
        List<Long> expenseApproveIds = baseMapper.selectList(new QueryWrapper<ExpenseApprove>().eq("EXPENSE_APPLICATION_ID", expenseApplicationId)).stream().map(ExpenseApprove::getId).collect(Collectors.toList());
        baseMapper.deleteBatchIds(expenseApproveIds);
    }
}
