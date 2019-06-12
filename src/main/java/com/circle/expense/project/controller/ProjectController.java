package com.circle.expense.project.controller;


import com.circle.expense.project.dto.ProjectAnalyzeDTO;
import com.circle.expense.project.entity.Project;
import com.circle.expense.project.service.ProjectService;
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
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<Project> createProject(@RequestBody Project project){
        return ResponseEntity.ok(projectService.createProject(project));
    }

    @GetMapping("/list/by/team")
    public ResponseEntity<List<Project>> listProjectByTeamId(Long id){
        return ResponseEntity.ok(projectService.listProject(id));
    }

    @GetMapping("/analyze")
    public ResponseEntity<ProjectAnalyzeDTO> getProjectAnalyzeDTO(Long id){
        return ResponseEntity.ok(projectService.getProjectAnalyzeDTO(id));
    }


}
