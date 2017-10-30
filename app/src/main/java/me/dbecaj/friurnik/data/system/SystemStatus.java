package me.dbecaj.friurnik.data.system;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import me.dbecaj.friurnik.FRIUrnikApp;

/**
 * Created by Dominik on 30-Oct-17.
 */

public class SystemStatus {

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                FRIUrnikApp.getInstance().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
