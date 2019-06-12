package com.circle.expense.team.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.circle.core.exception.BizException;
import com.circle.expense.project.entity.Project;
import com.circle.expense.project.mapper.ProjectMapper;
import com.circle.expense.team.dto.TeamProjectDTO;
import com.circle.expense.team.entity.Team;
import com.circle.expense.team.entity.TeamManager;
import com.circle.expense.team.mapper.TeamMapper;
import com.circle.expense.team.service.TeamManagerService;
import com.circle.expense.team.service.TeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.circle.expense.user.entity.User;
import com.circle.expense.user.mapper.UserMapper;
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
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

    @Autowired
    public ProjectMapper projectMapper;
    @Autowired
    public UserMapper userMapper;
    @Autowired
    public TeamManagerService teamManagerService;

    @Override
    @Transactional
    public Team createTeam(Team team) {
        if(team == null){
            throw new BizException("数据有误,请重试!");
        }
        Integer num = baseMapper.selectCount(new QueryWrapper<Team>().eq("NAME", team.getName()));
        if (num > 0){
            throw new BizException("团队名称已存在!请重试!");
        }
        team.setId(IdWorker.getId());
        baseMapper.insert(team);
        User user = userMapper.selectById(team.getBelong());
        user.setTeamId(team.getId());
        userMapper.updateById(user);
        return team;
    }

    @Override
    public TeamProjectDTO getTeamProject(Long id) {
        Team team = baseMapper.selectById(id);
        List<Project> projects = projectMapper.selectList(new QueryWrapper<Project>().eq("TEAM_ID", id));
        return new TeamProjectDTO(team,projects);
    }

    @Override
    public List<Team> listTeam() {
        return baseMapper.selectList(new QueryWrapper<>());
    }
}
