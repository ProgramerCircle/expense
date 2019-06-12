package com.circle.expense.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.circle.core.exception.BizException;
import com.circle.expense.expenseApplication.service.ExpenseApplicationService;
import com.circle.expense.expenseType.dto.ExpenseTypeDTO;
import com.circle.expense.expenseType.entity.ExpenseType;
import com.circle.expense.expenseType.service.ExpenseTypeService;
import com.circle.expense.project.dto.ProjectAnalyzeDTO;
import com.circle.expense.project.entity.Project;
import com.circle.expense.project.mapper.ProjectMapper;
import com.circle.expense.project.service.ProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.circle.expense.team.entity.Team;
import com.circle.expense.team.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuan.liu
 * @since 2019-05-06
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {


    @Autowired
    private TeamService teamService;

    @Autowired
    private ExpenseTypeService expenseTypeService;

    @Autowired
    private ExpenseApplicationService expenseApplicationService;


    @Override
    @Transactional
    public Project createProject(Project project) {
        Integer num = baseMapper.selectCount(new QueryWrapper<Project>().eq("NAME", project.getName()).eq("TEAM_ID",project.getTeamId()));
        if(num > 0 ){
            throw new BizException("同团队下不允许项目名称重复！");
        }
        Integer num1 = baseMapper.selectCount(new QueryWrapper<Project>().eq("SHORT_NAME", project.getName()).eq("TEAM_ID",project.getTeamId()));
        if(num1 > 0 ){
            throw new BizException("同团队下不允许项目名称简写重复！");
        }
        project.setId(IdWorker.getId());
        baseMapper.insert(project);
        Team team = teamService.selectById(project.getTeamId());
        //创建默认项目，由管理员审批
        ExpenseType expenseType = new ExpenseType();
        expenseType.setId(IdWorker.getId());
        expenseType.setName("默认费用类型");
        expenseType.setMaxAmount(project.getBudgetAmount()/10);
        expenseType.setApproveStatus(true);
        expenseType.setApproveUser(team.getBelong());
        expenseType.setShortName("DEF");
        expenseType.setProjectId(project.getId());
        expenseTypeService.insert(expenseType);
        return project;
    }

    @Override
    public List<Project> listProject(Long id) {
        return baseMapper.selectList(new QueryWrapper<Project>().eq("TEAM_ID",id));
    }

    @Override
    public ProjectAnalyzeDTO getProjectAnalyzeDTO(Long projectId) {
        ProjectAnalyzeDTO projectAnalyzeDTO = new ProjectAnalyzeDTO();
        projectAnalyzeDTO.setProject(baseMapper.selectById(projectId));
        projectAnalyzeDTO.setExpenseTypeDTOS(expenseTypeService.listAnalyzeExpenseTypeDTO(projectId));
        return projectAnalyzeDTO;
    }


}
