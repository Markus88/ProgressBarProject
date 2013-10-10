package dk.phh.progressbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {


    private ProgressBar bar;
    private TextView txtview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // OnClickListener
        findViewById(R.id.btnClick).setOnClickListener(this);

        // Progress bar initialization
        bar = (ProgressBar)findViewById(R.id.pb);
        bar.setMax(100);

        //txtViews & others
        txtview = (TextView)findViewById(R.id.txtView);

        // Local Broadcast manager
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("serviceProgressBar"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v)
    {
        bar.setProgress(0);
        txtview.setText("TEST");
        Intent intent = new Intent(this, ServerSyncService.class);
        startService(intent);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("DEBUG: ", "Message received");
            handleMessage(intent);
        }
    };

    private void handleMessage(Intent message)
    {
        Bundle data = message.getExtras();
        switch (data.getInt("COMMAND", 0))
        {
            case 1:
                int progress = data.getInt("DATA", 0);
                bar.setProgress(progress);
                break;
            case 2:
                String result = data.getString("DATA");
                txtview.setText(result);
                break;
            default:
                break;
        }
    }
    
}
