package com.circle.expense.expenseApplication.controller;


import com.circle.expense.expenseApplication.entity.Evidence;
import com.circle.expense.expenseApplication.service.EvidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 凭证表 前端控制器
 * </p>
 *
 * @author yuan.liu
 * @since 2019-05-11
 */
@RestController
@RequestMapping("/expenseApplication/evidence")
public class EvidenceController {

    @Autowired
    private EvidenceService evidenceService;

    @PostMapping
    public ResponseEntity<Evidence> uploadEvidence(@RequestParam("file") MultipartFile uploadFile){

        return ResponseEntity.ok( evidenceService.uploadEvidence(uploadFile));
    }

    @GetMapping
    public ResponseEntity<Evidence> getEvidenceById(Long id){
        return ResponseEntity.ok(evidenceService.selectById(id));
    }

}
