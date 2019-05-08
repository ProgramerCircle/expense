package com.circle.expense.expenseType.controller;


import com.circle.expense.expenseType.dto.ExpenseTypeDTO;
import com.circle.expense.expenseType.entity.ExpenseType;
import com.circle.expense.expenseType.service.ExpenseTypeService;
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
 * @since 2019-05-07
 */
@RestController
@RequestMapping("/expenseType")
public class ExpenseTypeController {

    @Autowired
    private ExpenseTypeService expenseTypeService;

    @PostMapping("/create")
    public ResponseEntity createExpenseType(@RequestBody ExpenseType expenseType){
        expenseTypeService.createExpenseType(expenseType);
        return ResponseEntity.ok(expenseType);
    }

    @GetMapping("/list/by/team")
    public ResponseEntity<List<ExpenseType>> listByTeam(Long id){
        return ResponseEntity.ok(expenseTypeService.listByProjectId(id));
    }

    @GetMapping("/list/dto/by/team")
    public ResponseEntity<List<ExpenseTypeDTO>> listDTOByTeam(Long id){
        return ResponseEntity.ok(expenseTypeService.listDTOByProjectId(id));
    }

    @GetMapping("/get")
    public ResponseEntity<ExpenseType> getById(Long id){
        return ResponseEntity.ok(expenseTypeService.getById(id));
    }


}
