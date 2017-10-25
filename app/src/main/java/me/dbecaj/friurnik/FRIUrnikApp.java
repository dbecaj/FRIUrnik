package me.dbecaj.friurnik;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

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
        initDbFlow();

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

    private void initDbFlow() {
        FlowManager.init(this);
    }

    public static FRIUrnikApp getInstance() {
        return instance;
    }
}
