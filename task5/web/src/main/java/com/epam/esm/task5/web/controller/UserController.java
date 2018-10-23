package com.epam.esm.task5.web.controller;

import com.epam.esm.task5.service.UserService;
import com.epam.esm.task5.service.dto.ResponseMessage;
import com.epam.esm.task5.service.dto.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/users")
@CrossOrigin
public class UserController {

  private static final Integer SUCCESS_STATUS_CODE = 201;
  private static final String REGISTRATION_MESSAGE = "Registration was successful";

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Registrates a new valid user. Returns message of successful registration, uri of saved user and
   * http status CREATED.
   *
   * @param user valid user containing username and password for registration
   * @param ucb the UriComponentsBuilder Spring object for building uri of the created resource
   * @return the ResponseEntity with saved user, header with the uri of created resource and http
   *     status CREATED
   */
  @PostMapping(consumes = "application/json")
  public ResponseEntity<ResponseMessage> addUser(
      @RequestBody @Valid UserDTO user, UriComponentsBuilder ucb) {
    UserDTO savedUser = userService.signUp(user);

    HttpHeaders headers = new HttpHeaders();
    URI locationUri = ucb.path("/users/").path(String.valueOf(savedUser.getId())).build().toUri();
    headers.setLocation(locationUri);

    return new ResponseEntity<>(
        new ResponseMessage(SUCCESS_STATUS_CODE, REGISTRATION_MESSAGE),
        headers,
        HttpStatus.CREATED);
  }
}
