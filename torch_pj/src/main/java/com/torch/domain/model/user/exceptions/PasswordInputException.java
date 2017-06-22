package com.torch.domain.model.user.exceptions;

/**
 * Created by long on 11/20/16.
 */
public class PasswordInputException extends RuntimeException {
    private final String password;

    private PasswordInputException(String password) {
        this.password = password;
    }

    public static PasswordInputException passwordInputException(String password) {
        return new PasswordInputException(password);
    }
}
