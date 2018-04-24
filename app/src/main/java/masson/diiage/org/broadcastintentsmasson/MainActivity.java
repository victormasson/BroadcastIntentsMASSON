package masson.diiage.org.broadcastintentsmasson;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.BatteryManager;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.net.Inet4Address;
import java.security.Provider;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlobalBroadcastReceiver globalBroadcastReceiver = new GlobalBroadcastReceiver();
        // Ajoute les events à regarder
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        this.registerReceiver(globalBroadcastReceiver, filter);
    }

    @Override
    protected void onStart() {
        // Sert à demander les permissions au dessus d'android 6
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.RECEIVE_SMS}, 1);
        super.onStart();
    }
}
