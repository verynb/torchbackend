package com.torch.application.upload;

import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by yuanj on 2017/6/13.
 */
//@EnableAutoConfiguration
@Component
@ConfigurationProperties(prefix = "torch.photo.path")
//@PropertySource("classpath:imagePath.properties")
@Data
public class PhotoPath {

 public static String PHOTO_PATH="C:\\nginx-1.13.3\\torch\\photo\\";

 public static String EXCEL_TEMP="C:\\nginx-1.13.3\\torch\\excelTemp\\";

 public static String NEW_EXCEL="C:\\nginx-1.13.3\\torch\\newExcel\\";

}
