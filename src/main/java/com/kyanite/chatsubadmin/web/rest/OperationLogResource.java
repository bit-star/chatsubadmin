package com.kyanite.chatsubadmin.web.rest;

import com.kyanite.chatsubadmin.domain.OperationLog;
import com.kyanite.chatsubadmin.repository.OperationLogRepository;
import com.kyanite.chatsubadmin.service.OperationLogService;
import com.kyanite.chatsubadmin.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.kyanite.chatsubadmin.domain.OperationLog}.
 */
@RestController
@RequestMapping("/api")
public class OperationLogResource {

    private final Logger log = LoggerFactory.getLogger(OperationLogResource.class);

    private static final String ENTITY_NAME = "operationLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OperationLogService operationLogService;

    private final OperationLogRepository operationLogRepository;

    public OperationLogResource(OperationLogService operationLogService, OperationLogRepository operationLogRepository) {
        this.operationLogService = operationLogService;
        this.operationLogRepository = operationLogRepository;
    }

    /**
     * {@code POST  /operation-logs} : Create a new operationLog.
     *
     * @param operationLog the operationLog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new operationLog, or with status {@code 400 (Bad Request)} if the operationLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/operation-logs")
    public ResponseEntity<OperationLog> createOperationLog(@RequestBody OperationLog operationLog) throws URISyntaxException {
        log.debug("REST request to save OperationLog : {}", operationLog);
        if (operationLog.getId() != null) {
            throw new BadRequestAlertException("A new operationLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperationLog result = operationLogService.save(operationLog);
        return ResponseEntity
            .created(new URI("/api/operation-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /operation-logs/:id} : Updates an existing operationLog.
     *
     * @param id the id of the operationLog to save.
     * @param operationLog the operationLog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operationLog,
     * or with status {@code 400 (Bad Request)} if the operationLog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the operationLog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/operation-logs/{id}")
    public ResponseEntity<OperationLog> updateOperationLog(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OperationLog operationLog
    ) throws URISyntaxException {
        log.debug("REST request to update OperationLog : {}, {}", id, operationLog);
        if (operationLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operationLog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operationLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OperationLog result = operationLogService.save(operationLog);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, operationLog.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /operation-logs/:id} : Partial updates given fields of an existing operationLog, field will ignore if it is null
     *
     * @param id the id of the operationLog to save.
     * @param operationLog the operationLog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operationLog,
     * or with status {@code 400 (Bad Request)} if the operationLog is not valid,
     * or with status {@code 404 (Not Found)} if the operationLog is not found,
     * or with status {@code 500 (Internal Server Error)} if the operationLog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/operation-logs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OperationLog> partialUpdateOperationLog(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OperationLog operationLog
    ) throws URISyntaxException {
        log.debug("REST request to partial update OperationLog partially : {}, {}", id, operationLog);
        if (operationLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operationLog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operationLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OperationLog> result = operationLogService.partialUpdate(operationLog);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, operationLog.getId().toString())
        );
    }

    /**
     * {@code GET  /operation-logs} : get all the operationLogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of operationLogs in body.
     */
    @GetMapping("/operation-logs")
    public ResponseEntity<List<OperationLog>> getAllOperationLogs(Pageable pageable) {
        log.debug("REST request to get a page of OperationLogs");
        Page<OperationLog> page = operationLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /operation-logs/:id} : get the "id" operationLog.
     *
     * @param id the id of the operationLog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the operationLog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/operation-logs/{id}")
    public ResponseEntity<OperationLog> getOperationLog(@PathVariable Long id) {
        log.debug("REST request to get OperationLog : {}", id);
        Optional<OperationLog> operationLog = operationLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operationLog);
    }

    /**
     * {@code DELETE  /operation-logs/:id} : delete the "id" operationLog.
     *
     * @param id the id of the operationLog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/operation-logs/{id}")
    public ResponseEntity<Void> deleteOperationLog(@PathVariable Long id) {
        log.debug("REST request to delete OperationLog : {}", id);
        operationLogService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
