package com.zukalover.checknetworkconnection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btn_submit;
    TextView txt_notcon;

    public static final String BroadCastStringForAction = "checkinternet";

    private IntentFilter intentFilter;//network


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_notcon = findViewById(R.id.txt_not_connect);
        btn_submit = findViewById(R.id.btn_online_submit);



        /**
         * Create a service for handler thread
         * Initialization
         */
        intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCastStringForAction);
        Intent serviceIntent = new Intent(MainActivity.this,MyService.class);
        startService(serviceIntent);
        txt_notcon.setVisibility(View.GONE);

        if(isOnline(getApplicationContext()))
        {
            setVisibilityOn();
        }else {
            setVisibilityOff();
        }

    }

    public BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(BroadCastStringForAction))
            {
                if (intent.getStringExtra("online_status").equals("true"))
                {
                    setVisibilityOn();
                }else {
                    setVisibilityOff();
                }
            }
        }
    };

        public boolean isOnline(Context c) {
            ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();

            if (ni != null && ni.isConnectedOrConnecting())
            {
                return true;
            }else return false;
        }

        //IF CONNECTION IS THERE
        public void setVisibilityOn()
        {
            txt_notcon.setVisibility(View.GONE);
            btn_submit.setVisibility(View.VISIBLE);
        }

        public void setVisibilityOff()
        {
            txt_notcon.setVisibility(View.VISIBLE);
            btn_submit.setVisibility(View.GONE);
        }

        //HOME BUTTON
        @Override
        protected void onRestart() {
            super.onRestart();
            registerReceiver(myReceiver,intentFilter);
        }

        //ON-PAUSE

        @Override
        protected void onPause() {
            super.onPause();
            unregisterReceiver(myReceiver);
        }

        @Override
        protected void onResume() {
            super.onResume();
            registerReceiver(myReceiver,intentFilter);
        }

    public IntentFilter getIntentFilter() {
        return intentFilter;
    }

    public void setIntentFilter(IntentFilter intentFilter) {
        this.intentFilter = intentFilter;
    }
}