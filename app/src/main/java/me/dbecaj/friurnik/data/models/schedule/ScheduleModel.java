package me.dbecaj.friurnik.data.models.schedule;

import timber.log.Timber;

/**
 * Created by HP on 10/18/2017.
 */

public class ScheduleModel {

    private SubjectModel schedule[][] = new SubjectModel[5][13];

    public void parseXml(String xml) {
        Timber.d(xml);
    }

    public SubjectModel[][] getSchedule() {
        return schedule;
    }
}
