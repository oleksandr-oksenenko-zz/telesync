package smartfoxlabs.telesync.Services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;


import smartfoxlabs.telesync.Activitys.AuthActivity;
import smartfoxlabs.telesync.R;

/**
 * Created by dwite_000 on 12.10.2014.
 */
public class UpdateService extends IntentService {
    public static final String APP_NUMBER = "NUMBER";
    private int number = 0;
    public UpdateService()
    {
        super(UpdateService.class.getSimpleName());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            int downloadUrl = bundle.getInt(APP_NUMBER);
            this.number = downloadUrl;
        }
        // Do useful things.
        //Toast.makeText(getApplicationContext(),String.valueOf(++number),Toast.LENGTH_SHORT).show();
        // After doing useful things...
        //showNotif();
        /*Intent downloadService = new Intent(getApplicationContext(), DownloadService.class);
        downloadService.putExtra("downloadUrl",
                "https://dl.dropboxusercontent.com/u/31880748/Smash%20Champs%20-%20Gameplay%20Trailer.flv");
        startService(downloadService);*/
        updateStatus();
        scheduleNextUpdate();
    }

    private void updateStatus() {

    }
    private void showNotif() {
        NotificationManager mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.ic_launcher, "vvs",
                System.currentTimeMillis());
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        Intent intent = new Intent(this, AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this.getBaseContext(), 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, "title",
                "text", contentIntent);
        // Send the notification.
        // We use a layout id because it is a unique number.  We use it later to cancel.
        mNM.notify(R.string.app_name, notification);
        startForeground(1337, notification);

    }

    private void scheduleNextUpdate()
    {
        Intent intent = new Intent(this, this.getClass());
        PendingIntent pendingIntent =
                PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // The update frequency should often be user configurable.  This is not.

        long currentTimeMillis = System.currentTimeMillis();
        long nextUpdateTimeMillis = currentTimeMillis + 15 * DateUtils.MINUTE_IN_MILLIS;
        Time nextUpdateTime = new Time();
        nextUpdateTime.set(nextUpdateTimeMillis);

        /*if (nextUpdateTime.hour < 8 || nextUpdateTime.hour >= 18)
        {
            nextUpdateTime.hour = 8;
            nextUpdateTime.minute = 0;
            nextUpdateTime.second = 0;
            nextUpdateTimeMillis = nextUpdateTime.toMillis(false) + DateUtils.DAY_IN_MILLIS;
        }
        */
        nextUpdateTimeMillis = System.currentTimeMillis() + 15 * 60 * 1000;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, nextUpdateTimeMillis, pendingIntent);
    }

}

