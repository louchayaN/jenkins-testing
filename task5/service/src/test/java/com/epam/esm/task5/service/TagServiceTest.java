package com.epam.esm.task5.service;

import com.epam.esm.task5.repository.entity.Tag;
import com.epam.esm.task5.repository.impl.TagRepositoryImpl;
import com.epam.esm.task5.repository.searchcriteria.PaginationCriteria;
import com.epam.esm.task5.repository.searchcriteria.TagSearchCriteria;
import com.epam.esm.task5.repository.specification.tag.CountAllTagsSpecification;
import com.epam.esm.task5.repository.specification.tag.GetAllTagsSpecification;
import com.epam.esm.task5.repository.specification.tag.GetTopTagOfUserWithTopReservationsSpecification;
import com.epam.esm.task5.repository.specification.tag.IsTagNameExistsSpecification;
import com.epam.esm.task5.service.dto.Pagination;
import com.epam.esm.task5.service.dto.tag.TagDTO;
import com.epam.esm.task5.service.exception.TagNameDuplicationException;
import com.epam.esm.task5.service.impl.TagServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TagServiceTest {

  @Mock private TagRepositoryImpl tagRepository;

  @InjectMocks private TagServiceImpl tagService;

  private static final Long ID = 1L;

  private Tag tag;
  private List<Tag> tags;

  private TagDTO tagDTO;
  private List<TagDTO> tagsDTO;

  @Before
  public void doSetup() {
    MockitoAnnotations.initMocks(this);

    tag = new Tag(1L, "sport");
    tags = new ArrayList<>();
    tags.add(tag);

    tagDTO = new TagDTO(1L, "sport");
    tagsDTO = new ArrayList<>();
    tagsDTO.add(tagDTO);
  }

  @Test(expected = TagNameDuplicationException.class)
  public void addTag_ThrowException_IfTagNameExists() {
    TagService spy = spy(tagService);
    doReturn(true).when(spy).isTagNameExists(tagDTO.getName());

    spy.addTag(tagDTO);
  }

  @Test
  public void addTag_MethodCalled() {
    tagService.addTag(tagDTO);

    verify(tagRepository, times(1)).save(tag);
  }

  @Test
  public void getTagById_MethodCalled() {
    tagService.getTagById(ID);

    verify(tagRepository, times(1)).getById(Tag.class, ID);
  }

  @Test
  public void getTagsBySearchCriteria_GetTopMethodCalled_IfCostCriteria() {
    TagSearchCriteria criteriaWithCost = new TagSearchCriteria();
    criteriaWithCost.setTop("cost");

    tagService.getTagsBySearchCriteria(criteriaWithCost);

    verify(tagRepository, times(1)).get(any(GetTopTagOfUserWithTopReservationsSpecification.class));
  }

  @Test
  public void getTagsBySearchCriteria_ReturnPaginationWithEmptyList_IfZeroTagCount() {
    TagSearchCriteria criteria = new TagSearchCriteria();
    Long totalTagCount = 0L;
    when(tagRepository.count(any(CountAllTagsSpecification.class))).thenReturn(totalTagCount);
    Pagination<TagDTO> expectedPagination =
        new Pagination<>(Collections.emptyList(), totalTagCount);

    Pagination<TagDTO> actualPagination = tagService.getTagsBySearchCriteria(criteria);

    assertEquals(expectedPagination, actualPagination);
  }

  @Test
  public void getTagsBySearchCriteria_ReturnPaginationWithTagList_IfNotZeroTagCount() {
    TagSearchCriteria criteria = new TagSearchCriteria();
    Long totalTagCount = 1L;
    when(tagRepository.count(any(CountAllTagsSpecification.class))).thenReturn(totalTagCount);
    when(tagRepository.getForPage(
            any(GetAllTagsSpecification.class), any(PaginationCriteria.class)))
        .thenReturn(tags);
    Pagination<TagDTO> expectedPagination = new Pagination<>(tagsDTO, totalTagCount);

    Pagination<TagDTO> actualPagination = tagService.getTagsBySearchCriteria(criteria);

    assertEquals(expectedPagination, actualPagination);
  }

  @Test
  public void isTagNameExists_MethodCalled() {
    tagService.isTagNameExists(tagDTO.getName());

    verify(tagRepository, times(1)).isExists(any(IsTagNameExistsSpecification.class));
  }

  @Test
  public void deleteTagById_MethodCalled() {
    tagService.deleteTagById(ID);

    verify(tagRepository, times(1)).deleteById(Tag.class, ID);
  }
}
