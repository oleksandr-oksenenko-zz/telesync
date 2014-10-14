package smartfoxlabs.telesync.Services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit.RestAdapter;
import smartfoxlabs.telesync.Activitys.AuthActivity;
import smartfoxlabs.telesync.R;
import smartfoxlabs.telesync.Rest.RegRequest;
import smartfoxlabs.telesync.Rest.TV;
import smartfoxlabs.telesync.Rest.TeleSyncClient;

/**
 * Created by dwite_000 on 12.10.2014.
 */
public class UpdateService extends IntentService {
    public static final String APP_NUMBER = "NUMBER";
    private int number = 0;
    SharedPreferences mPreferences;
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
        // Do useful things.
        //Toast.makeText(getApplicationContext(),String.valueOf(++number),Toast.LENGTH_SHORT).show();
        // After doing useful things...
        //showNotif();
        /*Intent downloadService = new Intent(getApplicationContext(), DownloadService.class);
        downloadService.putExtra("downloadUrl",
                "https://dl.dropboxusercontent.com/u/31880748/Smash%20Champs%20-%20Gameplay%20Trailer.flv");
        startService(downloadService);*/
        mPreferences = getApplicationContext().getSharedPreferences(AuthActivity.APP_PREFERENCES,
                Context.MODE_PRIVATE);
        try {
            scheduleNextUpdate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void updateStatus() {
        new HeartBeatTask().execute();
    }

    private void scheduleNextUpdate() throws ParseException {
        updateStatus();
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

        SimpleDateFormat sdf2 = new SimpleDateFormat("kk:mm");
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR);
        int minute = now.get(Calendar.MINUTE);
        String currentHour = Integer.toString(hour);
        String currentMinute = Integer.toString(minute);
        String timeNow = currentHour + ":" + currentMinute;
        Date timeRightNow = sdf2.parse(timeNow);
        if(hour < 8 || hour > 22) {
            //TODO add check for new video
        }
        nextUpdateTimeMillis = System.currentTimeMillis() + 15 * 60 * 1000;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, nextUpdateTimeMillis, pendingIntent);
    }

    private class HeartBeatTask extends AsyncTask<Void,Void,Void> {

        int tvId = -1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(mPreferences == null)
                mPreferences =
                        getSharedPreferences(AuthActivity.APP_PREFERENCES,Context.MODE_PRIVATE);
            if(mPreferences.contains(AuthActivity.APP_PREFERENCES_ID)) {
                tvId = mPreferences.getInt(AuthActivity.APP_PREFERENCES_ID, -1);
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (tvId != -1)
            try {
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .setEndpoint(TeleSyncClient.API_URL)
                        .build();
                TeleSyncClient.TeleSyncRestApi teleSyncAdapter =
                        restAdapter.create(TeleSyncClient.TeleSyncRestApi.class);
                teleSyncAdapter.updateStatus(tvId);
            }
            catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}

