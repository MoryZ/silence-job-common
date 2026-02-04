package com.old.silence.job.common.alarm;

import java.util.List;

import com.old.silence.job.common.enums.NotifyType;


public interface Alarm<T> {

    NotifyType getAlarmType();

    boolean asyncSendMessage(T t);

    boolean syncSendMessage(T t);

    boolean asyncSendMessage(List<T> ts);
}
