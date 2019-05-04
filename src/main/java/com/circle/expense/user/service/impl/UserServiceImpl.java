package com.circle.expense.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.circle.core.exception.BizException;
import com.circle.core.util.RandomUtils;
import com.circle.core.util.StringUtils;
import com.circle.core.util.VerifyCodeUtils;
import com.circle.expense.user.dto.LoginUserDTO;
import com.circle.expense.user.entity.User;
import com.circle.expense.user.mapper.UserMapper;
import com.circle.expense.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.env.RandomValuePropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuan.liu
 * @since 2019-04-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String verifyUser(User user) {
        if(user.getId() != null){
            throw new BizException("系统错误,请稍后重试!");
        }
        Integer num = null;
        num = baseMapper.selectCount(new QueryWrapper<User>().eq("LOGIN_NO", user.getLoginNo()));
        if(num > 0){
            throw new BizException("该登陆账号已被使用!");
        }
        num = baseMapper.selectCount(new QueryWrapper<User>().eq("PHONE", user.getPhone()));
        if(num > 0){
            throw new BizException("该手机号已被使用!");
        }
        num = baseMapper.selectCount(new QueryWrapper<User>().eq("EMAIL", user.getEmail()));
        if(num > 0){
            throw new BizException("该邮箱已被使用!");
        }
        String verifyCode = String.valueOf(RandomUtils.getNextInt(100000,999999));
        redisTemplate.opsForValue().set("verifyCode",verifyCode);
        //TODO 发送验证码

        //VerifyCodeUtils.sendVerifyCode(user.getPhone(),String.valueOf(verifyCode));
        return verifyCode;
    }

    @Override
    public LoginUserDTO registerUser(LoginUserDTO user) {
        Object verifyCode = redisTemplate.opsForValue().get("verifyCode");
        if(!user.getCaptcha().equals((String)verifyCode)){
            throw new BizException("验证码错误!");
        }
        user.setId(IdWorker.getId());
        baseMapper.insert(user);
        user.setStatus("ok");
        user.setCurrentAuthority("admin");
        return user;
    }

    @Override
    public User editUser(User user) {
        User oldUser = baseMapper.selectById(user.getId());
        Integer num = null;
        if(user.getLoginNo() != oldUser.getLoginNo()){
            num = baseMapper.selectCount(new QueryWrapper<User>().eq("LOGIN_NO", user.getLoginNo()));
            if(num > 0){
                throw new BizException("该登陆账号已被使用!");
            }else {
                oldUser.setLoginNo(user.getLoginNo());
            }
        }
        if(user.getPhone() != oldUser.getPhone()){
            num = baseMapper.selectCount(new QueryWrapper<User>().eq("PHONE", user.getPhone()));
            if(num > 0){
                throw new BizException("该手机号已被使用!");
            }else {
                oldUser.setPhone(user.getPhone());
            }
        }
        if(user.getEmail() != oldUser.getEmail()){
            num = baseMapper.selectCount(new QueryWrapper<User>().eq("EMAIL", user.getEmail()));
            if(num > 0){
                throw new BizException("该邮箱已被使用!");
            }else {
                oldUser.setEmail(user.getEmail());
            }
        }
        baseMapper.updateById(oldUser);
        return oldUser;
    }

    @Override
    public LoginUserDTO login(String login, String password) {
        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("LOGIN_NO", login).or().eq("PHONE", login));
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        if(user != null && user.getPassword().equals(password) ){
            BeanUtils.copyProperties(user,loginUserDTO);
            loginUserDTO.setStatus("ok");
            loginUserDTO.setCurrentAuthority("admin");
            return loginUserDTO;
        }else {
            loginUserDTO.setStatus("error");
            loginUserDTO.setCurrentAuthority("admin");
            return loginUserDTO;
        }
    }

}
