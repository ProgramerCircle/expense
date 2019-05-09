package com.circle.expense.user.controller;


import com.circle.expense.user.dto.LoginUserDTO;
import com.circle.expense.user.entity.User;
import com.circle.expense.user.service.UserService;
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
 * @since 2019-04-12
 */
@RestController
@CrossOrigin(origins = "*" ,allowCredentials="true")
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

    @GetMapping("/list/by/team")
    public ResponseEntity<List<User>> listByTeam(Long id){
        return ResponseEntity.ok(userService.listByTeamId(id));
    }

    @PostMapping("/team/join")
    public ResponseEntity<LoginUserDTO> joinTeam(Long userId,Long teamId){
        return ResponseEntity.ok(userService.joinTeam(userId,teamId));
    }

    @PostMapping("/team/cancel/manager")
    public ResponseEntity cancelManager(Long userId){
        userService.cancelManager(userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/team/set/manager")
    public ResponseEntity setManager(Long userId){
        userService.setManager(userId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
