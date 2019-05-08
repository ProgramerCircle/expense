package com.circle.expense.team.service;

import com.circle.expense.team.dto.TeamProjectDTO;
import com.circle.expense.team.entity.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.circle.expense.user.dto.LoginUserDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuan.liu
 * @since 2019-05-06
 */
public interface TeamService extends IService<Team> {

    public Team createTeam(Team team);

    public TeamProjectDTO getTeamProject(Long id);

    public List<Team> listTeam();

}
