package com.epam.esm.task5.service.dto.user;

import java.util.Objects;

public class UserInfoDTO {

  private Long id;

  private String username;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserInfoDTO that = (UserInfoDTO) o;
    return Objects.equals(id, that.id) && Objects.equals(username, that.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username);
  }

  @Override
  public String toString() {
    return "UserInfoDTO{" + "id=" + id + ", username='" + username + '\'' + '}';
  }
}
