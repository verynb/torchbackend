package com.torch.interfaces.user.internal.assembler;

import static java.util.Optional.of;

import com.torch.domain.model.school.School;
import com.torch.domain.model.user.DictVolunteerRole;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.user.facade.dto.SponsorDetailDto;
import com.torch.interfaces.user.facade.dto.SponsorDetailResultDto;
import com.torch.interfaces.user.facade.dto.VolunteerDetailDto;
import com.torch.interfaces.user.facade.dto.VolunteerDetailResultDto;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;

import com.torch.domain.model.user.User;
import com.torch.interfaces.user.facade.dto.UserAddDTO;
import com.torch.interfaces.user.facade.dto.UserDTO;
import com.torch.interfaces.user.facade.dto.UserGetDTO;

/**
 * 
 * 
 * @author service#yangle.org.cn
 * @date 2017年1月16日 下午12:23:10 
 *
 */
public class UserDTOAssembler {
	public static Optional<UserDTO> toOptionalDTO(Optional<User> user) {
		if (!user.isPresent())
			return Optional.empty();
		return of(toDTO(user.get()));
	}

	public static UserDTO toDTO(User user) {
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setName(user.getName());
		return dto;
	}

	public static UserAddDTO toUserAddDTO(User user) {
		UserAddDTO userAddDTO = new UserAddDTO();
		userAddDTO.setId(user.getId());
		return userAddDTO;
	}

	public static VolunteerDetailDto toUserGetDTO(User user,DictVolunteerRole role, List<School> schools) {
		VolunteerDetailDto dto = new VolunteerDetailDto();
		if (user==null) {
			return null;
		}
		BeanUtils.copyProperties(user, dto);
		dto.setJoinTime(user.getJoinTime().getMillis());
		dto.setRoleName(role==null? "":role.getRoleName());
		dto.setCodeMessage(new CodeMessage());
		dto.setSchools(schools);
		return dto;
	}

	public static VolunteerDetailResultDto toUserResultDTO(User user,DictVolunteerRole role) {
		VolunteerDetailResultDto dto = new VolunteerDetailResultDto();
		if (user==null) {
			return null;
		}
		BeanUtils.copyProperties(user, dto);
		dto.setJoinTime(user.getJoinTime().getMillis());
		dto.setRoleName(role==null? "":role.getRoleName());
		return dto;
	}

	public static SponsorDetailDto toSponsorDto(User user) {
		SponsorDetailDto dto = new SponsorDetailDto();
		if (user==null) {
			return null;
		}
		BeanUtils.copyProperties(user, dto);
		dto.setJoinTime(user.getJoinTime().getMillis());
		dto.setCodeMessage(new CodeMessage());
		return dto;
	}

	public static SponsorDetailResultDto toSponsorResultDto(User user) {
		SponsorDetailResultDto dto = new SponsorDetailResultDto();
		if (user==null) {
			return null;
		}
		BeanUtils.copyProperties(user, dto);
		dto.setJoinTime(user.getJoinTime().getMillis());
		return dto;
	}
}
