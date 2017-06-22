package com.torch.interfaces.common;


import com.torch.interfaces.common.exceptions.NotFoundException;
import com.torch.interfaces.common.exceptions.UnAuthorizedException;

import java.util.Optional;

/**
 * Created by long on 9/18/16.
 */
public class Responses {
    public static <T> T notFound(Optional<T> resource) {
        if (!resource.isPresent()) {
            throw NotFoundException.notFoundException();
        }
        return resource.get();
    }

    public static <T> T unAuthorized(Optional<T> resource) {
        if (!resource.isPresent()) {
            throw new UnAuthorizedException();
        }
        return resource.get();
    }
}
