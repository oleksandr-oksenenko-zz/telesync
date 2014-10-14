package smartfoxlabs.telesync.Activitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.RestAdapter;
import smartfoxlabs.telesync.R;
import smartfoxlabs.telesync.Rest.RegRequest;
import smartfoxlabs.telesync.Rest.TV;
import smartfoxlabs.telesync.Rest.TeleSyncClient;
import smartfoxlabs.telesync.Services.UpdateService;


public class AuthActivity extends Activity {

    EditText tvName;
    EditText tvPhone;
    TextView txtName;

    public static final String APP_PREFERENCES = "APPPREFERENCES";
    public static final String APP_PREFERENCES_ID = "ID";
    public static final String APP_PREFERENCES_TVNAME = "NAME";
    SharedPreferences mSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSettings =
                getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if(mSettings.contains(APP_PREFERENCES_ID)) {
            setInfo();
        }
        else {
            setContentView(R.layout.activity_auth);
            tvName = (EditText) findViewById(R.id.etName);
            tvPhone = (EditText) findViewById(R.id.eTPhone);

        /*
        Intent downloadService = new Intent(this, DownloadService.class);
        downloadService.putExtra("downloadUrl",
                "https://dl.dropboxusercontent.com/u/31880748/Smash%20Champs%20-%20Gameplay%20Trailer.flv");
        startService(downloadService);
        */
        }
    }

    private void setInfo() {
        setContentView(R.layout.activity_authed);
        txtName = (TextView) findViewById(R.id.txtName);
        txtName.setText(mSettings.getString(APP_PREFERENCES_TVNAME,""));
    }
    public void onUpdateClick(View v) {
        startUpdateService();
    }
    private void startUpdateService() {
        Intent updateService = new Intent(this, UpdateService.class);
        startService(updateService);
    }

    public void onRegClick(View v) {
        new RegTask().execute();
    }

    private class RegTask extends AsyncTask<Void,Void,Void> {

        TV myTv;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(AuthActivity.this);
            dialog.setMessage(getString(R.string.process_registration));
            try {
                dialog.show();
            }
            catch (Exception e) {

            }
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (!mSettings.contains(APP_PREFERENCES_ID))
                try {
                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setLogLevel(RestAdapter.LogLevel.FULL)
                            .setEndpoint(TeleSyncClient.API_URL)
                            .build();
                    TeleSyncClient.TeleSyncRestApi teleSyncAdapter =
                            restAdapter.create(TeleSyncClient.TeleSyncRestApi.class);
                    if (tvName.getText().length() > 0)
                    myTv = teleSyncAdapter.regTv(
                            new RegRequest(
                                    tvPhone.getText().toString(),
                                    tvName.getText().toString()
                            ));
                }
                catch (Exception e) {

                }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.hide();
            if(myTv != null && myTv.getTvName() != null)
            {
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putInt(APP_PREFERENCES_ID, myTv.getId());
                editor.putString(APP_PREFERENCES_TVNAME,myTv.getTvName());
                editor.apply();
                startUpdateService();
                setInfo();
            }
            else {
                Toast.makeText(getApplicationContext(), getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(aVoid);
        }
    }
}
