/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.application.school;

import com.torch.domain.model.school.School;
import com.torch.interfaces.school.AddSchoolCommand;
import com.torch.interfaces.school.UpdateSchoolCommand;

/**
 *
 * @author yuanj 2017-01-18 14:31:25
 * @version 1.0
 */
public interface SchoolService {

  School addSchool(AddSchoolCommand command);

  School updateSchool(UpdateSchoolCommand command);

  School getSchoolDetail(Long id);

  void deleteSchool(Long id);

}