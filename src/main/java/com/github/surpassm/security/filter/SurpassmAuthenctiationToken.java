package com.github.surpassm.security.filter;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * @author mc
 * Create date 2019/2/15 12:52
 * Version 1.0
 * Description
 */
public class SurpassmAuthenctiationToken extends AbstractAuthenticationToken {
	private static final long servialVsersion = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	/**
	 * Creates a token with the supplied array of authorities.
	 *
	 * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
	 *                    represented by this authentication object.
	 */
	public SurpassmAuthenctiationToken(Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return null;
	}
}
