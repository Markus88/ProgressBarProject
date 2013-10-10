package dk.phh.progressbar;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class ServerSyncService extends IntentService {

    public ServerSyncService() {
        super("ServerSyncService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        for(int i = 1 ; i <= 100 ; i++)
        {
            sendUpdateMessage(i);
            try
            {
                Thread.sleep(200);
            }
            catch(Exception e)
            {
                Log.d("DEBUG: ", "Error in sending: " + e.getMessage());
            }
        }
        sendResultMessage("Data fra service modtaget");

        stopSelf();
    }
    public void sendUpdateMessage(int percent) {
        Log.d("DEBUG: ", "Sending update data " + percent);
        Intent intent = new Intent("serviceProgressBar");
        intent.putExtra("COMMAND", 1);
        intent.putExtra("DATA", percent);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
    public void sendResultMessage(String data) {
        Log.d("DEBUG: ", "Result message: " + data);
        Intent intent = new Intent("serviceProgressBar");
        intent.putExtra("COMMAND", 2);
        intent.putExtra("DATA", data);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}