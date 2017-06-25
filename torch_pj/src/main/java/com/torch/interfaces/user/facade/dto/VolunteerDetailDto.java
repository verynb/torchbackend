package com.torch.interfaces.user.facade.dto;

import com.torch.domain.model.school.School;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

@ApiModel(value = "义工详细")
@Data
public class VolunteerDetailDto {
	private CodeMessage codeMessage;
	@NotNull
	@ApiModelProperty(value = "ID")
	private Long id;

	@ApiModelProperty(value = "姓名")
	private String name;

	@ApiModelProperty(value = "网名")
	private String netName;

	@ApiModelProperty(value = "年龄")
	private Integer age;

	@ApiModelProperty(value = "角色ID")
	private Long roleId;

	@ApiModelProperty(value = "角色名")
	private String roleName;

	@ApiModelProperty(value = "qq", required = true)
	private String qq;

	@ApiModelProperty(value = "邮箱", required = true)
	private String email;

	@ApiModelProperty(value = "手机", required = true)
	private String mobile;

	@ApiModelProperty(value = "省份", required = false)
	private String province;

	@ApiModelProperty(value = "市", required = false)
	private String city;

	@ApiModelProperty(value = "区", required = false)
	private String area;

	@ApiModelProperty(value = "详细地址", required = true)
	private String address;

	@ApiModelProperty(value = "加入时间", required = true)
	private String joinTime;

	@ApiModelProperty(value = "头像", required = true)
	private String photo;

	private List<School> schools;

}
