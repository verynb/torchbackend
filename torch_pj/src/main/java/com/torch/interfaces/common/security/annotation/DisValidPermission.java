package com.bici.interfaces.common.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不用校验权限
 * 
 * @author service#yangle.org.cn
 * @date 2017年1月10日 下午6:48:36 
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DisValidPermission {

}
