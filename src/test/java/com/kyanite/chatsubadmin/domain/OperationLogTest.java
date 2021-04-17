package com.kyanite.chatsubadmin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyanite.chatsubadmin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OperationLogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationLog.class);
        OperationLog operationLog1 = new OperationLog();
        operationLog1.setId(1L);
        OperationLog operationLog2 = new OperationLog();
        operationLog2.setId(operationLog1.getId());
        assertThat(operationLog1).isEqualTo(operationLog2);
        operationLog2.setId(2L);
        assertThat(operationLog1).isNotEqualTo(operationLog2);
        operationLog1.setId(null);
        assertThat(operationLog1).isNotEqualTo(operationLog2);
    }
}
