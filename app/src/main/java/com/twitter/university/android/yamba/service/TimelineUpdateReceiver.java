package com.twitter.university.android.yamba.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import com.twitter.university.android.yamba.client.BuildConfig;
import com.twitter.university.android.yamba.client.R;
import com.twitter.university.android.yamba.client.TimelineActivity;


public class TimelineUpdateReceiver extends BroadcastReceiver {
    private static final String TAG = "TLUPDATE";
    private static final int NOTIFICATION_ID = 7;
    private static final int NOTIFICATION_INTENT_ID = 13;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context ctxt, Intent intent) {
        Resources rez = ctxt.getResources();
        String notifyTitle = rez.getString(R.string.notify_title);
        String notifyMessage = rez.getString(R.string.notify_message);

        int count = intent.getIntExtra(YambaContract.Service.PARAM_COUNT, -1);
        if (BuildConfig.DEBUG) { Log.d(TAG, "timeline update: " + count); }

        if (0 >= count) { return; }

        PendingIntent pi = PendingIntent.getActivity(
            ctxt,
            NOTIFICATION_INTENT_ID,
            new Intent(ctxt, TimelineActivity.class),
            0);

        Notification.Builder builder = new Notification.Builder(ctxt)
            .setContentTitle(notifyTitle)
            .setContentText(count + " " + notifyMessage)
            .setAutoCancel(true)
            .setSmallIcon(android.R.drawable.stat_notify_more)
            .setWhen(System.currentTimeMillis())
            .setContentIntent(pi);

        ((NotificationManager) ctxt.getSystemService(Context.NOTIFICATION_SERVICE))
            .notify(
                NOTIFICATION_ID,
                (Build.VERSION_CODES.JELLY_BEAN > Build.VERSION.SDK_INT)
                    ? builder.getNotification()
                    : builder.build());
    }
}
