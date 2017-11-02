package me.dbecaj.friurnik.ui.schedule;

import android.os.Handler;
import android.os.Looper;

import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.data.interactors.schedule.ScheduleInteractor;
import me.dbecaj.friurnik.data.interactors.schedule.ScheduleInteractorDatabaseImp;
import me.dbecaj.friurnik.data.interactors.schedule.ScheduleInteractorNetworkImp;
import me.dbecaj.friurnik.data.interactors.student.StudentInteractor;
import me.dbecaj.friurnik.data.interactors.student.StudentInteractorImp;
import me.dbecaj.friurnik.data.models.ScheduleModel;
import me.dbecaj.friurnik.data.models.StudentModel;
import me.dbecaj.friurnik.data.system.SystemStatus;

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
                view.showStudentIdTitle(student);
                loadDatabaseSchedule(student.getStudentId());
            }

            @Override
            public void failure(int resId) {
                view.showError(resId);
            }
        });
    }

    @Override
    public void loadDatabaseSchedule(final long studentId) {
        this.studentId = studentId;

        StudentInteractor interactor = new StudentInteractorImp();
        if(!interactor.hasStudent(studentId)) {
            view.showError(R.string.error_student_not_found_in_database);
            return;
        }

        interactor.getStudent(studentId, new StudentInteractor.StudentListener() {
            @Override
            public void successful(StudentModel student) {
                view.showStudentIdTitle(student);
                view.showNavigationDrawerStudent(student);
            }

            @Override
            public void failure(int resId) {
                view.showError(resId);
            }
        });

        view.showProgress();
        final ScheduleInteractor dbInteractor = new ScheduleInteractorDatabaseImp();
        dbInteractor.getSchedule(studentId, new ScheduleInteractor.ScheduleListener() {
            @Override
            public void sucessful(final ScheduleModel schedule) {
                Handler mainHander = new Handler(Looper.getMainLooper());
                mainHander.post(new Runnable() {
                    @Override
                    public void run() {
                        view.showSchedule(schedule);
                        view.hideProgress();
                    }
                });
            }

            @Override
            public void failure(final int resId) {
                if(resId == R.string.error_no_schedule_saved_under_this_student_id) {
                    loadNetworkSchedule(studentId);
                }
                else {
                    view.showError(resId);
                    view.hideProgress();
                }
            }
        });
    }

    @Override
    public void loadNetworkSchedule(final long studentId) {
        this.studentId = studentId;

        StudentInteractor interactor = new StudentInteractorImp();
        if(!interactor.hasStudent(studentId)) {
            view.showError(R.string.error_student_not_found_in_database);
            return;
        }

        interactor.getStudent(studentId, new StudentInteractor.StudentListener() {
            @Override
            public void successful(StudentModel student) {
                view.showStudentIdTitle(student);
                view.showNavigationDrawerStudent(student);
            }

            @Override
            public void failure(int resId) {
                view.showError(resId);
            }
        });

        view.showProgress();
        final ScheduleInteractor networkScheduleInteractor = new ScheduleInteractorNetworkImp();
        networkScheduleInteractor.getSchedule(studentId, new ScheduleInteractor.ScheduleListener() {
            @Override
            public void sucessful(final ScheduleModel schedule) {
                Handler mainHander = new Handler(Looper.getMainLooper());
                mainHander.post(new Runnable() {
                    @Override
                    public void run() {
                        view.showSchedule(schedule);
                        view.hideProgress();
                        networkScheduleInteractor.saveSchedule(schedule,studentId);
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

        loadNetworkSchedule(studentId);
    }

    @Override
    public void cancel() {

    }
}
