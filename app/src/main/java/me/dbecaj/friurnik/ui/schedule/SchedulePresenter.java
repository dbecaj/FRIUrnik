package me.dbecaj.friurnik.ui.schedule;

import me.dbecaj.friurnik.data.interactors.schedule.ScheduleInteractor;
import me.dbecaj.friurnik.data.interactors.schedule.ScheduleInteractorImp;
import me.dbecaj.friurnik.data.models.schedule.ScheduleModel;

/**
 * Created by HP on 10/18/2017.
 */

public class SchedulePresenter implements ScheduleMvp.Presenter {

    private ScheduleMvp.View view;

    public SchedulePresenter(ScheduleMvp.View view) {
        this.view = view;
    }

    @Override
    public void loadSchedule(int studentId) {
        ScheduleInteractor scheduleInteractor = new ScheduleInteractorImp();
        scheduleInteractor.getSchedule(studentId, new ScheduleInteractor.ScheduleListener() {
            @Override
            public void sucessful(ScheduleModel schedule) {
                // Is called from another thread so it can not access UI thread!
            }

            @Override
            public void failure(String error) {
                // Same dilemma as successful()
            }
        });
    }

    @Override
    public void cancel() {

    }
}
