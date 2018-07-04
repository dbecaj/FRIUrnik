package me.dbecaj.friurnik.ui.schedule;

import android.os.Handler;
import android.os.Looper;

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
    private long studentId = -1;

    public SchedulePresenter(ScheduleMvp.View view) {
        this.view = view;
    }

    @Override
    public void loadSchedule() {

        StudentInteractor interactor = new StudentInteractorImp();
        interactor.getDefaultStudent(new StudentInteractor.StudentListener() {
            @Override
            public void successful(StudentModel student) {
                loadSchedule(student.getStudentId());
            }

            @Override
            public void failure(int resId) {
                view.showError(resId);
            }
        });
    }

    @Override
    public void loadSchedule(long studentId) {
        loadSchedule(studentId, false);
    }

    @Override
    public void loadSchedule(long studentId, boolean forceNetworkLoad) {
        // Set the studentId for further use
        this.studentId = studentId;

        // Check if the student exists in the database
        StudentInteractor studentInteractor = new StudentInteractorImp();
        if(!studentInteractor.hasStudent(studentId)) {
            view.showError(R.string.error_student_not_found_in_database);
            return;
        }

        // Load the student info into title and navigation drawer
        StudentModel student = studentInteractor.getStudent(studentId);
        view.showStudentIdTitle(student);

        // Check if the database doesn't have the schedule with this studentId or
        // if we specifically forced the network load
        ScheduleInteractor scheduleInteractor = null;
        ScheduleInteractorDatabaseImp databaseImp = new ScheduleInteractorDatabaseImp();
        if((forceNetworkLoad || !databaseImp.hasSchedule(studentId)) &&
                SystemStatus.isNetworkAvailable()) {
            // If not we will load the schedule from the network
            scheduleInteractor = new ScheduleInteractorNetworkImp();
        }
        else {
            scheduleInteractor = new ScheduleInteractorDatabaseImp();
        }


        // Load schedule
        view.showProgress();
        scheduleInteractor.getSchedule(studentId, new ScheduleInteractor.ScheduleListener() {
            @Override
            public void sucessful(final ScheduleModel schedule) {
                // If we load from network we need to access the UI thread from the main looper
                Handler mainHander = new Handler(Looper.getMainLooper());
                mainHander.post(new Runnable() {
                    @Override
                    public void run() {
                        view.populateSchedule(schedule);
                        view.hideProgress();
                    }
                });
            }

            @Override
            public void failure(final int resId) {
                Handler mainHander = new Handler(Looper.getMainLooper());
                mainHander.post(new Runnable() {
                    @Override
                    public void run() {
                        view.showError(resId);
                        view.hideProgress();
                        // If there is a problem hide the previous schedule
                        view.hideSchedule();
                    }
                });
            }
        });
    }

    @Override
    public void refreshSchedule() {
        if(studentId < 0) {
            throw new RuntimeException("studentId is not initialized!");
        }

        loadSchedule(studentId, true);
    }

    @Override
    public void processAddButton() {
        view.showAddActivity();
    }

    @Override
    public void processDeleteStudent() {
        if(studentId < 0) {
            throw new RuntimeException("studentId is not initialized!");
        }

        // Delete student from Schedule table and Student table
        StudentInteractor studentInteractor = new StudentInteractorImp();
        studentInteractor.deleteStudent(studentId, new GenericListener() {
            @Override
            public void failure(int resId) {
                view.showError(resId);
            }

            @Override
            public void success() {
                ScheduleInteractorDatabaseImp scheduleInteractor =
                        new ScheduleInteractorDatabaseImp();
                scheduleInteractor.deleteSchedule(studentId, new GenericListener() {
                    @Override
                    public void failure(int resId) {
                        if(resId == R.string.error_student_not_found_in_database) {
                            // If the student has faulty schedule it is not saved so overlook this error
                            if(BuildConfig.DEBUG) {
                                Timber.d(String.valueOf(studentId) + " student is " +
                                        "not found in database");
                            }

                            // Proceed with normal operations
                            success();
                        }
                        else {
                            view.showError(resId);
                        }
                    }

                    @Override
                    public void success() {
                        view.showMessage(R.string.student_deleted);
                        // Load the default student and update the student menu list
                        loadSchedule();
                    }
                });
            }
        });
    }

    @Override
    public void cancel() {

    }
}
