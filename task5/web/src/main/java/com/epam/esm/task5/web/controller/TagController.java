package com.epam.esm.task5.web.controller;

import com.epam.esm.task5.repository.entity.Tag;
import com.epam.esm.task5.repository.searchcriteria.TagSearchCriteria;
import com.epam.esm.task5.service.TagService;
import com.epam.esm.task5.service.dto.Pagination;
import com.epam.esm.task5.service.dto.tag.TagDTO;
import com.epam.esm.task5.web.exception.TagNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/tags")
@CrossOrigin
public class TagController {

  private TagService tagService;

  @Autowired
  public TagController(TagService tagService) {
    this.tagService = tagService;
  }

  /**
   * Adds a new specified valid tag. Builds uri represented saved resource.
   *
   * @param tag the valid tag
   * @param ucb the UriComponentsBuilder Spring object for building uri of the created resource
   * @return the ResponseEntity with saved tag, header with built uri of created resource and http
   *     status CREATED
   */
  @PostMapping(consumes = "application/json")
  @PreAuthorize("#oauth2.hasScope('write')")
  public ResponseEntity<TagDTO> addTag(@RequestBody @Valid TagDTO tag, UriComponentsBuilder ucb) {
    TagDTO savedTag = tagService.addTag(tag);

    HttpHeaders headers = new HttpHeaders();
    URI locationUri = ucb.path("/tags/").path(String.valueOf(savedTag.getId())).build().toUri();
    headers.setLocation(locationUri);

    return new ResponseEntity<>(savedTag, headers, HttpStatus.CREATED);
  }

  /**
   * Returns the tag by specified id. If tag was not found throws TagNotFoundException
   *
   * @param id of searching tag
   * @return the tag found by id
   * @throws TagNotFoundException if tag wasn't found by the specified id
   */
  @PreAuthorize("#oauth2.hasScope('read')")
  @GetMapping(value = "/{id}", produces = "application/json")
  public TagDTO getTagById(@PathVariable Long id) {
    TagDTO tag = tagService.getTagById(id);
    if (tag == null) {
      throw new TagNotFoundException(id);
    }
    return tag;
  }

  /**
   * Returns portion of tags for current page and total count of tags found by the specified search
   * criteria.
   *
   * @return portion of found tags for current page and total count of found tags
   */
  @GetMapping(produces = "application/json")
  @PreAuthorize("#oauth2.hasScope('read')")
  public Pagination<TagDTO> getTags(@Valid TagSearchCriteria criteria) {
    return tagService.getTagsBySearchCriteria(criteria);
  }

  /**
   * Deletes tag by the specified id.
   *
   * @param id of tag for deletion
   * @return the ResponseEntity with http status NO_CONTENT
   */
  @DeleteMapping(value = "/{id}", produces = "application/json")
  @PreAuthorize("#oauth2.hasScope('write')")
  public ResponseEntity<Tag> deleteTag(@PathVariable Long id) {
    tagService.deleteTagById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
