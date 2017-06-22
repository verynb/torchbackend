package com.torch.interfaces.common.security.exceptions;

public class TokenValidException extends RuntimeException {

    public static TokenValidException tokenValidException() {
        return new TokenValidException();
    }
}
