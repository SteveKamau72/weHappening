package com.snapttechtechnologies.stevekamau.wehappening.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.snapttechtechnologies.stevekamau.wehappening.R;
import com.snapttechtechnologies.stevekamau.wehappening.activities.AttendOrNot;


/**
 * This service is started when an Alarm has been raised
 * <p/>
 * We pop a notification into the status bar for the user to click on
 * When the user clicks the notification a new activity is opened
 *
 * @author paul.blundell
 */
public class NotifyService extends Service {

    // Name of an intent extra we can use to identify if this service was started to create a notification
    public static final String INTENT_NOTIFY = "com.blundell.tut.service.INTENT_NOTIFY";
    // Unique id to identify the notification.
    private static final int NOTIFICATION = 123;
    // This is the object that receives interactions from clients
    private final IBinder mBinder = new ServiceBinder();
    // The system notification manager
    private NotificationManager mNM;

    @Override
    public void onCreate() {
        Log.i("NotifyService", "onCreate()");
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        // If this service was started by out AlarmTask intent then we want to show our notification
        if (intent.getBooleanExtra(INTENT_NOTIFY, false))
            showNotification();

        // We don't care if this service is stopped as we have already delivered our notification
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Creates a notification and shows it in the OS drag-down status bar
     */
    private void showNotification() {
        Toast.makeText(getApplicationContext(), "Notice", Toast.LENGTH_LONG).show();

        Notification myNotication;
        NotificationManager manager;
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, AttendOrNot.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setAutoCancel(true);
        builder.setTicker("weHappening event alert");
        builder.setContentTitle("weHappening");
        builder.setContentText("Your event will be up in an hour!!");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        builder.setSubText("Let's get happening!");
        builder.setNumber(100);
        builder.build();
        myNotication = builder.getNotification();
        myNotication.defaults |= Notification.DEFAULT_VIBRATE;
        myNotication.defaults |= Notification.DEFAULT_SOUND;
        manager.notify(11, myNotication);


        // Stop the service when we are finished
        stopSelf();
    }

    /**
     * Class for clients to access
     */
    public class ServiceBinder extends Binder {
        NotifyService getService() {
            return NotifyService.this;
        }
    }
}