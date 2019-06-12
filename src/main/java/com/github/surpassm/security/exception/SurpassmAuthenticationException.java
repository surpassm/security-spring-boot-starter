package com.github.surpassm.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author mc
 * version 1.0v
 * date 2019/2/10 17:45
 * description TODO
 */
public class SurpassmAuthenticationException extends AuthenticationException {

    public SurpassmAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public SurpassmAuthenticationException(String msg) {
        super(msg);
    }
}
