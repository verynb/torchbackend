package com.torch.interfaces.common;

import com.torch.interfaces.common.security.Session;

public class Users {
    public static Long currentUserId() {
        return Session.getUserId();
    }
}
