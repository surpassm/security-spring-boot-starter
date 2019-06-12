package com.github.surpassm.security.filter;

import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author mc
 * Create date 2019/2/15 12:47
 * Version 1.0
 * Description
 */
@Data
public class SurpassmAuthenctiationProvider implements AuthenticationProvider {
//	private SocialUserDetailsService socialUserDetailsService;
//	private UsersConnectionRepository usersConnectionRepository;
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		SurpassmAuthenctiationToken authenctiationToken = (SurpassmAuthenctiationToken) authentication;
//		Set<String> providerUserIds = new HashSet<String>(16);
//		providerUserIds.add((String)authenctiationToken.getPrincipal());
//		//查询usersConnection 用户是否存在
//		Set<String> userIds = usersConnectionRepository.findUserIdsConnectedTo(authenctiationToken.getProviderId(),providerUserIds);
//		if (CollectionUtils.isEmpty(userIds) || userIds.size() !=1){
//			throw new InternalAuthenticationServiceException("数据表‘usersConnection’用户信息不存在");
//		}
//		String userId = userIds.iterator().next();
//		UserDetails user = socialUserDetailsService.loadUserByUserId(userId);
//		if (user == null){
//			throw new InternalAuthenticationServiceException("无法获取用户信息");
//		}
//		SurpassmAuthenctiationToken authenctiationResulrt = new SurpassmAuthenctiationToken(user,user.getAuthorities());
//		authenctiationResulrt.setDetails(authenctiationToken);
//		return authenctiationResulrt;
		return authentication;

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return SurpassmAuthenctiationToken.class.isAssignableFrom(authentication);
	}
}
