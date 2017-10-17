package me.dbecaj.friurnik;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Dominik on 15-Oct-17.
 */

public class FRIUrnikApp extends Application {

    private static FRIUrnikApp instance;

    @Override
    public void onCreate() {
        super.onCreate();

        initTimber();

        instance = this;
    }

    private void initTimber() {
        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return  super.createStackElementTag(element) + "::" +
                            String.valueOf(element.getLineNumber());
                }
            });
        }
    }

    public static FRIUrnikApp getInstance() {
        return instance;
    }
}
