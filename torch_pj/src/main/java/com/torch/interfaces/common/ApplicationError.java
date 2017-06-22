package com.torch.interfaces.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.torch.interfaces.common.facade.dto.CodeMessage;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

/**
 * Created by long on 9/21/16.
 */
@Data
@Builder
public class ApplicationError {

  private CodeMessage codeMessage;

}
