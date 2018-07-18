package me.dbecaj.friurnik.ui.schedule;

import android.os.Handler;
import android.os.Looper;

import java.util.Calendar;
import java.util.List;

import me.dbecaj.friurnik.BuildConfig;
import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.data.interactors.GenericListener;
import me.dbecaj.friurnik.data.interactors.schedule.ScheduleInteractor;
import me.dbecaj.friurnik.data.interactors.schedule.ScheduleInteractorDatabaseImp;
import me.dbecaj.friurnik.data.interactors.schedule.ScheduleInteractorNetworkImp;
import me.dbecaj.friurnik.data.interactors.student.StudentInteractor;
import me.dbecaj.friurnik.data.interactors.student.StudentInteractorImp;
import me.dbecaj.friurnik.data.models.ScheduleModel;
import me.dbecaj.friurnik.data.models.StudentModel;
import me.dbecaj.friurnik.data.system.SystemStatus;
import timber.log.Timber;

/**
 * Created by HP on 10/18/2017.
 */

public class SchedulePresenter implements ScheduleMvp.Presenter {

    private ScheduleMvp.View view;
    private final StudentModel student;

    public SchedulePresenter(ScheduleMvp.View view) {
        this.view = view;

        student = new StudentInteractorImp().getDefaultStudent();
    }

    @Override
    public void loadSchedule(ScheduleInteractor.ScheduleListener listener) {
        loadSchedule(listener, false);
    }

    @Override
    public void loadSchedule(ScheduleInteractor.ScheduleListener listener, boolean forceNetwork) {
        ScheduleInteractor interactor = new ScheduleInteractorDatabaseImp();
        // If we don't have the schedule we load it from the network
        if (!interactor.hasSchedule(student.getStudentId()) || forceNetwork) {
            view.showProgress();
            interactor = new ScheduleInteractorNetworkImp();
        }

        interactor.getSchedule(student.getStudentId(), listener);
    }

    @Override
    public void refreshSchedule() {
        ScheduleInteractorDatabaseImp interactor = new ScheduleInteractorDatabaseImp();
        // Delete the current schedule in database and update the schedule
        if (!SystemStatus.isNetworkAvailable() ||
                !interactor.deleteSchedule(student.getStudentId())) {
            view.showError(R.string.error_delete_schedule);
            return;
        }

        processDayChange(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
    }

    @Override
    public void processDayChange(final int day) {
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        loadSchedule(new ScheduleInteractor.ScheduleListener() {
            @Override
            public void sucessful(final ScheduleModel schedule) {
                // To access the main UI thread we need to use a Handler
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.showSchedule();
                        view.changeDay(schedule, day);
                        view.hideProgress();
                    }
                });
            }

            @Override
            public void failure(final int resId) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.hideSchedule();
                        view.showError(resId);
                        view.hideProgress();
                    }
                });
            }
        });
    }

    @Override
    public void cancel() {

    }
}
