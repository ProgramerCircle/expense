

package com.circle.expense.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Component
@Aspect
@Order(1)
public class ResourceAspect {
    private static Logger log = LoggerFactory.getLogger(ResourceAspect.class);

    @Around("within(com.circle.expense..*.controller..*)")
    protected Object aroudAdivce(ProceedingJoinPoint jp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestMethod = request.getMethod();
        String servletPath = request.getContextPath() + request.getServletPath();
        //参数
        String requestParameter = "";
        if("GET".equals(requestMethod)){
            requestParameter = request.getQueryString();
        }else{
            Enumeration em = request.getParameterNames();
            while (em.hasMoreElements()) {
                //参数名称
                String name = (String) em.nextElement();
                //根据参数名称获取到参数值
                requestParameter += name + "=" + request.getParameter(name) + "   ";
            }
        }
        long startTime = System.currentTimeMillis();
        log.info("------------------------------------------------------------------------");
        log.info("REQUEST BEGIN...: path = [{}], method = [{}] args = [{}] ",servletPath, requestMethod, requestParameter);
        Object rt = jp.proceed();

        String ipAddress = getIPAddress(request);
        log.info("ACCESS IP.......: {}" ,ipAddress);

        //时间
        long cost = System.currentTimeMillis() - startTime;
        //状态
        String resStatus = "200 OK";
        if (rt instanceof ResponseEntity) {
            ResponseEntity resEnt = (ResponseEntity) rt;
            resStatus = resEnt.getStatusCode().value() + " " + resEnt.getStatusCode().getReasonPhrase();
        }
        log.info("REQUEST END.....: [cost {}ms], res = {}",cost, resStatus);
        log.info("------------------------------------------------------------------------");
        return rt;
    }

    public  String getIPAddress(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
