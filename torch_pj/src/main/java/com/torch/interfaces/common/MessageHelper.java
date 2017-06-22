package com.torch.interfaces.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageHelper implements ApplicationContextAware {


    private ApplicationContext applicationContext;

    public String getMessage(String code, Object... args) {
        return applicationContext.getMessage(code, args, code, Locale.getDefault());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
