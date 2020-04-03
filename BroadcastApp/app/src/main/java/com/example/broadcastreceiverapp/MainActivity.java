package com.example.broadcastreceiverapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private CustomReceiver mReceiver = new CustomReceiver();
    private ComponentName mReceiverComponentName;
    private PackageManager mPackageManager;
    Button broadcastButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        broadcastButton=findViewById(R.id.broadcastButton);
        mReceiverComponentName = new ComponentName(this,CustomReceiver.class);
        mPackageManager=getPackageManager();

        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,new IntentFilter(CustomReceiver.ACTION_CUSTOM_BROADCAST));
        broadcastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCustomBroadcast();
            }
        });
    }
    private void sendCustomBroadcast(){
        Intent broadcastIntent=new Intent(CustomReceiver.ACTION_CUSTOM_BROADCAST);
        broadcastIntent.putExtra("DATA","Data broadcast");
        LocalBroadcastManager.getInstance(this)
                .sendBroadcast(broadcastIntent);
    }
    protected void onStart(){
        mPackageManager.setComponentEnabledSetting(mReceiverComponentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        super.onStart();
    }
    protected  void onStop(){
        mPackageManager.setComponentEnabledSetting(mReceiverComponentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        super.onStop();
    }
    protected void onDestroy(){
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
