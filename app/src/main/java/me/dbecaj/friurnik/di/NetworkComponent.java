package me.dbecaj.friurnik.di;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by Dominik on 19-Oct-17.
 */

@Component(modules = NetworkModule.class)
public interface NetworkComponent {

    OkHttpClient getOkHttp();

}
