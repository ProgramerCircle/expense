package com.circle.expense.expenseApprove.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.circle.core.exception.BizException;
import com.circle.expense.expenseApplication.dto.ExpenseApplicationDTO;
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
import com.circle.expense.user.entity.User;
import com.circle.expense.user.service.UserService;
import com.circle.expense.utils.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
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

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;


    @Override
    public List<ExpenseApplicationDTO> listMyApprovalExpenseApplication(Long userId, Long applicationUser, Long projectId, Long expenseTypeId, Long status, String expenseNo) {
        List<Long> expenseApplicationIds = baseMapper.selectList(new QueryWrapper<ExpenseApprove>().eq("APPROVE_USER", userId)).stream().map(ExpenseApprove::getExpenseApplicationId).collect(Collectors.toList());
        if(expenseApplicationIds.size() > 0) {
            return expenseApplicationService.listDTOByCondition(null,applicationUser,expenseTypeId,projectId,2L,expenseNo,null,null,false,expenseApplicationIds);
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
            if(expenseType.getApproveUser().equals(expenseApplication.getApplicationUser())){
                throw new BizException("不能审批自己的单据，请创建默认费用类型单据通知管理员审批！");
            }
            ExpenseApprove expenseApprove = new ExpenseApprove();
            expenseApprove.setId(IdWorker.getId());
            expenseApprove.setApproveUser(expenseType.getApproveUser());
            expenseApprove.setExpenseApplicationId(expenseApplicationId);
            baseMapper.insert(expenseApprove);
        }else {
            List<Long> managerIds = teamManagerService.selectList(new QueryWrapper<TeamManager>().eq("TEAM_ID", expenseApplication.getTeamId())).stream().map(TeamManager::getUserId).collect(Collectors.toList());
            if(managerIds.size() < 1){
                throw new BizException("该团队下无负责人，请通知管理员维护！");
            }
            if(managerIds.size() == 1 && managerIds.get(0).equals(expenseApplication.getApplicationUser())){
                throw new BizException("该团队下无更多的负责人，请通知管理员维护！");
            }
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
    public void passExpenseApplication(Long expenseApplicationId,Long approveUserId) {
        ExpenseApplication expenseApplication = expenseApplicationService.selectById(expenseApplicationId);
        Project project = projectService.selectById(expenseApplication.getProjectId());
        ExpenseType expenseType = expenseTypeService.selectById(expenseApplication.getExpenseTypeId());
        if(project.getBudgetAmount() - project.getAmount() - expenseApplication.getAmount() < 0){
            throw new BizException("超出预算额度，无法通过！");
        }
        User applicationUser = userService.selectById(expenseApplication.getApplicationUser());
        User approveUser = userService.selectById(approveUserId);
        //设置审批信息
        expenseApplication.setStatus(3L);
        expenseApplication.setApproveUser(approveUser.getId());
        expenseApplication.setApproveDate(ZonedDateTime.now());
        expenseApplicationService.updateById(expenseApplication);
        //修改项目已使用额度
        project.setAmount(project.getAmount() + expenseApplication.getAmount());
        projectService.updateById(project);

        //发送邮件通知
        try {
            String emailTitle = "您的费用申请单已通过，请注意查收！";
            String emailContent =  "您的单号为 "+expenseApplication.getExpenseNo()+"已审批通过，请注意查收。\n\n" +"审批人："+approveUser.getName() ;
            emailService.sendEmail(applicationUser.getName(),applicationUser.getEmail(),emailTitle,emailContent);
        }catch (Exception e){}

        List<Long> expenseApproveIds = baseMapper.selectList(new QueryWrapper<ExpenseApprove>().eq("EXPENSE_APPLICATION_ID", expenseApplicationId)).stream().map(ExpenseApprove::getId).collect(Collectors.toList());
        baseMapper.deleteBatchIds(expenseApproveIds);
    }

    @Override
    @Transactional
    public void refuseExpenseApplication(Long expenseApplicationId,Long approveUserId) {
        ExpenseApplication expenseApplication = expenseApplicationService.selectById(expenseApplicationId);
        Project project = projectService.selectById(expenseApplication.getProjectId());
        ExpenseType expenseType = expenseTypeService.selectById(expenseApplication.getExpenseTypeId());

        User applicationUser = userService.selectById(expenseApplication.getApplicationUser());
        User approveUser = userService.selectById(approveUserId);
        //发送邮件通知
        try {
            String emailTitle = "您的费用申请单被驳回，请重新提交！";
            String emailContent = "您的单号为 "+expenseApplication.getExpenseNo()+"被驳回，请重新提交。\n\n" +"审批人："+approveUser.getName();
            emailService.sendEmail(applicationUser.getName(),applicationUser.getEmail(),emailTitle,emailContent);
        }catch (Exception e){}
        //设置审批信息
        expenseApplication.setStatus(4L);
        expenseApplication.setApproveUser(approveUser.getId());
        expenseApplication.setApproveDate(ZonedDateTime.now());
        expenseApplicationService.updateById(expenseApplication);
        List<Long> expenseApproveIds = baseMapper.selectList(new QueryWrapper<ExpenseApprove>().eq("EXPENSE_APPLICATION_ID", expenseApplicationId)).stream().map(ExpenseApprove::getId).collect(Collectors.toList());
        if(expenseApproveIds.size() > 0) {
            baseMapper.deleteBatchIds(expenseApproveIds);
        }
    }
}
