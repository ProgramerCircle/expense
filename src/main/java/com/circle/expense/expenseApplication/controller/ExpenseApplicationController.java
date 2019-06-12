package com.circle.expense.expenseApplication.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.circle.expense.expenseApplication.dto.ExpenseApplicationDTO;
import com.circle.expense.expenseApplication.entity.ExpenseApplication;
import com.circle.expense.expenseApplication.service.ExpenseApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuan,liu
 * @since 2019-05-08
 */
@RestController
@CrossOrigin(origins = "*" ,allowCredentials="true")
@RequestMapping("/expenseApplication")
public class ExpenseApplicationController {

    @Autowired
    private ExpenseApplicationService expenseApplicationService;

    @PostMapping("/create")
    public ResponseEntity<ExpenseApplication> createExpenseApplication(@RequestBody ExpenseApplication expenseApplication){
        return ResponseEntity.ok(expenseApplicationService.createExpenseApplication(expenseApplication));
    }

    @GetMapping("/get")
    public ResponseEntity<ExpenseApplication> getExpenseApplication(Long id){
        return ResponseEntity.ok(expenseApplicationService.getById(id));
    }

    @GetMapping("/list/by/condition")
    public ResponseEntity<List<ExpenseApplicationDTO>> listExpenseApplication(Long approveUser, Long applicationUser, Long projectId, Long expenseTypeId, Long status, String expenseNo,Boolean approveRecordSearchFlag){
        if(approveRecordSearchFlag == null){
            approveRecordSearchFlag = false;
        }
        return ResponseEntity.ok(expenseApplicationService.listDTOByCondition(approveUser,applicationUser,expenseTypeId,projectId,status,expenseNo,null,null,approveRecordSearchFlag,null));
    }

    @GetMapping("/page/by/condition")
    public ResponseEntity<Page<ExpenseApplicationDTO>> listExpenseApplication(Long approveUser, Long applicationUser, Long projectId, Long expenseTypeId, Long status, String expenseNo, Boolean approveRecordSearchFlag,int current,int size){
        if(approveRecordSearchFlag == null){
            approveRecordSearchFlag = false;
        }
        return ResponseEntity.ok(expenseApplicationService.pageDTOByCondition(approveUser,applicationUser,expenseTypeId,projectId,status,expenseNo,null,null,approveRecordSearchFlag,null,new Page(current,size)));
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteExpenseApplication(Long id){
        expenseApplicationService.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
