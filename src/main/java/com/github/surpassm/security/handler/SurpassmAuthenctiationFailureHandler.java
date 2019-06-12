package com.github.surpassm.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.surpassm.common.jackson.Result;
import com.github.surpassm.security.enums.LoginResponseType;
import com.github.surpassm.security.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author mc
 * version 1.0v
 * date 2019/2/10 17:23
 * description   自定义登陆失败配置 继承security默认失败处理器
 */
@Slf4j
@Component("surpassmAuthenctiationFailureHandler")
public class SurpassmAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private SecurityProperties securityProperties;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info("登录失败");
        if (LoginResponseType.JSON.equals(securityProperties.getLoginType())) {
            //设置返回状态码 默认返回状态码为200
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new Result(HttpStatus.UNAUTHORIZED.value(),exception.getMessage())));
        }else{
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
