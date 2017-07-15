/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.application.returnVisit;

import com.torch.interfaces.release.AddReleaseCommand;
import com.torch.interfaces.release.AddReleaseStudentCommand;
import com.torch.interfaces.release.CreateCreditDto;
import com.torch.interfaces.release.ReleaseStudentDto;
import com.torch.interfaces.returnVisit.dto.CreateReturnVisitDto;
import com.torch.interfaces.returnVisit.dto.ReturnVisitDetail;
import java.util.List;

/**
 * @author yuanj 2017-01-18 14:31:25
 * @version 1.0
 */
public interface ReturnVisitService {

  Long createReturnVisti(CreateReturnVisitDto dto);

  ReturnVisitDetail getDetail(Long id);

}