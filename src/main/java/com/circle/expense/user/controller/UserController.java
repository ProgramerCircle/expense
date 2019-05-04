package com.circle.expense.user.controller;


import com.circle.expense.user.dto.LoginUserDTO;
import com.circle.expense.user.entity.User;
import com.circle.expense.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuan.liu
 * @since 2019-04-12
 */
@RestController
@CrossOrigin(origins = "http://localhost:8000" ,allowCredentials="true")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestBody User user){
        String verifyCode = userService.verifyUser(user);
        return ResponseEntity.ok(verifyCode);
    }

    @PostMapping("/register")
    public ResponseEntity<LoginUserDTO> registerUser(@RequestBody LoginUserDTO user){
        LoginUserDTO result = userService.registerUser(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/edit")
    public ResponseEntity<User> editUser(@RequestBody User user){
        User result = userService.editUser(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserDTO> login(@RequestParam("login")String login, @RequestParam("password")String password){
        return ResponseEntity.ok(userService.login(login,password));
    }

}
