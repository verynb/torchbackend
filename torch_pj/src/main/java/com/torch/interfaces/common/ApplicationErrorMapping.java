package com.torch.interfaces.common;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.torch.interfaces.common.exceptions.TorchException;
import com.torch.interfaces.common.exceptions.UnAuthorizedException;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.common.security.exceptions.TokenNotExistException;
import com.torch.interfaces.common.security.exceptions.TokenValidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class ApplicationErrorMapping {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationErrorMapping.class);

  @ExceptionHandler(UnAuthorizedException.class)
  @ResponseStatus(OK)
  @ResponseBody
  public ApplicationError unAuthorizedException(UnAuthorizedException e) {
    return applicationError("failed", "用户名或密码错误");
  }

  @ExceptionHandler(TorchException.class)
  @ResponseStatus(OK)
  @ResponseBody
  public ApplicationError torch(TorchException e) {
    return applicationError("failed", e.getMessage());
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(OK)
  @ResponseBody
  public ApplicationError runtimeException(RuntimeException e) {
    return applicationError("failed", e.getMessage());
  }


  @ExceptionHandler(Exception.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ApplicationError validationException(Exception e) {
    e.printStackTrace();
    return applicationError("failed", "system error");
  }

  @ExceptionHandler(TokenNotExistException.class)
  @ResponseStatus(FORBIDDEN)
  @ResponseBody
  public ApplicationError mapTokenNotExistException(TokenNotExistException e) {
    return applicationError("TOKEN_NOT_EXIST", "未知Token");
  }

  @ExceptionHandler(TokenValidException.class)
  @ResponseStatus(FORBIDDEN)
  @ResponseBody
  public ApplicationError mapTokenValidException(TokenValidException e) {
    return applicationError("TOKEN_INVALID", "未知Token");
  }

  private ApplicationError applicationError(String code, String message) {
    return ApplicationError.builder()
        .codeMessage(new CodeMessage(code, message))
        .build();
  }

}
