/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.application.homeVisit.impl;

import com.torch.application.homeVisit.HomeVisitService;
import com.torch.domain.model.homeVisit.HomeVisit;
import com.torch.domain.model.homeVisit.HomeVisitAuditItem;
import com.torch.domain.model.homeVisit.HomeVisitAuditItemRepository;
import com.torch.domain.model.homeVisit.HomeVisitRepository;
import com.torch.domain.model.release.ReleaseRepository;
import com.torch.domain.model.release.ReleaseStudentRepository;
import com.torch.domain.model.student.StudentRepository;
import com.torch.interfaces.homeVisit.dto.CreateHomeVisitCommand;
import java.beans.Transient;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yuanj 2017-01-18 14:31:25
 * @version 1.0
 */
@Service
public class HomeVisitServiceImpl implements HomeVisitService {

  private final HomeVisitAuditItemRepository homeVistAuditItemRepository;

  private final HomeVisitRepository homeVisitRepository;


  @Autowired
  public HomeVisitServiceImpl(final HomeVisitAuditItemRepository homeVistAuditItemRepository,
      final HomeVisitRepository homeVisitRepository) {
    this.homeVistAuditItemRepository = homeVistAuditItemRepository;
    this.homeVisitRepository = homeVisitRepository;
  }

  @Override
  @Transient
  public void saveHomeVisit(CreateHomeVisitCommand command) {
    HomeVisit homeVisit = HomeVisit.builder()
        .batchId(command.getBatchId())
        .applicationForm(buildPhtot(command.getApplicationForms()))
        .familyPhoto(buildPhtot(command.getFamilyPhotos()))
        .homePhoto(buildPhtot(command.getHomePhotos()))
        .InteractivePhoto(buildPhtot(command.getInteractivePhotos()))
        .studentId(command.getStudentId())
        .studentPhoto(buildPhtot(command.getStudentPhotos()))
        .visitInfo(command.getVisitInfo())
        .build();
    homeVisitRepository.save(homeVisit);
    command.getAuditItemIds().forEach(item -> {
      HomeVisitAuditItem newItem = HomeVisitAuditItem.builder()
          .batchId(command.getBatchId())
          .homeVisitId(homeVisit.getId())
          .auditItemId(item)
          .build();
      homeVistAuditItemRepository.save(newItem);
    });

  }

  private String buildPhtot(List<String> phtots) {
    String resultStr = "";
    if (CollectionUtils.isNotEmpty(phtots)) {
      for (String photo : phtots) {
        resultStr += photo + ",";
      }
    }
    return resultStr;
  }
}
