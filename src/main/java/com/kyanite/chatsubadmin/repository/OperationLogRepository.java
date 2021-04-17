package com.kyanite.chatsubadmin.repository;

import com.kyanite.chatsubadmin.domain.OperationLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OperationLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationLogRepository extends JpaRepository<OperationLog, Long> {}
