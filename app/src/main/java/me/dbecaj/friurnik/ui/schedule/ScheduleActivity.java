package me.dbecaj.friurnik.ui.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.data.models.schedule.ScheduleModel;
import me.dbecaj.friurnik.data.models.schedule.SubjectModel;
import me.dbecaj.friurnik.ui.schedule.di.DaggerScheduleComponent;
import me.dbecaj.friurnik.ui.schedule.di.ScheduleComponent;
import me.dbecaj.friurnik.ui.schedule.di.ScheduleModule;
import timber.log.Timber;

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
        int endHour = -1;
        // Getting the biggest end hour from our week so that we create enough time rows
        for(String day : schedule.getSchedule().keySet()) {
            for(SubjectModel subject : schedule.getSchedule().get(day)) {
                if(subject.getEndHour() > endHour) {
                    endHour = subject.getEndHour();
                }
            }
        }

        if(endHour < 0) {
            Timber.d("Schedule is EMPTY!");
            return;
        }

        Timber.d(String.valueOf(endHour - startHour));
        gridLayout.setRowCount(endHour - startHour);

        /*<TextView
        android:id="@+id/schedule_hour"
        android:layout_width="@dimen/schedule_time_cells_margin"
        android:layout_column="0"
        android:layout_row="1"
        android:layout_rowWeight="1"
        android:background="@drawable/cell_border"
        android:gravity="center"
        android:text="7:00"
        android:textAlignment="center"/>*/

        // Populate GridLayout with starting time cells
        for(int hour = startHour; hour < endHour; hour++) {
            TextView hourCell = new TextView(this);
            hourCell.setGravity(Gravity.CENTER);
            hourCell.setText(String.valueOf(hour) + getString(R.string.time_hour_after));
            hourCell.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec(hour - startHour, 1f), GridLayout.spec(0, 1f));
            params.width = (int)getResources().getDimension(R.dimen.schedule_time_cells_margin);
            hourCell.setBackground(getDrawable(R.drawable.cell_border));
            gridLayout.addView(hourCell, params);
        }
    }
}
