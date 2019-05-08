package com.circle.expense.user.dto;

import com.circle.expense.user.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class LoginUserDTO extends User {

    private String currentAuthority;

    private String status;

    //验证码
    private String captcha;

    public LoginUserDTO(){
    }

    public LoginUserDTO(User user){
        BeanUtils.copyProperties(user,this);
    }

}
