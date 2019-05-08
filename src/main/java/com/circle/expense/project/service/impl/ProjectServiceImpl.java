package com.circle.expense.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.circle.core.exception.BizException;
import com.circle.expense.project.entity.Project;
import com.circle.expense.project.mapper.ProjectMapper;
import com.circle.expense.project.service.ProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
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
        return project;
    }

    @Override
    public List<Project> listProject(Long id) {
        return baseMapper.selectList(new QueryWrapper<Project>().eq("TEAM_ID",id));
    }


}
