package com.epam.esm.task5.service;

import com.epam.esm.task5.repository.UserRepository;
import com.epam.esm.task5.repository.entity.Role;
import com.epam.esm.task5.repository.entity.User;
import com.epam.esm.task5.repository.specification.user.GetUserByUsernameSpecification;
import com.epam.esm.task5.service.dto.user.UserDTO;
import com.epam.esm.task5.service.exception.UserNameDuplicationException;
import com.epam.esm.task5.service.exception.UserNotFoundException;
import com.epam.esm.task5.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

  @Mock private UserRepository userRepository;

  @Mock private PasswordEncoder passwordEncoder;

  @InjectMocks private UserServiceImpl userService;

  private User user;
  private UserDTO userDTO;

  @Before
  public void doSetup() {
    MockitoAnnotations.initMocks(this);

    user = new User();
    user.setUsername("username");
    user.setPassword("password");
    Role role = new Role();
    role.setId(1);
    role.setName("USER_ROLE");
    user.setRole(role);

    userDTO = new UserDTO();
    userDTO.setUsername("username");
    userDTO.setPassword("password");
  }

  @Test
  public void loadUserByUsername_MethodCalled() {
    when(userRepository.getOne(any(GetUserByUsernameSpecification.class))).thenReturn(user);

    userService.loadUserByUsername(user.getUsername());

    verify(userRepository, times(1)).getOne(any(GetUserByUsernameSpecification.class));
  }

  @Test(expected = UserNotFoundException.class)
  public void loadUserByUsername_ThrowException_IfUserNotFound() {
    when(userRepository.getOne(any(GetUserByUsernameSpecification.class))).thenReturn(null);

    userService.loadUserByUsername(user.getUsername());
  }

  @Test(expected = UserNameDuplicationException.class)
  public void signUp_ThrowException_IfUsernameExists() {
    UserService spy = spy(userService);
    doReturn(true).when(spy).isUsernameExists(userDTO.getUsername());

    spy.signUp(userDTO);
  }

}
