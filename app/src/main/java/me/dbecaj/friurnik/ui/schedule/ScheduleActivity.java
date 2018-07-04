package me.dbecaj.friurnik.ui.schedule;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.data.models.ScheduleModel;
import me.dbecaj.friurnik.data.models.StudentModel;
import me.dbecaj.friurnik.services.ScheduleJobService;
import me.dbecaj.friurnik.ui.add.AddActivity;
import timber.log.Timber;

/**
 * Created by HP on 10/18/2017.
 */

public class ScheduleActivity extends AppCompatActivity implements ScheduleMvp.View {

    private static final int UPDATE_JOB_ID = 1;

    private ScheduleMvp.Presenter presenter;
    private JobScheduler updateScheduler;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_activity);

        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this);

        // Initialize presenter
        presenter = new SchedulePresenter(this);
        presenter.loadSchedule();

        hideSchedule();

        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));

        // Initialize update job scheduler for updating our schedules
        updateScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        constructScheduleJob();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_schedule, menu);

        MenuItem addButton = menu.findItem(R.id.action_add);
        addButton.getIcon().setTint(getResources().getColor(R.color.white));

        return true;
    }

    @Override
    public void hideSchedule() {

    }

    @Override
    public void showSchedule() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.action_add:
                presenter.processAddButton();
                break;
            case R.id.action_delete:
                presenter.processDeleteStudent();
                break;
        }

        return super.onOptionsItemSelected(item);
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
    public void showError(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void populateSchedule(ScheduleModel schedule) {
        showSchedule();

        // TODO: Populate schedule
    }

    @Override
    public void showStudentIdTitle(StudentModel student) {
        if(student.hasNickname()) {
            toolbar.setTitle(String.valueOf(student.getStudentId()) + " " +
                    getString(R.string.navigation_drawer_nickname, student.getNickname()));
        }
        else {
            toolbar.setTitle(String.valueOf(student.getStudentId()));
        }
    }

    @Override
    public void constructScheduleJob() {
        // Tell our job on what interval we want the update to happen and it will persist
        // when the device is restarted among other things
        JobInfo.Builder builder = new JobInfo.Builder(UPDATE_JOB_ID, new ComponentName(this,
                ScheduleJobService.class))
                .setPeriodic(2000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true);

        // Start the job
        updateScheduler.schedule(builder.build());
    }

    @Override
    public void showAddActivity() {
        Intent intent = AddActivity.buildIntent(this);
        startActivity(intent);
    }

    public static Intent buildIntent(Context context) {
        Intent intent = new Intent(context, ScheduleActivity.class);

        return intent;
    }
}
