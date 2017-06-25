package com.torch.application.upload;

import com.torch.interfaces.common.exceptions.TorchException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

/**
 * Created by yuanj on 2017/6/13.
 */
@Service
public class ImageUploadService {

  public String GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
    if (StringUtils.isBlank(imgStr)) // 图像数据为空
    {
      throw new TorchException("上传图片为空");
    }

    if (!new File(imgFilePath).exists()) {
      new File(imgFilePath).mkdirs();
    }

    BASE64Decoder decoder = new BASE64Decoder();
    try {
      // Base64解码
      byte[] bytes = decoder.decodeBuffer(imgStr);
      for (int i = 0; i < bytes.length; ++i) {
        if (bytes[i] < 0) {// 调整异常数据
          bytes[i] += 256;
        }
      }
      String fileName = new DateTime().getMillis() + ".png";
      // 生成jpeg图片
      OutputStream out = new FileOutputStream(imgFilePath + fileName);
      out.write(bytes);
      out.flush();
      out.close();
      return imgFilePath + fileName;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return "";
  }

}
