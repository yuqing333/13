package com.example.administrator.testapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Activity2 extends Activity {
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout3);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setBroadcast();
        Log.i("TAG", "=====注册======");
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
        Log.i("TAG", "=======销毁========");
    }

    /**
     * 动态
     */
    private void setBroadcast() {
        receiver = new MyNetworkChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);

    }

    public static class MyNetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //发生变化
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Network[] networks = connectivity.getAllNetworks();
                    StringBuilder sb = new StringBuilder();
                    for (Network mNetwork : networks) {
                        NetworkInfo networkInfo = connectivity.getNetworkInfo((mNetwork));
                        sb.append((networkInfo.getTypeName() + "connect is " + networkInfo.isConnected()));
                    }
                    Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    //否则调用旧版本方法
                    if (connectivity != null) {
                        //wifi状态
                        NetworkInfo wifiInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                        //移动状态
                        NetworkInfo mobileInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                        if (wifiInfo.isConnected() && mobileInfo.isConnected()) {
                            Toast.makeText(context, "Wifi 已连接，移动网络已连接", Toast.LENGTH_SHORT).show();
                        } else if (wifiInfo.isConnected() && !mobileInfo.isConnected()) {
                            Toast.makeText(context, "Wifi 已连接，移动网络已断开", Toast.LENGTH_SHORT).show();
                        } else if (!wifiInfo.isConnected() && mobileInfo.isConnected()) {
                            Toast.makeText(context, "Wifi 已断开，移动网络已连接", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "无连接", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }
}
