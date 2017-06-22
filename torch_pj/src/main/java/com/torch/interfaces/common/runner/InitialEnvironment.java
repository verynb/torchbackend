package com.torch.interfaces.common.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author service#yangle.org.cn
 * @date 2017年1月19日 下午5:10:02
 */
@Component
@Order(value = 1)
public class InitialEnvironment implements CommandLineRunner {

  Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private CacheManager cacheManager;

  @Override
  public void run(String... args) throws Exception {
    cacheManager.setXXXAdminToken();
    logger.info(">>>>>>>>>>>>>>>设置超级用户token完成<<<<<<<<<<<<<");
  }

}