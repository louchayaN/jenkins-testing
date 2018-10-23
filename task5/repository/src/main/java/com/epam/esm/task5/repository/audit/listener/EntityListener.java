package com.epam.esm.task5.repository.audit.listener;

import com.epam.esm.task5.repository.audit.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityListener {

  static AuditLogRepository logRepository;

  @Autowired
  public void setLogRepository(AuditLogRepository logRepository) {
    EntityListener.logRepository = logRepository;
  }
}
