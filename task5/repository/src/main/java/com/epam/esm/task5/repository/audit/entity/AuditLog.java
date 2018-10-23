package com.epam.esm.task5.repository.audit.entity;

import com.epam.esm.task5.repository.audit.Action;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditLog implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_log")
  private Long id;

  @CreatedBy
  @Column(name = "id_user")
  private Long userId;

  @CreatedDate private Instant modificationDate;

  @Enumerated(EnumType.STRING)
  private Action action;

  AuditLog() {}

  AuditLog(Action action) {
    this.action = action;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Instant getModificationDate() {
    return modificationDate;
  }

  public void setModificationDate(Instant modificationDate) {
    this.modificationDate = modificationDate;
  }

  public Action getAction() {
    return action;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AuditLog auditLog = (AuditLog) o;
    return Objects.equals(id, auditLog.id)
        && Objects.equals(userId, auditLog.userId)
        && Objects.equals(modificationDate, auditLog.modificationDate)
        && action == auditLog.action;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userId, modificationDate, action);
  }

  @Override
  public String toString() {
    return "AuditLog{"
        + "id="
        + id
        + ", userId="
        + userId
        + ", modificationDate="
        + modificationDate
        + ", action="
        + action
        + '}';
  }

  public void setAction(Action action) {
    this.action = action;
  }
}
