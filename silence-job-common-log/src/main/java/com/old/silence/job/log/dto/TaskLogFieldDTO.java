package com.old.silence.job.log.dto;

import java.io.Serializable;

/**
 * @author moryzang
 */
public class TaskLogFieldDTO implements Serializable {

    private String name;
    private String value;

    /**
     * Non arg constructor for Serializable.
     */
    @SuppressWarnings("unused")
    public TaskLogFieldDTO() {

    }

    public TaskLogFieldDTO(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
