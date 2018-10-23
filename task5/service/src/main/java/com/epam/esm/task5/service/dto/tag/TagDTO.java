package com.epam.esm.task5.service.dto.tag;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class TagDTO {

  @Null(message = "{tag.id.null}")
  private Long id;

  @NotBlank(message = "{tag.name.nonblank}")
  @Length(min = 2, max = 50, message = "{tag.name.length}")
  @Pattern(regexp = "^[^,&?]+$", message = "{tag.name.pattern}")
  private String name;

  public TagDTO() {}

  public TagDTO(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TagDTO tag = (TagDTO) o;
    return Objects.equals(id, tag.id) && Objects.equals(name, tag.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "TagDTO [id=" + id + ", name=" + name + "]";
  }
}
