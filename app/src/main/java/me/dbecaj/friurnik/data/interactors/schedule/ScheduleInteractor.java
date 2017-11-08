package me.dbecaj.friurnik.data.interactors.schedule;

import me.dbecaj.friurnik.data.interactors.GenericListener;
import me.dbecaj.friurnik.data.models.ScheduleModel;
import me.dbecaj.friurnik.data.models.StudentModel;

/**
 * Created by HP on 10/18/2017.
 */

public interface ScheduleInteractor {

    interface ScheduleListener {
        void sucessful(ScheduleModel schedule);
        void failure(int resId);
    }

    void getSchedule(long studentId, ScheduleListener listener);
    boolean saveSchedule(ScheduleModel schedule, long studentId);
    boolean hasSchedule(long studentId);
    void deleteSchedule(long studentId, GenericListener listener);

}
