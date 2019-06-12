package com.circle.expense.project.service;

import com.circle.expense.project.dto.ProjectAnalyzeDTO;
import com.circle.expense.project.entity.Project;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuan.liu
 * @since 2019-05-06
 */
public interface ProjectService extends IService<Project> {

    public Project createProject(Project project);

    public List<Project> listProject(Long id);

    public ProjectAnalyzeDTO getProjectAnalyzeDTO(Long projectId);

}
