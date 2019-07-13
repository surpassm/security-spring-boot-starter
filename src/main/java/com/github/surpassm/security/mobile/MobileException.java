package com.github.surpassm.security.mobile;

import org.springframework.security.core.AuthenticationException;

import java.io.Serializable;

/**
 * @author mc
 * Create date 2019/7/13 11:40
 * Version 1.0
 * Description
 */
public class MobileException extends AuthenticationException implements Serializable {
	public MobileException(String msg) {
		super(msg);
	}
}
