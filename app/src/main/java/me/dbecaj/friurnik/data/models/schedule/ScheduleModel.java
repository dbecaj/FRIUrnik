package me.dbecaj.friurnik.data.models.schedule;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import me.dbecaj.friurnik.data.interactors.schedule.ScheduleInteractor;
import timber.log.Timber;

/**
 * Created by HP on 10/18/2017.
 */

public class ScheduleModel {

    private SubjectModel schedule[][] = new SubjectModel[5][13];

    public void parseXml(String xml) throws XmlPullParserException, IOException {
        StringReader stringReader = new StringReader(xml);
        try {
            XmlPullParser parser = Xml.newPullParser();
            //parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stringReader);
            readFeed(parser);
        }
        finally {
            stringReader.close();
        }
    }

    private void readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        while(parser.nextTag() != XmlPullParser.END_TAG) {
            Timber.d(parser.getName());
        }
    }

    public SubjectModel[][] getSchedule() {
        return schedule;
    }
}
