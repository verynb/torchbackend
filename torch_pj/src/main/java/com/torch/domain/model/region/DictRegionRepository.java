package com.torch.domain.model.region;

import com.torch.domain.model.user.DictVolunteerRole;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by yuanj on 9/18/16.
 */
public interface DictRegionRepository extends CrudRepository<DictRegion, Long> {

  List<DictRegion> findByLevelType(int levelType);

  List<DictRegion> findByParentIdAndLevelType(Long parentId, int levelType);
}
