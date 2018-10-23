package com.epam.esm.task5.repository;

import com.epam.esm.task5.repository.entity.Quest;

import java.util.List;

public interface QuestRepository extends BasicRepository<Quest> {

    void addQuests(List<Quest> quests);
}
