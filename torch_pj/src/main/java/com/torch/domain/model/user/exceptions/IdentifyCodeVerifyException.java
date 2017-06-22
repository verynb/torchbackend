package com.torch.domain.model.user.exceptions;

/**
 * Created by long on 11/20/16.
 */
public class IdentifyCodeVerifyException extends RuntimeException {
    private final String identifyCode;

    private IdentifyCodeVerifyException(String identifyCode) {
        this.identifyCode = identifyCode;
    }

    public static IdentifyCodeVerifyException identifyCodeVerifyException(String identifyCode) {
        return new IdentifyCodeVerifyException(identifyCode);
    }
}
