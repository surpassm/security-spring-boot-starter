package com.github.surpassm.config.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.surpassm.common.jackson.Result;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author mc
 * Create date 2019/3/1 9:12
 * Version 1.0
 * Description token拦截器
 */
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {
	@Resource
	private ObjectMapper objectMapper;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);
			request.setAttribute("Authorization", token);
			return true;
		}
		response(response);
		return false;
	}


	/**
	 * 返回错误信息
	 */
	private void response(HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Access-Control-Allow-Origin", "*");
		try (PrintWriter out = response.getWriter()) {
			out.write(objectMapper.writeValueAsString(new Result(401, "你无权操作", "")));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
