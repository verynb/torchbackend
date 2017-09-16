package com.torch.interfaces.user.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

@ApiModel(value = "修改义工参数")
@Data
public class VolunteerUpdateCommand {
	@NotNull
	@ApiModelProperty(value = "ID", required = true)
	private Long id;

	@NotBlank(message = "user.username.required")
	@ApiModelProperty(value = "姓名", required = true)
	private String name;

	@ApiModelProperty(value = "网名", required = true)
	private String netName;

	@ApiModelProperty(value = "年龄", required = true)
	private Integer age;

	@ApiModelProperty(value = "角色", required = true)
	private Long roleId;

	@ApiModelProperty(value = "QQ", required = true)
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

	@ApiModelProperty(value = "如果有跟新则将跟新后的学校IDs传如", required = true)
	private List<Long> schoolIds;

	//新增如下字段
	@ApiModelProperty(value = "性别，（m-男，w-女）", required = false)
	private String gender;

	@ApiModelProperty(value = "特长", required = false)
	private String speciality;

	@ApiModelProperty(value = "备注", required = false)
	private String remark;

	private Integer status;//0正常1冻结

}
