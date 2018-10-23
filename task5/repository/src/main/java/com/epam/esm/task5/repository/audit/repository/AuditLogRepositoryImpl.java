package com.epam.esm.task5.repository.audit.repository;

import com.epam.esm.task5.repository.BasicRepositoryImpl;
import com.epam.esm.task5.repository.audit.entity.AuditLog;
import org.springframework.stereotype.Repository;

@Repository
public class AuditLogRepositoryImpl extends BasicRepositoryImpl<AuditLog>
    implements AuditLogRepository {
}
