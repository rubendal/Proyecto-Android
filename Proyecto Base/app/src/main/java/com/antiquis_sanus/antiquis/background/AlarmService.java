package com.antiquis_sanus.antiquis.background;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class AlarmService extends Service {
    Alarm alarm = new Alarm();
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        alarm.SetAlarm(this);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        alarm.CancelAlarm(this);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }


}
