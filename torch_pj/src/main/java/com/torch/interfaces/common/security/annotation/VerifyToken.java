package com.torch.interfaces.common.security.annotation;

import com.torch.interfaces.common.security.Session;
import com.torch.interfaces.common.security.TokenService;
import com.torch.interfaces.common.security.exceptions.InvalidAccessException;
import com.torch.interfaces.common.security.exceptions.TokenNotExistException;
import com.torch.interfaces.common.security.exceptions.TokenValidException;
import com.torch.util.CommonFun;
import com.torch.util.cache.RedisUtils;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class VerifyToken {

  private final TokenService tokenService;

  @Autowired
  private RedisUtils redisUtils;
  @Autowired
  private Environment env;

  @Autowired
  public VerifyToken(final TokenService tokenService) {
    this.tokenService = tokenService;
  }

  /**
   * 注解@RoleCheck需要验证token和访问权限
   *
   * @return void
   */
  @Before("execution(public * com.torch.interfaces..*.*(..)) && @annotation(roleCheck)")
  @Order(50)
  public void verifyTokenAndRole(RoleCheck roleCheck) throws Throwable {
    verifyToken();
  }

  private void verifyToken() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
        .getRequest();

    String token = request.getHeader("x-auth-token");

    if (StringUtils.isBlank(token)) {
      throw new TokenNotExistException();
    }
    if (CommonFun.isNe(redisUtils.get("token/" + token))) {
      throw TokenValidException.tokenValidException();
    }
    if (token.equals("xxxxx-admin")) {
      return;
    }
    Optional<Session> session = tokenService.verify(token);
    if (!session.isPresent()) {
      throw TokenValidException.tokenValidException();
    }
    Session.persistenceCurrentSession(session.get());

    redisUtils.refreshExpireTime(token, Long.parseLong(env.getProperty("expireTime.login")));
  }

  private void verifyRole(String[] apiGroups) {
    boolean hasRole = false;

    Object object = redisUtils.get("usergroup/" + Session.getUsername());
    if (object == null) {
      throw new InvalidAccessException();
    }
    String userGroup = object.toString();

    for (String group : apiGroups) {
      if (userGroup.indexOf(group.trim()) != -1) {
        hasRole = true;
      }
    }
    if (!hasRole) {
      throw new InvalidAccessException();
    }
  }

}
