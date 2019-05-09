package com.circle.expense.expenseApprove.controller;


import com.circle.expense.expenseApplication.entity.ExpenseApplication;
import com.circle.expense.expenseApprove.service.ExpenseApproveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuan.liu
 * @since 2019-05-08
 */
@RestController
@CrossOrigin(origins = "*" ,allowCredentials="true")
@RequestMapping("/expenseApprove")
public class ExpenseApproveController {

    @Autowired
    private ExpenseApproveService expenseApproveService;

    @GetMapping("/list/by/condition")
    public ResponseEntity<List<ExpenseApplication>> listMyApprovalExpenseApplication(@RequestParam(value = "userId",required = true) Long userId, Long applicationUser, Long projectId, Long expenseTypeId, Long status, String expenseNo){
        return ResponseEntity.ok(expenseApproveService.listMyApprovalExpenseApplication(userId,applicationUser,projectId,expenseTypeId,status,expenseNo));
    }

    @PostMapping("/submit")
    public ResponseEntity submitApprove(Long expenseApplicationId){
        expenseApproveService.submitApprove(expenseApplicationId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/pass")
    public ResponseEntity passExpenseApplication(Long expenseApplicationId){
        expenseApproveService.passExpenseApplication(expenseApplicationId);
        return new ResponseEntity(HttpStatus.OK);
    };

    @PostMapping("/refuse")
    public ResponseEntity refuseExpenseApplication(Long expenseApplicationId){
        expenseApproveService.refuseExpenseApplication(expenseApplicationId);
        return new ResponseEntity(HttpStatus.OK);
    };

}
