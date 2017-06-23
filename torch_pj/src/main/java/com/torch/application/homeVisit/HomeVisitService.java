/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.application.homeVisit;

import com.torch.interfaces.homeVisit.dto.CreateHomeVisitCommand;
import com.torch.interfaces.release.AddReleaseCommand;
import com.torch.interfaces.release.AddReleaseStudentCommand;
import java.util.List;

/**
 * @author yuanj 2017-01-18 14:31:25
 * @version 1.0
 */
public interface HomeVisitService {


  public void saveHomeVisit(CreateHomeVisitCommand command);

}