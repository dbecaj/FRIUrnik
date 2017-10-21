package me.dbecaj.friurnik.data.models.schedule;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import me.dbecaj.friurnik.data.interactors.schedule.ScheduleInteractor;
import timber.log.Timber;

/**
 * Created by HP on 10/18/2017.
 */

public class ScheduleModel {

    private SubjectModel schedule[][] = new SubjectModel[5][13];

    public void parseXml(String xml) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
        }
        finally {

        }
    }

    public SubjectModel[][] getSchedule() {
        return schedule;
    }
}
