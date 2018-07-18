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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.data.models.ScheduleModel;
import me.dbecaj.friurnik.data.models.StudentModel;
import me.dbecaj.friurnik.data.models.SubjectModel;
import me.dbecaj.friurnik.services.ScheduleJobService;
import me.dbecaj.friurnik.ui.add.AddActivity;
import me.dbecaj.friurnik.utils.DateUtils;
import timber.log.Timber;

/**
 * Created by HP on 10/18/2017.
 */

public class ScheduleActivity extends AppCompatActivity implements ScheduleMvp.View,
        View.OnClickListener {

    private static final int UPDATE_JOB_ID = 1;

    private ScheduleMvp.Presenter presenter;
    private JobScheduler updateScheduler;
    private ScheduleListAdapter listAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.schedule_days_layout)
    LinearLayout daysLayout;

    @BindView(R.id.schedule_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.schedule_list)
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_activity);

        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this);

        hideSchedule();

        // Initialize presenter
        presenter = new SchedulePresenter(this);

        // Set up the ListView for the schedule
        hideSchedule();
        listAdapter = new ScheduleListAdapter(new ArrayList<SubjectModel>(), this);
        listView.setAdapter(listAdapter);

        // Set the schedule to the current day
        presenter.processDayChange(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));

        // Set up the toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white, null));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white, null));
        // Set the toolbar title to the current day and date
        toolbarTitle.setText(DateUtils.getDayDateTitle());

        // Connect all days buttons to the listener
        for (int i = 0; i < daysLayout.getChildCount(); i++) {
            daysLayout.getChildAt(i).setOnClickListener(this);
        }

        // Set up the refresh layout
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshSchedule();
            }
        });
        refreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.white,
                R.color.white);

        // Initialize update job scheduler for updating our schedules
        updateScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        constructScheduleJob();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                presenter.refreshSchedule();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void hideSchedule() {
        listView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showSchedule() {
        listView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        refreshLayout.setRefreshing(false);
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
    public void changeDay(ScheduleModel schedule, int day) {
        // Make the schedule visible
        showSchedule();

        // Change all button days to their default color
        for (int i = 0; i < daysLayout.getChildCount(); i++) {
            daysLayout.getChildAt(i).setBackgroundResource(R.color.colorPrimary);
        }

        List<SubjectModel> subjectList;
        switch (day) {
            case Calendar.MONDAY:
                daysLayout.getChildAt(0).setBackgroundResource(R.color.colorPrimaryDark);
                subjectList = schedule.getSchedule().get(ScheduleModel.Day.MO);
                break;
            case Calendar.TUESDAY:
                daysLayout.getChildAt(1).setBackgroundResource(R.color.colorPrimaryDark);
                subjectList = schedule.getSchedule().get(ScheduleModel.Day.TU);
                break;
            case Calendar.WEDNESDAY:
                daysLayout.getChildAt(2).setBackgroundResource(R.color.colorPrimaryDark);
                subjectList = schedule.getSchedule().get(ScheduleModel.Day.WE);
                break;
            case Calendar.THURSDAY:
                daysLayout.getChildAt(3).setBackgroundResource(R.color.colorPrimaryDark);
                subjectList = schedule.getSchedule().get(ScheduleModel.Day.TH);
                break;
            case Calendar.FRIDAY:
                daysLayout.getChildAt(4).setBackgroundResource(R.color.colorPrimaryDark);
                subjectList = schedule.getSchedule().get(ScheduleModel.Day.FR);
                break;
            default:
                // It's Saturday or Sunday
                daysLayout.getChildAt(0).setBackgroundResource(R.color.colorPrimaryDark);
                subjectList = schedule.getSchedule().get(ScheduleModel.Day.FR);
                break;
        }

        // Reset the current list view
        listAdapter.clearItems();
        listAdapter.notifyDataSetChanged();
        if (subjectList.isEmpty()) {
            return;
        }

        // Populate schedule
        for (int i = 0; i < subjectList.size(); i++) {
            SubjectModel currentSubject = subjectList.get(i);
            listAdapter.addItem(currentSubject);
        }
        listAdapter.notifyDataSetChanged();
    }

    // Event handler for all days buttons
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.schedule_button_mon:
                presenter.processDayChange(Calendar.MONDAY);
                break;
            case R.id.schedule_button_tue:
                presenter.processDayChange(Calendar.TUESDAY);
                break;
            case R.id.schedule_button_wed:
                presenter.processDayChange(Calendar.WEDNESDAY);
                break;
            case R.id.schedule_button_thu:
                presenter.processDayChange(Calendar.THURSDAY);
                break;
            case R.id.schedule_button_fri:
                presenter.processDayChange(Calendar.FRIDAY);
                break;
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
