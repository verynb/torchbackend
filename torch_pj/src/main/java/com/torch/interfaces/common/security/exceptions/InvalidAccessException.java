package com.torch.interfaces.common.security.exceptions;

@SuppressWarnings("serial")
public class InvalidAccessException extends RuntimeException {

    public static InvalidAccessException invalidAccessException() {
        return new InvalidAccessException();
    }
}
