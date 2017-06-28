/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.application.contribute;

import com.torch.interfaces.homeVisit.dto.CreateHomeVisitCommand;
import java.util.List;

/**
 * @author yuanj 2017-01-18 14:31:25
 * @version 1.0
 */
public interface ContributeService {

  void contribute (Long batchId,List<Long> studentIds);


}