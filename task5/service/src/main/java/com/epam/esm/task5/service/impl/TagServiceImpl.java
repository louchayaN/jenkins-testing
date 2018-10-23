package com.epam.esm.task5.service.impl;

import com.epam.esm.task5.repository.TagRepository;
import com.epam.esm.task5.repository.entity.Tag;
import com.epam.esm.task5.repository.searchcriteria.PaginationCriteria;
import com.epam.esm.task5.repository.searchcriteria.TagSearchCriteria;
import com.epam.esm.task5.repository.specification.tag.*;
import com.epam.esm.task5.service.TagService;
import com.epam.esm.task5.service.dto.Pagination;
import com.epam.esm.task5.service.dto.tag.TagDTO;
import com.epam.esm.task5.service.dto.tag.TopTagSearch;
import com.epam.esm.task5.service.exception.TagNameDuplicationException;
import com.epam.esm.task5.service.util.PaginationUtil;
import com.epam.esm.task5.service.util.mapper.TagModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TagServiceImpl implements TagService {

  private TagRepository tagRepository;

  @Autowired
  public TagServiceImpl(TagRepository tagRepository) {
    this.tagRepository = tagRepository;
  }

  @Override
  public TagDTO addTag(TagDTO tagDTO) {
    tagDTO.setName(tagDTO.getName().toLowerCase());
    if (isTagNameExists(tagDTO.getName())) {
      throw new TagNameDuplicationException(tagDTO.getName());
    }

    Tag tag = TagModelMapper.mapTagDtoToTag(tagDTO);
    return TagModelMapper.mapTagToTagDto(tagRepository.save(tag));
  }

  @Override
  public TagDTO getTagById(Long id) {
    return TagModelMapper.mapTagToTagDto(tagRepository.getById(Tag.class, id));
  }

  @Override
  public Set<Tag> getTagsByName(Set<Tag> tags) {
    Set<Tag> tagsWithIds = new HashSet<>();
    for (Tag tag : tags) {
      List<Tag> foundTags = tagRepository.get(new GetTagByNameSpecification(tag.getName()));
      if (foundTags.isEmpty()) {
        tagsWithIds.add(tag);
      } else {
        tagsWithIds.add(foundTags.get(0));
      }
    }
    return tagsWithIds;
  }

  @Override
  public Pagination<TagDTO> getTagsBySearchCriteria(TagSearchCriteria criteria) {
    if (TopTagSearch.COST.name().equalsIgnoreCase(criteria.getTop())) {
      List<Tag> topTags = tagRepository.get(new GetTopTagOfUserWithTopReservationsSpecification());
      return new Pagination<>(TagModelMapper.mapTagsToTagsDTO(topTags), (long) topTags.size());
    }

    Long totalTagCount = tagRepository.count(new CountAllTagsSpecification());
    if (totalTagCount == 0L) {
      return new Pagination<>(Collections.emptyList(), totalTagCount);
    }

    PaginationCriteria pagination = PaginationUtil.setDefaultPagesIfNotValid(criteria);

    List<Tag> tagsForPage = tagRepository.getForPage(new GetAllTagsSpecification(), pagination);
    return new Pagination<>(TagModelMapper.mapTagsToTagsDTO(tagsForPage), totalTagCount);
  }

  @Override
  public boolean isTagNameExists(String name) {
    return tagRepository.isExists(new IsTagNameExistsSpecification(name));
  }

  @Override
  public void deleteTagById(Long id) {
    tagRepository.deleteById(Tag.class, id);
  }
}
