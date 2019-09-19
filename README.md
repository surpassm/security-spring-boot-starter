# 敏捷开发权限验证模块
1. 在yml文件中常用配置
~~~
surpassm:
  security:
    enabled: true ##必要条件
    o-auth2:
      store-type: redis  ##缓存方式 可以使用JWT
      clients[0]:
        clientId: user_1
        clientIdSecret: 123456
        #成功token时长 过期时间默认24小时
        access-token-validity-seconds: 86400
        #刷新token时长 过期时间默认30天
        refresh-token-validity-seconds: 2592000
      clients[1]:
        clientId: user_2
        clientIdSecret: 123456
        #成功token时长 过期时间默认24小时
        access-token-validity-seconds: 86400
        #刷新token时长 过期时间默认30天
        refresh-token-validity-seconds: 2592000
    login-type: JSON
#      登陆页面设置
    no-verify[0]: /swagger-**
    no-verify[1]: /images/**
    no-verify[2]: /webjars/**
    no-verify[3]: /v2/api-docs**
    no-verify[4]: /swagger-resources/configuration/ui**
    no-verify[5]: /swagger-resources/configuration/security**
    no-verify[6]: /websocket/socketServer.ws**
    no-verify[7]: /sockjs/socketServer.ws**
    no-verify[8]: /login/v1/refreshToken**
    no-verify[9]: /mobile/v1/getPhone**
    no-verify[10]: /menu/v1/resourcesUpdate**
    no-verify[11]: /fileManage/v1/getFileNameUrl**
    no-verify[12]: /fileManage/v1/listUploadedFiles**
    no-verify[13]: /fileManage/v1/getPath**
    no-verify[14]: /upload/**
#    用户名密码登录请求处理url
    default-login-processing-url-from: /authentication/form
    #默认的登陆属性名称
#    username-parameter: username
    #登陆的密码属性名称
#    password-parameter: password
~~~
2、使用方法
~~~
//1、在Application启动类中添加自定义注解 @EnableSecurity

@EnableSecurity
@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

//2、在yml文件配置
surpassm:
  security:
    enabled: true

~~~
注：这2个条件必不可少
3、在自己的项目实现 UserDetailsService 接口处理自己的登陆逻辑
~~~
@Slf4j
@Component
public class SurpassmUserDetailsServiceImpl implements UserDetailsService {

	@Resource
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return buildUser(username);
	}

        //具体实现真实业务逻辑
	private UserDetails buildUser(String username) {
		log.info("用户开始登陆:"+username);
		if (StringUtils.isEmpty(username)) {
			throw new SurpassmAuthenticationException(Tips.PARAMETER_ERROR.msg);
		}
		String password = bCryptPasswordEncoder.encode("123456");
		log.info("数据库密码是:"+password);
		return new UserInfo(1L,username, password,
				true, true, true, true,
				AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));
	}
}
~~~
4、获取用户信息
 
 基于redis缓存做存储
~~~
@Configuration
public class BeanConfig {
	@Resource
	private TokenStore redisTokenStore;

	public UserInfo getAccessToken(String accessToken){
		OAuth2AccessToken oAuth2AccessToken = redisTokenStore.readAccessToken(accessToken);
		if (oAuth2AccessToken == null){
			throw new SurpassmAuthenticationException("token已失效");
		}
		Map<String, Object> additionalInformation = oAuth2AccessToken.getAdditionalInformation();
		if (additionalInformation.size() == 0){
			throw new SurpassmAuthenticationException("请登陆");
		}
		UserInfo object = (UserInfo)additionalInformation.get("userInfo");
		try {
			if (object != null) {
				return object;
			}
		} catch (Exception e) {
			throw new SurpassmAuthenticationException("请登陆");
		}
		throw new SurpassmAuthenticationException("请登陆");
	}
}

~~~
5、如需要对每个接口进行控制 
~~~
第一步：指定绑定接口
public interface RbacService {
	boolean hasPermission(HttpServletRequest request, Authentication authentication);
}


@Configuration
public class SurpassmAuthorizeConfigProvider implements AuthorizeCofigProvider {
	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		config.anyRequest().access("@rbacService.hasPermission(request,authentication)");
	}
}

第二步：实现接口RbacService.hasPermission()方法 栗子如下：

@Slf4j
@Component("rbacService")
public class RbacServiceImpl implements RbacService {
	private AntPathMatcher antPathMatcher = new AntPathMatcher();
	@Override
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		//具体业务逻辑在此处实现 true为通过、否则不通过
        boolean flag = false;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserInfo){
            //当前用戶
            String username = ((UserInfo) principal).getUsername();
//			Optional<UserInfo> userInfo = userInfoMapper.findByUsername(username);
//			//当前用户所具备的权限
//			List<UserMenu> menuList = userInfoMapper.findByUserRoles(userInfo.get().getUserRoles());
//			//當前用戶所擁有權限的所有URL进行遍历查看用户是否具备权限
//			String requestURI = request.getRequestURI();
//			if (!requestURI.equals("/favicon.ico")){
//				log.info("请求验证的URL"+requestURI);
//				for (UserMenu menu : menuList){
//					if (menu.getMenuUrl() != null && antPathMatcher.match(menu.getMenuUrl(),requestURI)){
//						flag = true;
//						break;
//					}
//				}
//			}
        }
        return true;
	}
}

~~~


可以参考   [demo-mybatis-1.0.0](https://github.com/surpassm/demo-mybatis)和[demo-JPA-1.0.0](https://github.com/surpassm/demo-jpa)示例使用