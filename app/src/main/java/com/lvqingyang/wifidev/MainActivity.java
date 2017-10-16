package com.lvqingyang.wifidev;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import static android.net.wifi.WifiManager.WIFI_STATE_DISABLED;
import static android.net.wifi.WifiManager.WIFI_STATE_DISABLING;
import static android.net.wifi.WifiManager.WIFI_STATE_ENABLED;
import static android.net.wifi.WifiManager.WIFI_STATE_ENABLING;
import static android.net.wifi.WifiManager.WIFI_STATE_UNKNOWN;

public class MainActivity extends AppCompatActivity {

    private WifiManager mWifiManager;
    private android.widget.Switch swwifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.swwifi = (Switch) findViewById(R.id.sw_wifi);

        mWifiManager= (WifiManager) getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        //set wifi switch state
        swwifi.setChecked(mWifiManager.isWifiEnabled());

        swwifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mWifiManager.setWifiEnabled(isChecked);
            }
        });

        registerBroadcast();
    }

    //注册接收器
    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction()== WifiManager.WIFI_STATE_CHANGED_ACTION) {
                    switch (intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WIFI_STATE_UNKNOWN)) {
                        case WIFI_STATE_DISABLED:{
                            Toast.makeText(context, "WiFi disabled", Toast.LENGTH_SHORT).show();
                            swwifi.setChecked(false);
                            break;
                        }
                        case WIFI_STATE_DISABLING:{
                            Toast.makeText(context, "WiFi disabling", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case WIFI_STATE_ENABLED :{
                            Toast.makeText(context, "WiFi enabled", Toast.LENGTH_SHORT).show();
                            swwifi.setChecked(true);
                            break;
                        }
                        case WIFI_STATE_ENABLING:{
                            Toast.makeText(context, "WiFi enabling", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case WIFI_STATE_UNKNOWN:{
                            Toast.makeText(context, "WiFi state unknown", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            }
        }, filter);
    }
}
