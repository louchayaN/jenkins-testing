package com.epam.esm.task5.service.util.mapper;

import com.epam.esm.task5.repository.entity.User;
import com.epam.esm.task5.service.dto.user.UserDTO;
import com.epam.esm.task5.service.dto.user.UserInfoDTO;
import org.modelmapper.ModelMapper;

public class UserModelMapper {

  private UserModelMapper() {
    throw new AssertionError("Class contains static methods only");
  }

  public static User mapUserDtoToUser(UserDTO userDTO) {
    return userDTO == null ? null : new ModelMapper().map(userDTO, User.class);
  }

  public static UserInfoDTO mapUserToUserInfoDto(User user) {
    return user == null ? null : new ModelMapper().map(user, UserInfoDTO.class);
  }
}
