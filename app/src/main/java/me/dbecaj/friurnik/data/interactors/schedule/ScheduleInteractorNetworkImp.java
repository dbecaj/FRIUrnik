package me.dbecaj.friurnik.data.interactors.schedule;

import com.google.gson.Gson;

import java.io.IOException;

import me.dbecaj.friurnik.BuildConfig;
import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.data.interactors.GenericListener;
import me.dbecaj.friurnik.data.models.ScheduleModel;
import me.dbecaj.friurnik.data.system.ResourceProvider;
import me.dbecaj.friurnik.data.system.SystemStatus;
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

public class ScheduleInteractorNetworkImp implements ScheduleInteractor {

    @Override
    public void getSchedule(final long studentId, final ScheduleListener listener) {
        if(!SystemStatus.isNetworkAvailable()) {
            listener.failure(R.string.error_no_internet_connection);
            return;
        }

        NetworkComponent networkComponent = DaggerNetworkComponent.builder().build();
        Request request = new Request.Builder()
                .url(ResourceProvider.getString(R.string.url, studentId))
                .build();

        networkComponent.getOkHttp().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.failure(R.string.error_retrieving_schedule);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    if (BuildConfig.DEBUG) {
                        Timber.d(response.message());
                    }
                    listener.failure(R.string.error_server_error);
                    return;
                }

                String scheduleData = response.body().string();
                ScheduleModel scheduleModel = new ScheduleModel();

                scheduleModel.parseICal(scheduleData);
                if (scheduleModel.isEmpty()) {
                    listener.failure(R.string.error_empty_schedule);
                    return;
                }

                // Save the schedule in the database
                saveSchedule(scheduleModel, studentId);

                listener.sucessful(scheduleModel);
            }
        });
    }

    public boolean saveSchedule(ScheduleModel schedule, long studentId) {
        Gson gson = new Gson();
        schedule.setJsonSchedule(gson.toJson(schedule.getSchedule()));
        schedule.setStudentId(studentId);

        if(schedule.exists())
            return schedule.update();

        return schedule.save();
    }

    @Override
    public boolean hasSchedule(long studentId) {
        Timber.d("calling hasSchedule() in a Network implementation!");
        return false;
    }
}
