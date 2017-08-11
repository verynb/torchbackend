/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.interfaces.upload;

import static com.torch.interfaces.common.ApiPaths.API_CONTEXT_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.torch.application.school.SchoolService;
import com.torch.application.upload.ImageUploadService;
import com.torch.application.upload.PhotoPath;
import com.torch.interfaces.common.exceptions.TorchException;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.common.security.annotation.RoleCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuanj 2017-01-12 16:41:05
 * @version 1.0
 */
@RestController
@RequestMapping(path = API_CONTEXT_PATH, produces = APPLICATION_JSON_VALUE)
@Api(value = "UploadResource", description = "图片上传相关api")
public class UploadResource {

  @Value("${torch.photo.path.ip}")
  private String SERVER_PREFIX;

  private final ImageUploadService imageUploadService;

  private final SchoolService schoolService;

  @Autowired
  public UploadResource(final ImageUploadService imageUploadService,
      final SchoolService schoolService
  ) {
    this.imageUploadService = imageUploadService;
    this.schoolService = schoolService;
  }

  @RoleCheck
  @ApiOperation(value = "上传头像", notes = "", httpMethod = "POST")
  @RequestMapping(path = "/headPhoto", method = POST)
  @ResponseStatus(HttpStatus.CREATED)
  public UploadRusltDto uploadHeadPhoto(@RequestBody UploadDto uploadDto) {
    String fullPath = imageUploadService.GenerateImage(uploadDto.getImgStr(), PhotoPath.PHOTO_PATH);
    if (StringUtils.isBlank(fullPath)) {
      throw new TorchException("头像上传失败");
    }
    return UploadRusltDto.builder()
        .codeMessage(new CodeMessage())
        .absolutePath(SERVER_PREFIX + fullPath)
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "上传家访图片", notes = "", httpMethod = "POST")
  @RequestMapping(path = "/visitPhoto", method = POST)
  @ResponseStatus(HttpStatus.CREATED)
  public UploadRusltDto uploadVisitPhoto(@RequestBody UploadDto uploadDto) {
    String fullPath = imageUploadService.GenerateImage(uploadDto.getImgStr(), PhotoPath.PHOTO_PATH);
    if (StringUtils.isBlank(fullPath)) {
      throw new TorchException("上传失败");
    }
    return UploadRusltDto.builder()
        .codeMessage(new CodeMessage())
        .absolutePath(SERVER_PREFIX + fullPath)
        .build();
  }
}
