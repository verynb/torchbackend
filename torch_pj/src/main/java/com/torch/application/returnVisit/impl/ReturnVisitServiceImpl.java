/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.application.returnVisit.impl;

import com.google.common.collect.Lists;
import com.torch.application.returnVisit.ReturnVisitService;
import com.torch.domain.model.homeVisit.HomeVisitRepository;
import com.torch.domain.model.release.CreditRepository;
import com.torch.domain.model.release.ReleaseRepository;
import com.torch.domain.model.release.ReleaseStudentRepository;
import com.torch.domain.model.returnVisit.ReturnVisit;
import com.torch.domain.model.returnVisit.ReturnVisitRepository;
import com.torch.domain.model.student.StudentRepository;
import com.torch.domain.model.user.User;
import com.torch.domain.model.user.UserRepository;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.common.security.Session;
import com.torch.interfaces.returnVisit.dto.CreateReturnVisitDto;
import com.torch.interfaces.returnVisit.dto.ReturnVisitDetail;
import com.torch.util.cache.RedisUtils;

import java.util.Collections;
import java.util.List;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yuanj 2017-01-18 14:31:25
 * @version 1.0
 */
@Service
public class ReturnVisitServiceImpl implements ReturnVisitService {

    private final ReturnVisitRepository returnVisitRepository;

    private final UserRepository userRepository;


    @Autowired
    public ReturnVisitServiceImpl(final ReturnVisitRepository returnVisitRepository,
                                  final UserRepository userRepository) {
        this.returnVisitRepository = returnVisitRepository;
        this.userRepository = userRepository;
    }


    @Override
    @Transient
    public Long createReturnVisti(CreateReturnVisitDto dto) {
        ReturnVisit returnVisit = ReturnVisit.builder()
                .returnInfo(dto.getReturnInfo())
                .studentPhotos(buildPhtot(dto.getStudentPhotos()))
                .environmentPhotos(buildPhtot(dto.getEnvironmentPhotos()))
                .familyPhotos(buildPhtot(dto.getFamilyPhotos()))
                .returnTime(new DateTime())
                .returnVisitor(Session.getUserId())
                .studentId(dto.getStudentId())
                .build();
        returnVisitRepository.save(returnVisit);
        return returnVisit.getId();
    }

    @Override
    public ReturnVisitDetail getDetail(Long id) {
        ReturnVisit returnVisit = returnVisitRepository.findOne(id);
        if (returnVisit == null) {
            return ReturnVisitDetail.builder()
                    .codeMessage(new CodeMessage())
                    .build();
        }
        User user = userRepository.findOne(returnVisit.getReturnVisitor() == null ? 0l : returnVisit.getReturnVisitor());
        return ReturnVisitDetail.builder()
                .returnInfo(returnVisit.getReturnInfo())
                .familyPhotos(buildPhotoToList(returnVisit.getFamilyPhotos()))
                .environmentPhotos(buildPhotoToList(returnVisit.getEnvironmentPhotos()))
                .studentPhotos(buildPhotoToList(returnVisit.getStudentPhotos()))
                .returnTime(
                        returnVisit.getReturnTime() == null ? "" : returnVisit.getReturnTime().toString("yyyy-MM-dd HH:mm:ss"))
                .returnVisitor(user == null ? "" : user.getName())
                .codeMessage(new CodeMessage())
                .build();
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


    private List<String> buildPhotoToList(String photo) {
        if (StringUtils.isBlank(photo)) {
            return Collections.EMPTY_LIST;
        }
        String[] arr = photo.split(",");
        List<String> list = Lists.newArrayList();
        for (int i = 0; i < arr.length; i++) {
            if (!StringUtils.isBlank(arr[i])) {
                list.add(arr[i]);
            }

        }
        return list;
    }
}
