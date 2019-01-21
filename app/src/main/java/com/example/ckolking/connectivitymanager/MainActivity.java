package com.example.ckolking.connectivitymanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getConnectedNetworks(this);
        getConnectivityStatusString(this);
    }

    private static List<NetworkInfo> getConnectedNetworks(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return null;
        }
        final List<NetworkInfo> list = new ArrayList<NetworkInfo>();
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Network[] networkInfoList = cm.getAllNetworks();
            if (networkInfoList != null) {
                final int length = networkInfoList.length;
                for (int i = 0; i < length; i++) {
                    Log.d("CEK connection manager - get networks","network = " + networkInfoList[i].toString());
//                    if (networkInfoList[i].getState() == NetworkInfo.State.CONNECTED) {
//                        list.add(networkInfoList[i]);
//                    }
                }
            }
//        } else {
//            final Network[] networks = cm.getAllNetworks();
//            if (networks != null && networks.length > 0) {
//                NetworkInfo info;
//                for (Network network : networks) {
//                    info = cm.getNetworkInfo(network);
//                    if (info != null && info.getState() == NetworkInfo.State.CONNECTED) {
//                        list.add(info);
//                    }
//                }
//            }
//        }
        return list;
    }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        Network activeNetwork = cm.getActiveNetwork();
        Log.d("CEK connection manager", "active network string is: " + activeNetwork.toString());
        if (null != activeNetwork) {
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(activeNetwork);
            if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                return TYPE_WIFI;

            if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static void getConnectivityStatusString(Context context) {

        int conn = getConnectivityStatus(context);
        String status = null;
        if (conn == TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        Log.d("CEK connection manager - on create", "current active is: " + status );
    }
}
