package com.torch.application.user;

import com.torch.domain.model.user.User;

import com.torch.interfaces.user.command.SponsorAddCommand;
import com.torch.interfaces.user.command.SponsorUpdateCommand;
import com.torch.interfaces.user.command.VolunteerAddCommand;
import com.torch.interfaces.user.command.VolunteerUpdateCommand;
import java.util.Optional;

/**
 * Created by yuanj on 9/21/16.
 */
public interface UserService {

  Optional<User> authenticate(String phone, String password);

  Long addVolunteer(VolunteerAddCommand command);

  Long addSponsor(SponsorAddCommand command);

  void updateVolunteer(VolunteerUpdateCommand command);

  void updateSponsor(SponsorUpdateCommand command);

}
