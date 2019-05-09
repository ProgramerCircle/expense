package com.circle.expense.expenseApplication.controller;


import com.circle.expense.expenseApplication.entity.ExpenseApplication;
import com.circle.expense.expenseApplication.service.ExpenseApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<ExpenseApplication>> listExpenseApplication(@RequestParam(value = "applicationUser",required = true) Long applicationUser,Long projectId,Long expenseTypeId,Long status,String expenseNo){
        return ResponseEntity.ok(expenseApplicationService.listExpenseApplicationByCondition(applicationUser,projectId,expenseTypeId,status,expenseNo));
    }

}
