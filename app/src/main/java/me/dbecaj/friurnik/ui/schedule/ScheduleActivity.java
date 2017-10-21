package me.dbecaj.friurnik.ui.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.IOException;

import butterknife.ButterKnife;
import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.data.system.ResourceProvider;
import me.dbecaj.friurnik.di.DaggerNetworkComponent;
import me.dbecaj.friurnik.di.NetworkComponent;
import me.dbecaj.friurnik.ui.schedule.di.DaggerScheduleComponent;
import me.dbecaj.friurnik.ui.schedule.di.ScheduleComponent;
import me.dbecaj.friurnik.ui.schedule.di.ScheduleModule;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by HP on 10/18/2017.
 */

public class ScheduleActivity extends AppCompatActivity implements ScheduleMvp.View {

    private ScheduleMvp.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_activity);

        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this);

        ScheduleComponent component = DaggerScheduleComponent.builder()
                .scheduleModule(new ScheduleModule(this)).build();
        presenter = component.getPresenter();

        presenter.loadSchedule();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    public static Intent buildIntent(Context context) {
        Intent intent = new Intent(context, ScheduleActivity.class);

        return intent;
    }
}
