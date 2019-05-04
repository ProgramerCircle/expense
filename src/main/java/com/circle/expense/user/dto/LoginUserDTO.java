package com.circle.expense.user.dto;

import com.circle.expense.user.entity.User;
import lombok.Data;

@Data
public class LoginUserDTO extends User {

    private String currentAuthority;

    private String status;

    //验证码
    private String captcha;

}
