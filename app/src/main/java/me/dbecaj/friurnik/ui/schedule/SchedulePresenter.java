package me.dbecaj.friurnik.ui.schedule;

import android.os.Handler;
import android.os.Looper;

import java.util.List;

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
        view.showNavigationDrawerStudent(student);

        // Check if the database doesn't have the schedule with this studentId or
        // if we specifically forced the network load
        ScheduleInteractor scheduleInteractor = new ScheduleInteractorDatabaseImp();
        if(forceNetworkLoad || !scheduleInteractor.hasSchedule(studentId)) {
            // If not we will load the schedule from the network
            scheduleInteractor = new ScheduleInteractorNetworkImp();
        }


        // Load schedule
        scheduleInteractor.getSchedule(studentId, new ScheduleInteractor.ScheduleListener() {
            @Override
            public void sucessful(final ScheduleModel schedule) {
                // If we load from network we need to access the UI thread from the main looper
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

        loadSchedule(studentId, true);
    }

    @Override
    public void loadStudentsMenu() {
        StudentInteractor interactor = new StudentInteractorImp();
        List<StudentModel> students = interactor.getAllStudents();
        for(StudentModel student : students) {
            if(student.getStudentId() == studentId) {
                students.remove(student);
                break;
            }
        }

        view.populateNavigationDrawerStudentMenu(students);
    }

    @Override
    public void processAddButton() {
        view.showAddActivity();
    }

    @Override
    public void cancel() {

    }
}
