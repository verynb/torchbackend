package com.torch.domain.model.user.exceptions;

/**
 * Created by long on 11/20/16.
 */
public class PhoneInputException extends RuntimeException {
    private final String phone;

    private PhoneInputException(String phone) {
        this.phone = phone;
    }

    public static PhoneInputException phoneInputException(String phone) {
        return new PhoneInputException(phone);
    }
}
