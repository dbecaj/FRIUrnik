package me.dbecaj.friurnik.ui.schedule;

import java.util.List;

import me.dbecaj.friurnik.data.interactors.GenericListener;
import me.dbecaj.friurnik.data.interactors.schedule.ScheduleInteractor;
import me.dbecaj.friurnik.data.models.ScheduleModel;
import me.dbecaj.friurnik.data.models.StudentModel;
import me.dbecaj.friurnik.ui.BaseMvp;

/**
 * Created by HP on 10/18/2017.
 */

public interface ScheduleMvp {

    interface View extends BaseMvp.View {

        void hideSchedule();
        void showSchedule();
        void showAddActivity();
        void constructScheduleJob();
        void changeDay(ScheduleModel schedule, int day);
        int getSelectedDay();
    }

    interface Presenter extends BaseMvp.Presenter {

        void loadSchedule(ScheduleInteractor.ScheduleListener listener);
        void loadSchedule(ScheduleInteractor.ScheduleListener listener, boolean forceNetworkLoad);
        void refreshSchedule();
        void processDayChange(int day);
    }

}
