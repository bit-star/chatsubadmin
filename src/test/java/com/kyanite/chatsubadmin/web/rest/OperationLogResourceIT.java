package com.kyanite.chatsubadmin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.chatsubadmin.IntegrationTest;
import com.kyanite.chatsubadmin.domain.OperationLog;
import com.kyanite.chatsubadmin.repository.OperationLogRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OperationLogResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OperationLogResourceIT {

    private static final Instant DEFAULT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CHATID = "AAAAAAAAAA";
    private static final String UPDATED_CHATID = "BBBBBBBBBB";

    private static final String DEFAULT_SET_USER_LIST = "AAAAAAAAAA";
    private static final String UPDATED_SET_USER_LIST = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/operation-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OperationLogRepository operationLogRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOperationLogMockMvc;

    private OperationLog operationLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperationLog createEntity(EntityManager em) {
        OperationLog operationLog = new OperationLog().time(DEFAULT_TIME).chatid(DEFAULT_CHATID).setUserList(DEFAULT_SET_USER_LIST);
        return operationLog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperationLog createUpdatedEntity(EntityManager em) {
        OperationLog operationLog = new OperationLog().time(UPDATED_TIME).chatid(UPDATED_CHATID).setUserList(UPDATED_SET_USER_LIST);
        return operationLog;
    }

    @BeforeEach
    public void initTest() {
        operationLog = createEntity(em);
    }

    @Test
    @Transactional
    void createOperationLog() throws Exception {
        int databaseSizeBeforeCreate = operationLogRepository.findAll().size();
        // Create the OperationLog
        restOperationLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operationLog)))
            .andExpect(status().isCreated());

        // Validate the OperationLog in the database
        List<OperationLog> operationLogList = operationLogRepository.findAll();
        assertThat(operationLogList).hasSize(databaseSizeBeforeCreate + 1);
        OperationLog testOperationLog = operationLogList.get(operationLogList.size() - 1);
        assertThat(testOperationLog.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testOperationLog.getChatid()).isEqualTo(DEFAULT_CHATID);
        assertThat(testOperationLog.getSetUserList()).isEqualTo(DEFAULT_SET_USER_LIST);
    }

    @Test
    @Transactional
    void createOperationLogWithExistingId() throws Exception {
        // Create the OperationLog with an existing ID
        operationLog.setId(1L);

        int databaseSizeBeforeCreate = operationLogRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperationLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operationLog)))
            .andExpect(status().isBadRequest());

        // Validate the OperationLog in the database
        List<OperationLog> operationLogList = operationLogRepository.findAll();
        assertThat(operationLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOperationLogs() throws Exception {
        // Initialize the database
        operationLogRepository.saveAndFlush(operationLog);

        // Get all the operationLogList
        restOperationLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operationLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].chatid").value(hasItem(DEFAULT_CHATID)))
            .andExpect(jsonPath("$.[*].setUserList").value(hasItem(DEFAULT_SET_USER_LIST)));
    }

    @Test
    @Transactional
    void getOperationLog() throws Exception {
        // Initialize the database
        operationLogRepository.saveAndFlush(operationLog);

        // Get the operationLog
        restOperationLogMockMvc
            .perform(get(ENTITY_API_URL_ID, operationLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(operationLog.getId().intValue()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()))
            .andExpect(jsonPath("$.chatid").value(DEFAULT_CHATID))
            .andExpect(jsonPath("$.setUserList").value(DEFAULT_SET_USER_LIST));
    }

    @Test
    @Transactional
    void getNonExistingOperationLog() throws Exception {
        // Get the operationLog
        restOperationLogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOperationLog() throws Exception {
        // Initialize the database
        operationLogRepository.saveAndFlush(operationLog);

        int databaseSizeBeforeUpdate = operationLogRepository.findAll().size();

        // Update the operationLog
        OperationLog updatedOperationLog = operationLogRepository.findById(operationLog.getId()).get();
        // Disconnect from session so that the updates on updatedOperationLog are not directly saved in db
        em.detach(updatedOperationLog);
        updatedOperationLog.time(UPDATED_TIME).chatid(UPDATED_CHATID).setUserList(UPDATED_SET_USER_LIST);

        restOperationLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOperationLog.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOperationLog))
            )
            .andExpect(status().isOk());

        // Validate the OperationLog in the database
        List<OperationLog> operationLogList = operationLogRepository.findAll();
        assertThat(operationLogList).hasSize(databaseSizeBeforeUpdate);
        OperationLog testOperationLog = operationLogList.get(operationLogList.size() - 1);
        assertThat(testOperationLog.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testOperationLog.getChatid()).isEqualTo(UPDATED_CHATID);
        assertThat(testOperationLog.getSetUserList()).isEqualTo(UPDATED_SET_USER_LIST);
    }

    @Test
    @Transactional
    void putNonExistingOperationLog() throws Exception {
        int databaseSizeBeforeUpdate = operationLogRepository.findAll().size();
        operationLog.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperationLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, operationLog.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operationLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationLog in the database
        List<OperationLog> operationLogList = operationLogRepository.findAll();
        assertThat(operationLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOperationLog() throws Exception {
        int databaseSizeBeforeUpdate = operationLogRepository.findAll().size();
        operationLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operationLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationLog in the database
        List<OperationLog> operationLogList = operationLogRepository.findAll();
        assertThat(operationLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOperationLog() throws Exception {
        int databaseSizeBeforeUpdate = operationLogRepository.findAll().size();
        operationLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationLogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operationLog)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OperationLog in the database
        List<OperationLog> operationLogList = operationLogRepository.findAll();
        assertThat(operationLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOperationLogWithPatch() throws Exception {
        // Initialize the database
        operationLogRepository.saveAndFlush(operationLog);

        int databaseSizeBeforeUpdate = operationLogRepository.findAll().size();

        // Update the operationLog using partial update
        OperationLog partialUpdatedOperationLog = new OperationLog();
        partialUpdatedOperationLog.setId(operationLog.getId());

        partialUpdatedOperationLog.chatid(UPDATED_CHATID);

        restOperationLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperationLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOperationLog))
            )
            .andExpect(status().isOk());

        // Validate the OperationLog in the database
        List<OperationLog> operationLogList = operationLogRepository.findAll();
        assertThat(operationLogList).hasSize(databaseSizeBeforeUpdate);
        OperationLog testOperationLog = operationLogList.get(operationLogList.size() - 1);
        assertThat(testOperationLog.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testOperationLog.getChatid()).isEqualTo(UPDATED_CHATID);
        assertThat(testOperationLog.getSetUserList()).isEqualTo(DEFAULT_SET_USER_LIST);
    }

    @Test
    @Transactional
    void fullUpdateOperationLogWithPatch() throws Exception {
        // Initialize the database
        operationLogRepository.saveAndFlush(operationLog);

        int databaseSizeBeforeUpdate = operationLogRepository.findAll().size();

        // Update the operationLog using partial update
        OperationLog partialUpdatedOperationLog = new OperationLog();
        partialUpdatedOperationLog.setId(operationLog.getId());

        partialUpdatedOperationLog.time(UPDATED_TIME).chatid(UPDATED_CHATID).setUserList(UPDATED_SET_USER_LIST);

        restOperationLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperationLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOperationLog))
            )
            .andExpect(status().isOk());

        // Validate the OperationLog in the database
        List<OperationLog> operationLogList = operationLogRepository.findAll();
        assertThat(operationLogList).hasSize(databaseSizeBeforeUpdate);
        OperationLog testOperationLog = operationLogList.get(operationLogList.size() - 1);
        assertThat(testOperationLog.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testOperationLog.getChatid()).isEqualTo(UPDATED_CHATID);
        assertThat(testOperationLog.getSetUserList()).isEqualTo(UPDATED_SET_USER_LIST);
    }

    @Test
    @Transactional
    void patchNonExistingOperationLog() throws Exception {
        int databaseSizeBeforeUpdate = operationLogRepository.findAll().size();
        operationLog.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperationLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, operationLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operationLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationLog in the database
        List<OperationLog> operationLogList = operationLogRepository.findAll();
        assertThat(operationLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOperationLog() throws Exception {
        int databaseSizeBeforeUpdate = operationLogRepository.findAll().size();
        operationLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operationLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationLog in the database
        List<OperationLog> operationLogList = operationLogRepository.findAll();
        assertThat(operationLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOperationLog() throws Exception {
        int databaseSizeBeforeUpdate = operationLogRepository.findAll().size();
        operationLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationLogMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(operationLog))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OperationLog in the database
        List<OperationLog> operationLogList = operationLogRepository.findAll();
        assertThat(operationLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOperationLog() throws Exception {
        // Initialize the database
        operationLogRepository.saveAndFlush(operationLog);

        int databaseSizeBeforeDelete = operationLogRepository.findAll().size();

        // Delete the operationLog
        restOperationLogMockMvc
            .perform(delete(ENTITY_API_URL_ID, operationLog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OperationLog> operationLogList = operationLogRepository.findAll();
        assertThat(operationLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
