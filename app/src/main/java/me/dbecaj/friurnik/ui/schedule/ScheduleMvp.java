package me.dbecaj.friurnik.ui.schedule;

import me.dbecaj.friurnik.data.models.ScheduleModel;
import me.dbecaj.friurnik.ui.BaseMvp;

/**
 * Created by HP on 10/18/2017.
 */

public interface ScheduleMvp {

    interface View extends BaseMvp.View {

        void showSchedule(ScheduleModel schedule);
        void hideSchedule();
        void showSchedule();
    }

    interface Presenter extends BaseMvp.Presenter {

        void loadSchedule();
        void loadNetworkSchedule(long studentId);
        void loadDatabaseSchedule(long studentId);
        void refreshSchedule();
    }

}
