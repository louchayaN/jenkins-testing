package com.epam.esm.task5.repository.audit.listener;

import com.epam.esm.task5.repository.audit.Action;
import com.epam.esm.task5.repository.entity.Quest;
import com.epam.esm.task5.repository.audit.entity.QuestAuditLog;

import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class QuestEntityListener extends EntityListener {

  @PostPersist
  public void postPersist(Quest target) {
    perform(target, Action.INSERTED);
  }

  @PreUpdate
  public void preUpdate(Quest target) {
    perform(target, Action.UPDATED);
  }

  @PreRemove
  public void preRemove(Quest target) {
    perform(target, Action.DELETED);
  }

  private void perform(Quest quest, Action action) {
    logRepository.save(new QuestAuditLog(quest, action));
  }
}
