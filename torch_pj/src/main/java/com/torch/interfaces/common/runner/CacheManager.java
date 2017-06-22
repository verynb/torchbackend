package com.torch.interfaces.common.runner;

import com.google.common.collect.Maps;
import com.torch.interfaces.common.security.ApplicationSecuritySettings;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.torch.application.userGroup.UserGroupService;
import com.torch.domain.model.user.User;
import com.torch.domain.model.userGroup.UserGroup;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.torch.domain.model.apiRole.ApiRoleRepositoryJpa;
import com.torch.domain.model.userGroup.UserGroupRepositoryJpa;
import com.torch.interfaces.common.CommonService;
import com.torch.util.cache.RedisUtils;


@Service
@Component
@Transactional(readOnly = true)
public class CacheManager extends CommonService {

  Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private RedisUtils redisUtils;

  @Autowired
  private ApplicationSecuritySettings applicationSecuritySettings;

  public void setXXXAdminToken() {
    redisUtils.set("token/" + "xxxxx-admin", "admin");
  }
}
