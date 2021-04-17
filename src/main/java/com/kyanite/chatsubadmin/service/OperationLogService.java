package com.kyanite.chatsubadmin.service;

import com.kyanite.chatsubadmin.domain.OperationLog;
import com.kyanite.chatsubadmin.repository.OperationLogRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OperationLog}.
 */
@Service
@Transactional
public class OperationLogService {

    private final Logger log = LoggerFactory.getLogger(OperationLogService.class);

    private final OperationLogRepository operationLogRepository;

    public OperationLogService(OperationLogRepository operationLogRepository) {
        this.operationLogRepository = operationLogRepository;
    }

    /**
     * Save a operationLog.
     *
     * @param operationLog the entity to save.
     * @return the persisted entity.
     */
    public OperationLog save(OperationLog operationLog) {
        log.debug("Request to save OperationLog : {}", operationLog);
        return operationLogRepository.save(operationLog);
    }

    /**
     * Partially update a operationLog.
     *
     * @param operationLog the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OperationLog> partialUpdate(OperationLog operationLog) {
        log.debug("Request to partially update OperationLog : {}", operationLog);

        return operationLogRepository
            .findById(operationLog.getId())
            .map(
                existingOperationLog -> {
                    if (operationLog.getTime() != null) {
                        existingOperationLog.setTime(operationLog.getTime());
                    }
                    if (operationLog.getChatid() != null) {
                        existingOperationLog.setChatid(operationLog.getChatid());
                    }
                    if (operationLog.getSetUserList() != null) {
                        existingOperationLog.setSetUserList(operationLog.getSetUserList());
                    }

                    return existingOperationLog;
                }
            )
            .map(operationLogRepository::save);
    }

    /**
     * Get all the operationLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OperationLog> findAll(Pageable pageable) {
        log.debug("Request to get all OperationLogs");
        return operationLogRepository.findAll(pageable);
    }

    /**
     * Get one operationLog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OperationLog> findOne(Long id) {
        log.debug("Request to get OperationLog : {}", id);
        return operationLogRepository.findById(id);
    }

    /**
     * Delete the operationLog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OperationLog : {}", id);
        operationLogRepository.deleteById(id);
    }
}
