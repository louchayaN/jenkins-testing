package com.epam.esm.task5.service.impl;

import com.epam.esm.task5.repository.UserRepository;
import com.epam.esm.task5.repository.entity.Role;
import com.epam.esm.task5.repository.entity.User;
import com.epam.esm.task5.repository.specification.user.GetUserByUsernameSpecification;
import com.epam.esm.task5.repository.specification.user.IsUsernameExistsSpecification;
import com.epam.esm.task5.service.UserService;
import com.epam.esm.task5.service.dto.user.CustomUserDetails;
import com.epam.esm.task5.service.dto.user.UserDTO;
import com.epam.esm.task5.service.exception.UserNotFoundException;
import com.epam.esm.task5.service.exception.UserNameDuplicationException;
import com.epam.esm.task5.service.util.mapper.UserModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

  private static final Integer ROLE_USER_ID = 1;

  private UserRepository userRepository;

  private PasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = userRepository.getOne(new GetUserByUsernameSpecification(username));
    if (user == null) {
      throw new UserNotFoundException("Invalid username or password");
    }
    return new CustomUserDetails(user);
  }

  @Override
  public UserDTO signUp(UserDTO userDTO) {
    if (isUsernameExists(userDTO.getUsername())) {
      throw new UserNameDuplicationException(userDTO.getUsername());
    }

    User user = UserModelMapper.mapUserDtoToUser(userDTO);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    Role role = new Role();
    role.setId(ROLE_USER_ID);
    user.setRole(role);

    User savedUser = userRepository.save(user);
    return new UserDTO(
        savedUser.getId(), savedUser.getUsername(), null, savedUser.getRole().getName());
  }

  @Override
  public boolean isUsernameExists(String username) {
    return userRepository.isExists(new IsUsernameExistsSpecification(username));
  }
}
