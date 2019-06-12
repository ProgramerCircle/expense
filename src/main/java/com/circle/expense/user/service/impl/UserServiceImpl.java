package com.circle.expense.user.service.impl;

import ch.qos.logback.core.util.TimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.circle.core.exception.BizException;
import com.circle.core.util.RandomUtils;
import com.circle.core.util.StringUtils;
import com.circle.core.util.VerifyCodeUtils;
import com.circle.expense.expenseApprove.entity.ExpenseApprove;
import com.circle.expense.expenseApprove.service.ExpenseApproveService;
import com.circle.expense.team.entity.Team;
import com.circle.expense.team.entity.TeamManager;
import com.circle.expense.team.service.TeamManagerService;
import com.circle.expense.team.service.TeamService;
import com.circle.expense.user.dto.LoginUserDTO;
import com.circle.expense.user.entity.User;
import com.circle.expense.user.mapper.UserMapper;
import com.circle.expense.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import freemarker.template.utility.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.env.RandomValuePropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private TeamManagerService teamManagerService;

    @Autowired
    private ExpenseApproveService expenseApproveService;

    @Autowired
    private TeamService teamService;

    @Value("${expense.user.useVerifyCodeFlag}")
    private Integer useVerifyCodeFlag;

    public void verifyUserExists(User user){
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
    }

    @Override
    public String verifyUser(User user) {
        if(user.getId() != null){
            throw new BizException("系统错误,请稍后重试!");
        }
        this.verifyUserExists(user);
        String verifyCode = String.valueOf(RandomUtils.getNextInt(100000,999999));
        redisTemplate.opsForValue().set("verifyCode",verifyCode);
        if(useVerifyCodeFlag > 0){
            VerifyCodeUtils.sendVerifyCode(user.getPhone(),String.valueOf(verifyCode));
        }
        return verifyCode;
    }

    @Override
    @Transactional
    public LoginUserDTO registerUser(LoginUserDTO user) {
        this.verifyUserExists(user);
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
    @Transactional
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

    @Override
    public List<User> listByTeamId(Long id) {
        return baseMapper.selectList(new QueryWrapper<User>().eq("TEAM_ID",id));
    }

    @Override
    public List<User> listByTeamIdAndRank(Long id,Long rank) {
        return baseMapper.selectList(new QueryWrapper<User>().eq("TEAM_ID",id).eq("RANK",rank));
    }

    @Override
    public LoginUserDTO joinTeam(Long userId, Long teamId) {
        User user = baseMapper.selectById(userId);
        if(user.getTeamId() != null){
            throw new BizException("已有团队，无法再次加入！");
        }
        user.setTeamId(teamId);
        baseMapper.updateById(user);
        LoginUserDTO loginUserDTO = new LoginUserDTO(user);
        loginUserDTO.setStatus("ok");
        loginUserDTO.setCurrentAuthority("admin");
        return loginUserDTO;
    }

    @Override
    public void setManager(Long userId) {
        User user = baseMapper.selectById(userId);
        user.setRank(1);
        baseMapper.updateById(user);
        TeamManager teamManager = new TeamManager(userId, user.getTeamId());
        teamManager.setId(IdWorker.getId());
        teamManagerService.insert(teamManager);
    }

    @Override
    @Transactional
    public void cancelManager(Long userId) {
        User user = baseMapper.selectById(userId);
        Team team = teamService.selectById(user.getTeamId());
        if(team.getBelong().equals(userId)){
            throw new BizException("无法取管理员的权限");
        }
        user.setRank(0);
        baseMapper.updateById(user);
        teamManagerService.delete(new QueryWrapper<TeamManager>().eq("USER_ID",userId).eq("TEAM_ID",user.getTeamId()));
    }

}
