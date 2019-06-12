package com.circle.expense.utils.exception;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.circle.core.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/exception")
public class ExceptionTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/test")
    public void bizExceptionTest(){
        throw new BizException("null","不能为空!");
    }

    @RequestMapping("/test/id")
    public String getId(){
        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
        Object seq = redisTemplate.opsForValue().get("seq");
        Integer num = 1;
        if(seq != null){
            num = (Integer) seq + 1;
        }
        redisTemplate.opsForValue().set("seq",num,seconds,TimeUnit.DAYS);
        System.out.println(num);
        return String.valueOf(IdWorker.getId());
    }

}
