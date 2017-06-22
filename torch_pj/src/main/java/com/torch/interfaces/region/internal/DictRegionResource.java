/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.interfaces.region.internal;

import static com.torch.interfaces.common.ApiPaths.API_CONTEXT_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.google.common.collect.Lists;
import com.torch.domain.model.region.DictRegion;
import com.torch.domain.model.region.DictRegionRepository;
import com.torch.domain.model.user.DictVolunteerRole;
import com.torch.domain.model.user.DictVolunteerRoleRepository;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.common.security.annotation.RoleCheck;
import com.torch.interfaces.region.internal.dto.RegionDto;
import com.torch.interfaces.region.internal.dto.RegionList;
import com.torch.interfaces.user.internal.VolunteerRoleDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuanj 2017-01-12 16:41:05
 * @version 1.0
 */
@RestController
@RequestMapping(value = API_CONTEXT_PATH + "/regions", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"DictRegionResource-api-1.0"}, description = "地域分区，{省，市，区}")
public class DictRegionResource {

  private final DictRegionRepository dictRegionRepository;

  @Autowired
  public DictRegionResource(final DictRegionRepository dictRegionRepository) {
    this.dictRegionRepository = dictRegionRepository;
  }

  @RoleCheck
  @ApiOperation(value = "查询全国所有的省份")
  @RequestMapping(value = "/provinces", method = RequestMethod.GET)
  public RegionList getAllProvince() {
    List<DictRegion> provinces = dictRegionRepository.findByLevelType(1);
    List<RegionDto> result = Lists.newArrayList();
    provinces.forEach(p -> {
      result.add(RegionDto.builder()
          .id(p.getId())
          .name(p.getName())
          .build());
    });
    return RegionList.builder()
        .codeMessage(new CodeMessage())
        .regionDtoList(result)
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "查询某个省下面的所有的市")
  @RequestMapping(value = "cities/{parentId}", method = RequestMethod.GET)
  public RegionList getAllCities(@PathVariable("parentId") Long parentId) {
    List<DictRegion> cities = dictRegionRepository.findByParentIdAndLevelType(parentId, 2);
    List<RegionDto> result = Lists.newArrayList();
    cities.forEach(city -> {
      result.add(RegionDto.builder()
          .id(city.getId())
          .name(city.getName())
          .build());
    });
    return RegionList.builder()
        .codeMessage(new CodeMessage())
        .regionDtoList(result)
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "查询某个市下面的所有的区/县")
  @RequestMapping(value = "areas/{parentId}", method = RequestMethod.GET)
  public RegionList getAllAreas(@PathVariable("parentId") Long parentId) {
    List<DictRegion> areas = dictRegionRepository.findByParentIdAndLevelType(parentId, 3);
    List<RegionDto> result = Lists.newArrayList();
    areas.forEach(area -> {
      result.add(RegionDto.builder()
          .id(area.getId())
          .name(area.getName())
          .build());
    });
    return RegionList.builder()
        .codeMessage(new CodeMessage())
        .regionDtoList(result)
        .build();
  }
}
