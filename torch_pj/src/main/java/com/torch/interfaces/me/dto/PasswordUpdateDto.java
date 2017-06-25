package com.torch.interfaces.me.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by yuanj on 2017/6/25.
 */
@Data
public class PasswordUpdateDto {

  @NotBlank
  private String password;
  @NotBlank
  private String newPassword;
  @NotBlank
  private String newPasswordTwo;

}
