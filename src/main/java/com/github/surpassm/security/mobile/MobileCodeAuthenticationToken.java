package com.github.surpassm.security.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * @author mc
 * Create date 2019/7/13 10:22
 * Version 1.0
 * Description
 */
public class MobileCodeAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    /**
     * 存放认证信息，认证之前存手机号，认证之后存放用户信息
     */
    private final Object principal;


    /**
     * 未认证时存入手机号
     *
     * @param mobile 手机号码
     */
    public MobileCodeAuthenticationToken(String mobile) {
        super(null);
        this.principal = mobile;
        setAuthenticated(false);
    }

    /**
     * 登陆成功存放用户信息
     *
     * @param principal   用户信息
     * @param authorities 认证信息集合
     */
    public MobileCodeAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        //必须使用super，因为我们重写
        super.setAuthenticated(true);
    }

    /**
     * 获取凭据
     *
     * @return Object
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * 获取当前手机号
     *
     * @return Object
     */
    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException("无法将此令牌设置为受信任-使用的构造函数，而该构造函数采用的是授予的颁发机构列表");
        }
        super.setAuthenticated(false);
    }

    /**
     * 删除凭据
     */
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
