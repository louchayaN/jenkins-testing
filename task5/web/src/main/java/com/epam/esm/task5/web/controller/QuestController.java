package com.epam.esm.task5.web.controller;

import com.epam.esm.task5.repository.searchcriteria.QuestSearchCriteria;
import com.epam.esm.task5.repository.util.TokenUtil;
import com.epam.esm.task5.service.QuestService;
import com.epam.esm.task5.service.dto.Pagination;
import com.epam.esm.task5.service.dto.quest.QuestDTO;
import com.epam.esm.task5.service.dto.reservation.TimeSlot;
import com.epam.esm.task5.service.exception.QuestNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/quests")
@CrossOrigin
public class QuestController {

  private QuestService questService;

  @Autowired
  public QuestController(QuestService questService) {
    this.questService = questService;
  }

  private static final String EMPTY_STRING = "";

  /**
   * Adds a specified valid questDTO containing quest and its tags. Builds uri of saved resource.
   * Returns saved questDTO, its uri and http status CREATED.
   *
   * @param questDTO valid questDTO containing quest and its tags
   * @param ucb the UriComponentsBuilder Spring object for building uri of the created resource
   * @return the ResponseEntity with saved questDTO, header with the uri of created resource and
   *     http status CREATED
   */
  @PostMapping(consumes = "application/json")
  @PreAuthorize("#oauth2.hasScope('write')")
  public ResponseEntity<QuestDTO> addQuest(
      @RequestBody @Valid QuestDTO questDTO,
      UriComponentsBuilder ucb,
      Authentication authentication) {

    questDTO.setOwnerId(TokenUtil.getUserIdFromToken(authentication));
    QuestDTO savedQuest = questService.addQuestWithTags(questDTO);

    HttpHeaders headers = new HttpHeaders();
    URI locationUri = ucb.path("/quests/").path(String.valueOf(savedQuest.getId())).build().toUri();
    headers.setLocation(locationUri);

    return new ResponseEntity<>(savedQuest, headers, HttpStatus.CREATED);
  }

  /**
   * Returns a quest with the specified id and its tags. If quest with the specified id doesn't
   * exist throws QuestNotFoundException.
   *
   * @param id of searching quest
   * @return the quest found by id
   * @throws QuestNotFoundException if no quests with the specified id were found
   */
  @GetMapping(value = "/{id}", produces = "application/json")
  public QuestDTO getQuestById(@PathVariable Long id) {
    QuestDTO questWithTags = questService.getQuest(id);
    if (questWithTags == null) {
      throw new QuestNotFoundException(id);
    }
    return questWithTags;
  }

  /**
   * Returns quests with their tags found by the specified search criteria and in according with the
   * specified in criteria pagination options.
   *
   * @param criteria the search criteria that consists optional parameters for quest search and
   *     parameters for pagination
   * @return the quests with their tags found by criteria for current page
   */
  @GetMapping(produces = "application/json")
  public Pagination<QuestDTO> getQuests(@Valid QuestSearchCriteria criteria) {
    List<String> tags = criteria.getTags();
    if (tags != null) {
      tags.removeIf(tagName -> tagName == null || EMPTY_STRING.equals(tagName.trim()));
    }

    return questService.getQuestsBySearchCriteria(criteria);
  }

  @GetMapping(value = "/{id}/schedules", produces = "application/json")
  public List<TimeSlot> getQuestSchedule(
      @PathVariable Long id, @RequestParam(value = "reservedDay") Long day) {
    return questService.getQuestScheduleForDay(id, day);
  }

  /**
   * Updates quest with the specified id and its tags. If quest with the specified id doesn't exist
   * throws QuestNotFoundException.
   *
   * @param id of the quest for updating
   * @param questDTO the valid quest with tags
   * @return the ResponseEntity with the updated object and http status OK
   * @throws QuestNotFoundException if quest with the specified id doesn't exist
   */
  @PutMapping(value = "/{id}", produces = "application/json")
  @PreAuthorize("#oauth2.hasScope('write')")
  public ResponseEntity<QuestDTO> updateQuest(
      @PathVariable Long id, @RequestBody @Valid QuestDTO questDTO, Authentication authentication) {
    if (!questService.isQuestIdExists(id)) {
      throw new QuestNotFoundException(id);
    }

    questDTO.setId(id);
    questDTO.setOwnerId(TokenUtil.getUserIdFromToken(authentication));
    QuestDTO updatedQuest = questService.updateQuestWithTagsById(questDTO);
    return new ResponseEntity<>(updatedQuest, HttpStatus.OK);
  }

  /**
   * Deletes quest with the specified id.
   *
   * @param id the id of quest for deletion
   * @return the ResponseEntity with http status NO_CONTENT
   */
  @DeleteMapping(value = "/{id}", produces = "application/json")
  @PreAuthorize("#oauth2.hasScope('write')")
  public ResponseEntity<QuestDTO> deleteQuest(
      @PathVariable Long id, Authentication authentication) {
    Long ownerId = TokenUtil.getUserIdFromToken(authentication);
    questService.deleteQuestById(id, ownerId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
