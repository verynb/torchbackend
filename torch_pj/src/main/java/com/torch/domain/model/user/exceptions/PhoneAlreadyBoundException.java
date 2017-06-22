package com.torch.domain.model.user.exceptions;

/**
 * Created by long on 9/24/16.
 */
public class PhoneAlreadyBoundException extends RuntimeException {

    private final String phone;

    private PhoneAlreadyBoundException(String phone) {
        this.phone = phone;
    }

    public static PhoneAlreadyBoundException phoneAlreadyBoundException(String phone) {
        return new PhoneAlreadyBoundException(phone);
    }
}
