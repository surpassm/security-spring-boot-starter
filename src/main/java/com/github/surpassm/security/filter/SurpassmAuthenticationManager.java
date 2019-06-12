package com.github.surpassm.security.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author mc
 * Create date 2019/2/15 12:47
 * Version 1.0
 * Description
 */
public class SurpassmAuthenticationManager extends UsernamePasswordAuthenticationFilter {

//    @Autowired
//    private CustomerUserDetailsService userDetailsService;

//    @Autowired
//    private JwtTokenUtils jwtTokenUtil;

	private String tokenHeader="Authorization";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResopnse = (HttpServletResponse) response;
//        if("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
//            httpResopnse.setStatus(HttpServletResponse.SC_OK);
//        }else {
//            String authToken = httpRequest.getHeader(this.tokenHeader);
//            if(authToken!=null)
//                authToken =authToken.substring(7);
//            String username = jwtTokenUtil.getUsernameFromToken(authToken);
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//                if (jwtTokenUtil.validateToken(authToken)) {
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }
            chain.doFilter(request, response);
//        }
//
    }

	/**
	 * 可以获取request
	 * @param request
	 * @param response
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		return super.attemptAuthentication(request, response);
	}

	/**
	 * 成功验证后调用的方法
	 * @param request
	 * @param response
	 * @param chain
	 * @param authResult
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
	}

	/**
	 * 验证失败时候调用的方法
	 * @param request
	 * @param response
	 * @param failed
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		super.unsuccessfulAuthentication(request, response, failed);
	}
}
