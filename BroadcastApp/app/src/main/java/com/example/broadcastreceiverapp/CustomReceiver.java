package com.example.broadcastreceiverapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {
    static final String ACTION_CUSTOM_BROADCAST
            ="com.example.broadcastreceiverapp.ACTION_CUSTOM_BROADCAST";

    @Override
    public void onReceive(Context context, Intent intent) {
       String intentAction=intent.getAction();
       String message=null;
       switch (intentAction){

           case Intent.ACTION_POWER_CONNECTED:
               message="Power Connected!";
               break;
           case Intent.ACTION_POWER_DISCONNECTED:
               message ="Power Disconnected!";
               break;
           case ACTION_CUSTOM_BROADCAST:
               message=intent.getStringExtra("DATA");
       }
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
