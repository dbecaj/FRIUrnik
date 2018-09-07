package me.dbecaj.friurnik.services;

import android.app.job.JobParameters;
import android.app.job.JobService;

import timber.log.Timber;

/**
 * Created by Dominik on 13-Nov-17.
 */

public class ScheduleJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Timber.d("Hello from ScheduleJobService");

        // We return true because the network request is executed on a separated thread
        // This is just for testing
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
