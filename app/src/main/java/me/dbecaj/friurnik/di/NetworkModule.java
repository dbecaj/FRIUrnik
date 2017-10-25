package me.dbecaj.friurnik.di;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by Dominik on 19-Oct-17.
 */

@Module
public class NetworkModule {

    @Provides
    public OkHttpClient okHttpClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    public HttpLoggingInterceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor
                .Logger() {
            @Override
            public void log(String message) {
                Timber.d(message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return interceptor;
    }

}
