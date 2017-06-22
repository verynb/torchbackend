package com.torch.interfaces.common.facade.dto;

import lombok.Data;

/**
 * Created by yuanj on 2017/6/19.
 */
@Data
public class CodeMessage {

  private String code;
  private String message;

  public CodeMessage() {
    this.code = "success";
    this.message = "成功";
  }

  public CodeMessage(String code, String message) {
    this.code = code;
    this.message = message;
  }
}
