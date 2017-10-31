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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.data.models.ScheduleModel;
import me.dbecaj.friurnik.data.models.SubjectModel;
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
        gridLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        gridLayout.setVisibility(View.VISIBLE);
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
        int endHour = schedule.getLastHour();

        // We want to have 1 more empty hour at the end of our schedule for styling
        if(endHour != 21) {
            endHour++;
        }

        // +1 is for the day row which is already in and -1 is to get the length not the index
        gridLayout.setRowCount(endHour - (startHour-1) + 1);
        gridLayout.setColumnCount(6);

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
        for(int i = 1; i < gridLayout.getRowCount(); i++) {
            TextView hourCell = new TextView(this);
            hourCell.setGravity(Gravity.CENTER);

            int hour = startHour + i-1;
            hourCell.setText(String.valueOf(hour) + getString(R.string.time_hour_after));
            hourCell.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            hourCell.setBackground(getDrawable(R.drawable.cell_border));

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.rowSpec = GridLayout.spec(i, 1, 1f);
            params.columnSpec = GridLayout.spec(0, 1);
            params.width = (int)getResources().getDimension(R.dimen.schedule_time_cells_margin);

            gridLayout.addView(hourCell, params);
        }

        // Populate GridLayout with subjects
        for(int dayIndex = 0; dayIndex < schedule.getSchedule().size(); dayIndex++) {

            for(SubjectModel subject : schedule.getSchedule().get(dayIndex)) {
                // Parent layout for subject/s
                LinearLayout subjectHolder = new LinearLayout(this);
                subjectHolder.setGravity(Gravity.CENTER);
                subjectHolder.setOrientation(LinearLayout.HORIZONTAL);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(subject.getStartHour() - (startHour-1),
                        subject.getDuration(), 1f);
                params.columnSpec = GridLayout.spec(dayIndex + 1, 1, 1f);
                gridLayout.addView(subjectHolder, params);

                View subjectCell = getLayoutInflater().inflate(R.layout.subject_layout, subjectHolder);
                TextView name = (TextView)subjectCell.findViewById(R.id.subject_name);
                TextView classroom = (TextView)subjectCell.findViewById(R.id.subject_classroom);
                name.setText(subject.getName());
                classroom.setText(subject.getClassroom());
            }
        }
    }
}
