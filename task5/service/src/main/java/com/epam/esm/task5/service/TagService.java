package com.epam.esm.task5.service;

import com.epam.esm.task5.repository.entity.Tag;
import com.epam.esm.task5.repository.searchcriteria.TagSearchCriteria;
import com.epam.esm.task5.service.dto.Pagination;
import com.epam.esm.task5.service.dto.tag.TagDTO;

import java.util.Set;

public interface TagService {

  TagDTO addTag(TagDTO tag);

  TagDTO getTagById(Long id);

  Set<Tag> getTagsByName(Set<Tag> tags);

  Pagination<TagDTO> getTagsBySearchCriteria(TagSearchCriteria criteria);

  boolean isTagNameExists(String name);

  void deleteTagById(Long id);
}
