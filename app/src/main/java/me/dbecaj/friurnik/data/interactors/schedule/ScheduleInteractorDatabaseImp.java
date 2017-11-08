package me.dbecaj.friurnik.data.interactors.schedule;

import com.google.gson.Gson;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.data.interactors.GenericListener;
import me.dbecaj.friurnik.data.models.ScheduleModel;
import me.dbecaj.friurnik.data.models.ScheduleModel_Table;

/**
 * Created by Dominik on 30-Oct-17.
 */

public class ScheduleInteractorDatabaseImp implements ScheduleInteractor {

    public ScheduleModel getSchedule(long studentId) {
        List<ScheduleModel> schedules = SQLite.select().from(ScheduleModel.class)
                .where(ScheduleModel_Table.studentId.is(studentId))
                .queryList();
        if(schedules.isEmpty()) {
            return null;
        }

        return schedules.get(0);
    }

    @Override
    public void getSchedule(long studentId, ScheduleListener listener) {
        List<ScheduleModel> schedules = SQLite.select().from(ScheduleModel.class)
                .where(ScheduleModel_Table.studentId.is(studentId))
                .queryList();

        if(schedules.isEmpty()) {
            listener.failure(R.string.error_no_schedule_saved_under_this_student_id);
            return;
        }

        ScheduleModel schedule = schedules.get(0);
        schedule.parseJson(schedule.getJsonSchedule());
        if(schedule.isEmpty()) {
            listener.failure(R.string.error_empty_schedule);
            return;
        }

        listener.sucessful(schedule);
    }

    public boolean saveSchedule(ScheduleModel schedule,long studentId) {
        Gson gson = new Gson();
        schedule.setJsonSchedule(gson.toJson(schedule.getSchedule()));
        schedule.setStudentId(studentId);

        if(schedule.exists())
            return schedule.update();

        return schedule.save();
    }

    public boolean hasSchedule(long studentId) {
        List<ScheduleModel> schedules = SQLite.select().from(ScheduleModel.class)
                .where(ScheduleModel_Table.studentId.is(studentId))
                .queryList();

        return !schedules.isEmpty();
    }

    public void deleteSchedule(long studentId, GenericListener listener) {
        if(!hasSchedule(studentId)) {
            listener.failure(R.string.error_student_not_found_in_database);
            return;
        }

        SQLite.delete().from(ScheduleModel.class).where(ScheduleModel_Table.studentId.is(studentId))
                .execute();
        getSchedule(studentId).delete();
        listener.success();
    }
}
