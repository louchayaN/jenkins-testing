package com.epam.esm.task5.service.util.mapper;

import com.epam.esm.task5.repository.entity.Tag;
import com.epam.esm.task5.service.dto.tag.TagDTO;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TagModelMapper {

  private TagModelMapper() {
    throw new AssertionError("Class contains static methods only");
  }

  public static TagDTO mapTagToTagDto(Tag tag) {
    return tag == null ? null : new ModelMapper().map(tag, TagDTO.class);
  }

  public static List<TagDTO> mapTagsToTagsDTO(List<Tag> tags) {
    if (tags == null) {
      return Collections.emptyList();
    }

    return tags.stream()
        .map(tag -> new ModelMapper().map(tag, TagDTO.class))
        .collect(Collectors.toList());
  }

  public static Tag mapTagDtoToTag(TagDTO tagDTO) {
    return tagDTO == null ? null : new ModelMapper().map(tagDTO, Tag.class);
  }
}
