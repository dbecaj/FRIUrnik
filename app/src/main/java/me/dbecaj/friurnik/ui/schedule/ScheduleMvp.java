package me.dbecaj.friurnik.ui.schedule;

import me.dbecaj.friurnik.ui.BaseMvp;

/**
 * Created by HP on 10/18/2017.
 */

public interface ScheduleMvp {

    interface View extends BaseMvp.View {

    }

    interface Presenter extends BaseMvp.Presenter {

        void loadSchedule();
        void loadSchedule(long studentId);
    }

}
