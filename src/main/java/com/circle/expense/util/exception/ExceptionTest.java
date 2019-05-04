package com.circle.expense.util.exception;

import com.circle.core.exception.BizException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionTest {

    @RequestMapping("/test")
    public void bizExceptionTest(){
        throw new BizException("null","不能为空!");
    }

}
