package me.dbecaj.friurnik.ui.schedule;

import java.util.List;

import me.dbecaj.friurnik.data.models.ScheduleModel;
import me.dbecaj.friurnik.data.models.StudentModel;
import me.dbecaj.friurnik.ui.BaseMvp;

/**
 * Created by HP on 10/18/2017.
 */

public interface ScheduleMvp {

    interface View extends BaseMvp.View {

        void populateSchedule(ScheduleModel schedule);
        void hideSchedule();
        void showSchedule();
        void showAddActivity();
        void constructScheduleJob();
    }

    interface Presenter extends BaseMvp.Presenter {

        void loadSchedule();
        void loadSchedule(long studentId);
        void loadSchedule(long studentId, boolean forceNetworkLoad);
        void refreshSchedule();
        void processAddButton();
        void processDeleteStudent();
    }

}
