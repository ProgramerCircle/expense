package com.circle.expense.team.controller;


import com.circle.expense.team.dto.TeamProjectDTO;
import com.circle.expense.team.entity.Team;
import com.circle.expense.team.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuan.liu
 * @since 2019-05-06
 */
@RestController
@CrossOrigin(origins = "*" ,allowCredentials="true")
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/create")
    public ResponseEntity<Team> createTeam(@RequestBody Team team){
        return ResponseEntity.ok(teamService.createTeam(team));
    }

    @GetMapping("/getTeamProject")
    public ResponseEntity<TeamProjectDTO> getTeamProject(Long id){
        return ResponseEntity.ok(teamService.getTeamProject(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Team>> listTeam(){
        return ResponseEntity.ok(teamService.listTeam());
    }


}
