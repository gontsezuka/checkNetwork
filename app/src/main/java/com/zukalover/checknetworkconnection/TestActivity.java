package com.zukalover.checknetworkconnection;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends AbstractActivity {


    TextView txt_notcon;
    Button btn_con;
    boolean sent=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);




        txt_notcon = findViewById(R.id.txt_con);
        btn_con = findViewById(R.id.btn_con);

        createNotificationChannel();
        /**
         * Create a service for handler thread
         * Initialization
         */
        txt_notcon.setVisibility(View.GONE);

        //GetApplicationContenxt
        if(isOnline(getBaseContext()))
        {
            setVisibilityOn();
            sent=false;
        }else {
            setVisibilityOff();
            sent=false;
        }
    }


    @Override
    public void setVisibilityOff() {
        super.setVisibilityOff();

        Toast.makeText(TestActivity.this,"Network connectivity is off",Toast.LENGTH_SHORT).show();
        btn_con.setVisibility(View.GONE);
        txt_notcon.setVisibility(View.VISIBLE);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, AbstractActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Network Disconnected")
                .setContentText("Check your internet connectivity")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }

    @Override
    public void setVisibilityOn() {
        super.setVisibilityOn();
        Toast.makeText(TestActivity.this,"NetworkConnectivity is on",Toast.LENGTH_SHORT).show();
        btn_con.setVisibility(View.VISIBLE);
        txt_notcon.setVisibility(View.GONE);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver(myReceiver,super.getIntentFilter());
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver,super.getIntentFilter());
    }

}