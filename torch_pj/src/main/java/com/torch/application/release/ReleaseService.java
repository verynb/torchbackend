/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.application.release;

import com.torch.domain.model.school.School;
import com.torch.interfaces.release.AddReleaseCommand;
import com.torch.interfaces.release.AddReleaseStudentCommand;
import com.torch.interfaces.release.ReleaseStudentDto;
import com.torch.interfaces.school.AddSchoolCommand;
import com.torch.interfaces.school.UpdateSchoolCommand;
import java.util.List;

/**
 * @author yuanj 2017-01-18 14:31:25
 * @version 1.0
 */
public interface ReleaseService {

  Long addRelease(AddReleaseCommand command);

  void addReleaseStudent(List<AddReleaseStudentCommand> commands);

  void deleteReleaseStudent(Long id);

  void release(Long batchId, List<ReleaseStudentDto> releaseStudentIds);

}