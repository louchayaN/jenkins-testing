package com.epam.esm.task5.service.impl;

import com.epam.esm.task5.repository.QuestRepository;
import com.epam.esm.task5.repository.entity.Quest;
import com.epam.esm.task5.repository.entity.Tag;
import com.epam.esm.task5.repository.searchcriteria.PaginationCriteria;
import com.epam.esm.task5.repository.searchcriteria.QuestSearchCriteria;
import com.epam.esm.task5.repository.specification.quest.CountQuestsbySearchCriteriaSpecification;
import com.epam.esm.task5.repository.specification.quest.GetQuestsBySearchCriteriaSpecification;
import com.epam.esm.task5.repository.specification.quest.IsQuestBelongToOwnerSpecification;
import com.epam.esm.task5.repository.specification.quest.IsQuestIdExistsSpecification;
import com.epam.esm.task5.service.QuestService;
import com.epam.esm.task5.service.ReservationService;
import com.epam.esm.task5.service.TagService;
import com.epam.esm.task5.service.dto.Pagination;
import com.epam.esm.task5.service.dto.quest.QuestDTO;
import com.epam.esm.task5.service.dto.reservation.TimeSlot;
import com.epam.esm.task5.service.exception.*;
import com.epam.esm.task5.service.util.PaginationUtil;
import com.epam.esm.task5.service.util.TimeSlotUtil;
import com.epam.esm.task5.service.util.mapper.QuestModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class QuestServiceImpl implements QuestService {

    private QuestRepository questRepository;
    private TagService tagService;
    private ReservationService reservationService;

    @Autowired
    public QuestServiceImpl(
            QuestRepository questRepository,
            TagService tagService,
            ReservationService reservationService) {
        this.questRepository = questRepository;
        this.tagService = tagService;
        this.reservationService = reservationService;
    }

    @Override
    public QuestDTO addQuestWithTags(QuestDTO questDTO) {
        if (questDTO.getDateOfReservationStart() > questDTO.getDateOfReservationEnd()) {
            throw new QuestReservationsDateException("The dates should be valid dates in future");
        }

        Quest quest = QuestModelMapper.mapToQuest(questDTO);
        quest.setDateOfCreation(Instant.now());
        quest.setDateOfModification(Instant.now());

        if (quest.getTags() != null) {
            quest.getTags().forEach(tag -> tag.setName(tag.getName().toLowerCase()));
            Set<Tag> tagsWithIds = tagService.getTagsByName(quest.getTags());
            quest.setTags(tagsWithIds);
        }
        return QuestModelMapper.mapToQuestDTO(questRepository.save(quest));
    }

    @Override
    public void addQuests(List<QuestDTO> questsDTO) {
        List<Quest> quests = QuestModelMapper.mapQuestsDTOToQuests(questsDTO);

        Instant currentDate = Instant.now();
        quests.forEach(
                quest -> {
                    quest.setDateOfCreation(currentDate);
                    quest.setDateOfModification(currentDate);
                });

        try {
            questRepository.addQuests(quests);
        } catch (Exception e) {
            throw new DBConstraintException();
        }
    }

    @Override
    public QuestDTO getQuest(Long id) {
        return QuestModelMapper.mapToQuestDTO(questRepository.getById(Quest.class, id));
    }

    @Override
    public Pagination<QuestDTO> getQuestsBySearchCriteria(QuestSearchCriteria criteria) {
        if (!isStartPriceLessThanEndPrice(criteria.getStartPrice(), criteria.getEndPrice())) {
            throw new QuestSearchPriceException(
                    "Start price for searching should be less than end price");
        }

        Long totalQuestCount = questRepository.count(new CountQuestsbySearchCriteriaSpecification(criteria));
        if (totalQuestCount == 0L) {
            return new Pagination<>(Collections.emptyList(), totalQuestCount);
        }

        PaginationCriteria pagination = PaginationUtil.setDefaultPagesIfNotValid(criteria);

        List<Quest> questsForPage =
                questRepository.getForPage(new GetQuestsBySearchCriteriaSpecification(criteria), pagination);
        return new Pagination<>(QuestModelMapper.mapQuestsToQuestsDTO(questsForPage), totalQuestCount);
    }

    private boolean isStartPriceLessThanEndPrice(String startPrice, String endPrice) {
        if (startPrice == null || endPrice == null) {
            return true;
        }

        return Double.parseDouble(startPrice) <= Double.parseDouble(endPrice);
    }

    @Override
    public List<TimeSlot> getQuestScheduleForDay(Long questId, Long date) {
        QuestDTO quest = getQuest(questId);
        Integer duration = quest.getDuration();

        List<Integer> reservedSlotIds = reservationService.getQuestReservedSlotIdsForDay(questId, date);
        List<TimeSlot> timeSlots = TimeSlotUtil.getSlotsForQuest(duration);
        for (TimeSlot timeSlot : timeSlots) {
            if (reservedSlotIds.contains(timeSlot.getSlotId())) {
                timeSlot.setIsReserved(true);
            }
        }

        return timeSlots;
    }

    @Override
    public boolean isQuestIdExists(Long id) {
        return questRepository.isExists(new IsQuestIdExistsSpecification(id));
    }

    @Override
    public boolean isQuestBelongToOwner(Long questId, Long ownerId) {
        return questRepository.isExists(new IsQuestBelongToOwnerSpecification(questId, ownerId));
    }

    @Override
    public QuestDTO updateQuestWithTagsById(QuestDTO questDTO) {
        if (!isQuestBelongToOwner(questDTO.getId(), questDTO.getOwnerId())) {
            throw new QuestOperationException();
        }

        QuestDTO existingQuest = getQuest(questDTO.getId());
        questDTO.setDateOfCreation(existingQuest.getDateOfCreation());

        Quest quest = QuestModelMapper.mapToQuest(questDTO);
        quest.setDateOfModification(Instant.now());

        if (quest.getTags() != null) {
            quest.getTags().forEach(tag -> tag.setName(tag.getName().toLowerCase()));
            Set<Tag> tagsWithIds = tagService.getTagsByName(quest.getTags());
            quest.setTags(tagsWithIds);
        }

        return QuestModelMapper.mapToQuestDTO(questRepository.save(quest));
    }

    @Override
    public void deleteQuestById(Long questId, Long ownerId) {
        if (!isQuestIdExists(questId)) {
            return;
        }

        if (!isQuestBelongToOwner(questId, ownerId)) {
            throw new QuestOperationException();
        }

        if (reservationService.isQuestReserved(questId)) {
            throw new QuestReservedException();
        }

        questRepository.deleteById(Quest.class, questId);
    }
}
