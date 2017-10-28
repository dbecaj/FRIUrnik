package me.dbecaj.friurnik.data.models.schedule;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;

import timber.log.Timber;

/**
 * Created by HP on 10/18/2017.
 */

public class ScheduleModel {

    public static final String MON = "MON";
    public static final String TUE = "TUE";
    public static final String WED = "WED";
    public static final String THU = "THU";
    public static final String FRI = "FRI";

    // URA {7:00, 21:00} DAN {PON, PET)
    // [URA]->(PON, TOR, SRE, CET, PET)
    private HashMap<String, ArrayList<SubjectModel>> schedule = new HashMap<>(5);
    private boolean empty = true;
    private int lastHour = -1;

    private void initHash() {
        schedule.put(MON, new ArrayList<SubjectModel>());
        schedule.put(TUE, new ArrayList<SubjectModel>());
        schedule.put(WED, new ArrayList<SubjectModel>());
        schedule.put(THU, new ArrayList<SubjectModel>());
        schedule.put(FRI, new ArrayList<SubjectModel>());
    }

    public void parseHtml(String html){
        initHash();

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

               if(day.className().contains(ScheduleModel.MON)) {
                   insertIntoHash(ScheduleModel.MON, extractSubject(day, hourCount));

               }
               else if(day.className().contains(ScheduleModel.TUE)) {
                   insertIntoHash(ScheduleModel.TUE, extractSubject(day, hourCount));
               }
               else if(day.className().contains(ScheduleModel.WED)) {
                   insertIntoHash(ScheduleModel.WED, extractSubject(day, hourCount));
               }
               else if(day.className().contains(ScheduleModel.THU)) {
                   insertIntoHash(ScheduleModel.THU, extractSubject(day, hourCount));
               }
               else if(day.className().contains(ScheduleModel.FRI)) {
                   insertIntoHash(ScheduleModel.FRI, extractSubject(day, hourCount));
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

    private void insertIntoHash(String day, SubjectModel subject) {
        schedule.get(day).add(subject);
        empty = false;
    }

    public void printOutSchedule() {
        for(String day : schedule.keySet()) {
            Timber.d(day + "===================>");
            for(SubjectModel subjectModel : schedule.get(day)) {
                Timber.d(subjectModel.getName() + ":" + subjectModel.getClassroom() +
                        ":" + String.valueOf(subjectModel.getStartHour()) + "/" +
                        String.valueOf(subjectModel.getEndHour()));
            }
        }
    }

    public HashMap<String, ArrayList<SubjectModel>> getSchedule() {
        return schedule;
    }

    public boolean isEmpty() {
        return empty;
    }

    public int getLastHour() {
        return lastHour;
    }
}
