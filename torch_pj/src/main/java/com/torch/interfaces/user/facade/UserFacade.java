/**
 * Copyright (c) 2017 
 *	成都博智云创科技有限公司.
 */
package com.torch.interfaces.user.facade;

import com.torch.interfaces.user.command.SponsorAddCommand;
import com.torch.interfaces.user.command.SponsorUpdateCommand;
import com.torch.interfaces.user.command.VolunteerAddCommand;
import com.torch.interfaces.user.command.VolunteerUpdateCommand;
import com.torch.interfaces.user.facade.dto.SponsorDetailDto;
import com.torch.interfaces.user.facade.dto.UserAddDTO;
import com.torch.interfaces.user.facade.dto.VolunteerDetailDto;

/**
 * 
 * @author yuanj 2017-01-12 18:12:07
 * @version 1.0
 */
public interface UserFacade {

	UserAddDTO addVolunteer(VolunteerAddCommand command);

	void updateVolunteer(VolunteerUpdateCommand command);

	void deleteUser(Long id);

	VolunteerDetailDto getUser(Long id);

	SponsorDetailDto getSponsorDetail(Long id);

	void updatePassword(Long id ,String password);

	UserAddDTO addSponsor(SponsorAddCommand command);

	void updateSponsor(SponsorUpdateCommand command);
}
