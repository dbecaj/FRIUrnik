package me.dbecaj.friurnik.data.models;

import com.google.gson.Gson;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

import me.dbecaj.friurnik.data.database.FRIUrnikDatabase;
import timber.log.Timber;

/**
 * Created by HP on 10/18/2017.
 */

@Table(database = FRIUrnikDatabase.class)
public class ScheduleModel extends BaseModel{

    @Column
    @PrimaryKey
    private long studentId;

    @Column
    @NotNull
    private String jsonSchedule;

    @Column
    @NotNull
    private int lastHour = -1;

    public static final int MON = 0;
    public static final int TUE = 1;
    public static final int WED = 2;
    public static final int THU = 3;
    public static final int FRI = 4;

    // URA {7:00, 21:00} DAN {PON, PET)
    // [URA]->(PON, TOR, SRE, CET, PET)
    private List<List<SubjectModel>> schedule = new ArrayList<>(5);

    private boolean empty = true;

    private void initSchedule() {
        for(int i = 0; i < 5; i++) {
            schedule.add(new ArrayList<SubjectModel>(5));
        }
    }

    public void parseJson(String json) {
        Gson gson = new Gson();
        schedule = gson.fromJson(json, schedule.getClass());
        empty = false;
    }

    public void parseHtml(String html){
        initSchedule();

        empty = true;
        Document document = Jsoup.parse(html);
        Element table = document.body().getElementsByTag("table").first();
        int hourCount = 7;
        for(Element hour : table.getElementsByAttributeValue("class", "timetable")) {
           for(Element day : hour.children().next()) {
               // This are usually the first row and are just used for padding the table in HTML
               if(day.children().size() == 0) {
                   continue;
               }

               if(day.className().contains("MON")) {
                   insertSubject(MON, extractSubject(day, hourCount));
               }
               else if(day.className().contains("TUE")) {
                   insertSubject(TUE, extractSubject(day, hourCount));
               }
               else if(day.className().contains("WEN")) {
                   insertSubject(WED, extractSubject(day, hourCount));
               }
               else if(day.className().contains("THU")) {
                   insertSubject(THU, extractSubject(day, hourCount));
               }
               else if(day.className().contains("FRI")) {
                   insertSubject(FRI, extractSubject(day, hourCount));
               }
           }
           hourCount++;
        }

        printOutSchedule();
    }

    private SubjectModel extractSubject(Element day, int hourCount) {
        int startHour = hourCount;
        int endHour = startHour + Integer.parseInt(day.attributes().get("rowspan"));
        if(endHour > lastHour) {
            lastHour = endHour;
        }
        String name = "";
        String classroom = "";

        // Just getting the subject info which is inside a div tag
        Element firstDiv = day.getElementsByTag("div").first();
        for(Element subjectInfo : firstDiv.getElementsByTag("a")) {
            if(subjectInfo.className().equals("activity")) {
                name = subjectInfo.text();
            }
            else if(subjectInfo.className().equals("classroom")) {
                classroom = subjectInfo.text();
            }
        }

        return new SubjectModel(name, classroom, startHour, endHour);
    }

    private void insertSubject(int day, SubjectModel subject) {
        schedule.get(day).add(subject);
        empty = false;
    }

    public void printOutSchedule() {
        for (int dayIndex = 0; dayIndex < schedule.size(); dayIndex++) {
            String day = "";
            switch (dayIndex) {
                case MON:
                    day = "MON";
                    break;
                case TUE:
                    day = "TUE";
                    break;
                case WED:
                    day = "WED";
                    break;
                case THU:
                    day = "THU";
                    break;
                case FRI:
                    day = "FRI";
                    break;
            }

            Timber.d(day + "===================>");
            for (int subjectIndex = 0; subjectIndex < schedule.get(dayIndex).size(); subjectIndex++) {
                SubjectModel subjectModel = schedule.get(dayIndex).get(subjectIndex);
                Timber.d(subjectModel.getName() + ":" + subjectModel.getClassroom() +
                        ":" + String.valueOf(subjectModel.getStartHour()) + "/" +
                        String.valueOf(subjectModel.getEndHour()));
            }
        }
    }

    public List<List<SubjectModel>> getSchedule() {
        return schedule;
    }

    public boolean isEmpty() {
        return empty;
    }

    public int getLastHour() {
        return lastHour;
    }

    protected void setLastHour(int lastHour) {
        this.lastHour = lastHour;
    }

    public String getJsonSchedule() {
        return jsonSchedule;
    }

    public void setJsonSchedule(String jsonSchedule) {
        this.jsonSchedule = jsonSchedule;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }
}
