package com.circle.expense.user.service;

import com.circle.core.util.RandomUtils;
import com.circle.expense.user.dto.LoginUserDTO;
import com.circle.expense.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuan.liu
 * @since 2019-04-12
 */
public interface UserService extends IService<User> {

    //创建用户时,进行验证码校验
    public String verifyUser(User user);

    public LoginUserDTO registerUser(LoginUserDTO user);

    public User editUser(User user);

    public LoginUserDTO login(String login, String password);

}
