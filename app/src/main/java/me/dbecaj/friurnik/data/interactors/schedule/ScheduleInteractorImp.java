package me.dbecaj.friurnik.data.interactors.schedule;

import java.io.IOException;

import me.dbecaj.friurnik.BuildConfig;
import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.data.models.schedule.ScheduleModel;
import me.dbecaj.friurnik.data.system.ResourceProvider;
import me.dbecaj.friurnik.di.DaggerNetworkComponent;
import me.dbecaj.friurnik.di.NetworkComponent;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by Dominik on 20-Oct-17.
 */

public class ScheduleInteractorImp implements ScheduleInteractor {

    @Override
    public void getSchedule(long studentId, final ScheduleListener listener) {
        NetworkComponent networkComponent = DaggerNetworkComponent.builder().build();
        Request request = new Request.Builder()
                .url(ResourceProvider.getString(R.string.url) + String.valueOf(studentId))
                .build();

        networkComponent.getOkHttp().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.failure(ResourceProvider.getString(R.string.error_retrievingSchedule));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()) {
                    if(BuildConfig.DEBUG) {
                        Timber.d(response.message());
                    }
                    listener.failure(ResourceProvider.getString(R.string.error_serverError));
                    return;
                }

                ScheduleModel scheduleModel = new ScheduleModel();
                scheduleModel.parseXml(response.body().string());

                listener.sucessful(scheduleModel);
            }
        });
    }

}
