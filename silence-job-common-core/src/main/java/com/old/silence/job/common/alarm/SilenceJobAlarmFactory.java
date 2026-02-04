package com.old.silence.job.common.alarm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.old.silence.job.common.enums.NotifyType;


public class SilenceJobAlarmFactory {

    private static final Map<NotifyType, Alarm> alarmMap = new ConcurrentHashMap<>();

    public static void register(Alarm alarm) {
        alarmMap.put(alarm.getAlarmType(), alarm);
    }

    public static Alarm getAlarmType(NotifyType alarmType) {
        return alarmMap.get(alarmType);
    }
}
