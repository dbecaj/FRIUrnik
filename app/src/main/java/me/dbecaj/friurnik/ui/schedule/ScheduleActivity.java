package me.dbecaj.friurnik.ui.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.data.models.schedule.ScheduleModel;
import me.dbecaj.friurnik.ui.schedule.di.DaggerScheduleComponent;
import me.dbecaj.friurnik.ui.schedule.di.ScheduleComponent;
import me.dbecaj.friurnik.ui.schedule.di.ScheduleModule;

/**
 * Created by HP on 10/18/2017.
 */

public class ScheduleActivity extends AppCompatActivity implements ScheduleMvp.View {

    private ScheduleMvp.Presenter presenter;

    @BindView(R.id.schedule_gridLayout)
    GridLayout gridLayout;

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
    public void showMessage(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    public static Intent buildIntent(Context context) {
        Intent intent = new Intent(context, ScheduleActivity.class);

        return intent;
    }

    @Override
    public void showSchedule(ScheduleModel schedule) {
        int startHour = 7;
        
        LinearLayout subjectLayout = new LinearLayout(this);
        subjectLayout.setOrientation(LinearLayout.HORIZONTAL);
        View subjectView = getLayoutInflater().inflate(R.layout.subject_layout, subjectLayout);
    }
}
