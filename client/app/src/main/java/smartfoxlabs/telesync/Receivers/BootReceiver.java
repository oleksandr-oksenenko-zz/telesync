package smartfoxlabs.telesync.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.Toast;

import smartfoxlabs.telesync.Services.UpdateService;

/**
 * Created by dwite_000 on 12.10.2014.
 */
public class BootReceiver extends BroadcastReceiver {
    Context context;
    private PowerManager.WakeLock wl;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            this.context = context;
            try {
                PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNjfdhotDimScreen");
            }catch (Exception e) {

            }
            finally {
                startUpdateService();
            }
        }
    }

    private void startUpdateService() {
        Intent updateService = new Intent(context, UpdateService.class);
        context.startService(updateService);
    }
}
