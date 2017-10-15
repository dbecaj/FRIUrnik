package me.dbecaj.friurnik;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Dominik on 15-Oct-17.
 */

public class FRIUrnikApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initTimber();
    }

    private void initTimber() {
        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return String.valueOf(element.getLineNumber()) + "::" +
                            super.createStackElementTag(element);
                }
            });
        }
    }
}
