package com.epam.esm.task5.service.dto.user;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.Objects;

public class UserDTO {

  @Null(message = "{user.id.null}")
  private Long id;

  @NotBlank(message = "{user.username.nonblank}")
  @Length(min = 6, max = 50, message = "{user.username.length}")
  private String username;

  @NotBlank(message = "{user.password.nonblank}")
  @Length(min = 6, max = 50, message = "{user.password.length}")
  private String password;

  private String role;

  public UserDTO() {}

  public UserDTO(Long id, String username, String password, String role) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.role = role;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserDTO userDTO = (UserDTO) o;
    return Objects.equals(id, userDTO.id)
        && Objects.equals(username, userDTO.username)
        && Objects.equals(password, userDTO.password)
        && Objects.equals(role, userDTO.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, password, role);
  }

  @Override
  public String toString() {
    return "UserDTO{"
        + "id="
        + id
        + ", username='"
        + username
        + '\''
        + ", password='"
        + password
        + '\''
        + ", role='"
        + role
        + '\''
        + '}';
  }
}
