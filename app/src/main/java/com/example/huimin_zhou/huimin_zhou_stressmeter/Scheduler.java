package com.example.huimin_zhou.huimin_zhou_stressmeter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
/**
 * Created by Lucidity on 17/1/25.
 */

public class Scheduler {
    public static void setSchedule(Context context) {
        setSchedule(context, 23, 50, 0);
    }

    private static void setSchedule(Context context, int hour, int min, int sec) {

        // the request code distinguish different stress meter schedule instances
        int requestCode = hour * 10000 + min * 100 + sec;
        Intent intent = new Intent(context, AlarmReceiver.class);

        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode, intent,
                PendingIntent.FLAG_CANCEL_CURRENT); //set pending intent to call EMAAlarmReceiver.

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, sec);

        if(calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DATE, 1);
        }

        //set repeating alarm, and pass the pending intent,
        //so that the broadcast is sent everytime the alarm
        // is triggered
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);
    }
}