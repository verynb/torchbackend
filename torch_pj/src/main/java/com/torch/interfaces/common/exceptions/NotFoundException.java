package com.torch.interfaces.common.exceptions;

/**
 * Created by long on 9/18/16.
 */
public class NotFoundException extends RuntimeException {
    private NotFoundException() {}

    public static NotFoundException notFoundException() {
        return new NotFoundException();
    }
}
