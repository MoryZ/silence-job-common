package com.old.silence.job.common.server.dto;

import java.math.BigInteger;

import com.old.silence.job.common.dto.LogTaskDTO;

/**
 * @author moryzang
 */
public class RetryLogTaskDTO extends LogTaskDTO {

    private BigInteger retryTaskId;

    private BigInteger retryId;

    private String clientInfo;

    public BigInteger getRetryTaskId() {
        return retryTaskId;
    }

    public void setRetryTaskId(BigInteger retryTaskId) {
        this.retryTaskId = retryTaskId;
    }

    public BigInteger getRetryId() {
        return retryId;
    }

    public void setRetryId(BigInteger retryId) {
        this.retryId = retryId;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }
}
