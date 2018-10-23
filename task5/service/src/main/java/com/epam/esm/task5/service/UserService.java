package com.epam.esm.task5.service;

import com.epam.esm.task5.service.dto.user.UserDTO;

public interface UserService {

  UserDTO signUp(UserDTO user);

  boolean isUsernameExists(String username);
}
