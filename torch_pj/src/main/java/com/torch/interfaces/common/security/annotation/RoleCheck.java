package com.torch.interfaces.common.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleCheck {

	/**
	 * 有权限访问的用户组 <br/>
	 * 多个用户组用“,”隔开 <br/>
	 * 不配置role默认登录了就可以访问接口
	 * 
	 * @return String
	 */
	String group() default "";
}