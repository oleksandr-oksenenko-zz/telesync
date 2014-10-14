package smartfoxlabs.telesync.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import smartfoxlabs.telesync.Services.UpdateService;

/**
 * Created by dwite_000 on 12.10.2014.
 */
public class BootReceiver extends BroadcastReceiver {
    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            this.context = context;
            startUpdateService();
        }
    }

    private void startUpdateService() {
        Intent updateService = new Intent(context, UpdateService.class);
        context.startService(updateService);
    }
}
