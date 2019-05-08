package com.circle.expense.team.dto;

import com.circle.expense.project.entity.Project;
import com.circle.expense.team.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TeamProjectDTO {

    private Team team;

    private List<Project> projects;




}
