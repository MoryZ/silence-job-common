package com.old.silence.job.common.enums;


import java.util.List;

import com.old.silence.core.enums.EnumValue;


public enum JobTaskBatchStatus implements EnumValue<Byte> {

    /**
     * 待处理
     */
    WAITING(1),

    /**
     * 运行中
     */
    RUNNING(2),

    /**
     * 处理成功
     */
    SUCCESS(3),

    /**
     * 处理失败
     */
    FAIL(4),

    /**
     * 任务停止
     */
    STOP(5),

    /**
     * 取消
     */
    CANCEL(6),

    /**
     * 工作流流转失败状态
     */
    WORKFLOW_DECISION_FAILED_STATUS(98),
    ;

    public static final List<JobTaskBatchStatus> NOT_COMPLETE = List.of(WAITING, RUNNING);
    public static final List<JobTaskBatchStatus> COMPLETED = List.of(SUCCESS, FAIL, STOP, CANCEL);
    public static final List<JobTaskBatchStatus> NOT_SUCCESS = List.of(FAIL, STOP, CANCEL);
    private final Byte value;

    JobTaskBatchStatus(int value) {
        this.value = (byte) value;
    }

    public Byte getValue() {
        return value;
    }
}
