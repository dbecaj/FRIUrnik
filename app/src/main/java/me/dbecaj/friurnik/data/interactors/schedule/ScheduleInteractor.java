package me.dbecaj.friurnik.data.interactors.schedule;

import me.dbecaj.friurnik.data.models.schedule.ScheduleModel;

/**
 * Created by HP on 10/18/2017.
 */

public interface ScheduleInteractor {

    interface ScheduleListener {
        void sucessful(ScheduleModel schedule);
        void failure(String error);
    }

    void getSchedule(int studentId, ScheduleListener listener);

}
