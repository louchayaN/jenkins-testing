package com.epam.esm.task5.repository.audit.entity;

import com.epam.esm.task5.repository.audit.Action;
import com.epam.esm.task5.repository.entity.Quest;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class QuestAuditLog extends AuditLog implements Serializable {

  private Long idQuest;

  private String questContent;

  public QuestAuditLog() {}

  public QuestAuditLog(Quest quest, Action action) {
    super(action);
    this.idQuest = quest.getId();
    this.questContent = quest.toString();
  }

  public Long getIdQuest() {
    return idQuest;
  }

  public void setIdQuest(Long idQuest) {
    this.idQuest = idQuest;
  }

  public String getQuestContent() {
    return questContent;
  }

  public void setQuestContent(String questContent) {
    this.questContent = questContent;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    QuestAuditLog that = (QuestAuditLog) o;
    return Objects.equals(idQuest, that.idQuest) && Objects.equals(questContent, that.questContent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idQuest, questContent);
  }

  @Override
  public String toString() {
    return "QuestAuditLog{" + "idQuest=" + idQuest + ", questContent='" + questContent + '\'' + '}';
  }
}
