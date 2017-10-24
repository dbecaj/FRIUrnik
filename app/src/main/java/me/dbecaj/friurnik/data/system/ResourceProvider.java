package me.dbecaj.friurnik.data.system;

import android.app.Application;
import android.content.res.Resources;

import me.dbecaj.friurnik.FRIUrnikApp;

/**
 * Created by HP on 10/17/2017.
 */

public class ResourceProvider {

    protected ResourceProvider() {}

    public static String getString(int resId) {
        return FRIUrnikApp.getInstance().getString(resId);
    }

}
