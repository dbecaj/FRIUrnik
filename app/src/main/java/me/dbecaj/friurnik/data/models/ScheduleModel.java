package me.dbecaj.friurnik.data.models;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.dbecaj.friurnik.BuildConfig;
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

    public enum Day {
        MO("MO"),
        TU("TU"),
        WE("WE"),
        TH("TH"),
        FR("FR");

        private String value;
        Day(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Day parseDay(String value) {
            for (Day day : values()) {
                if (day.getValue().equalsIgnoreCase(value)) {
                    return day;
                }
            }

            return null;
        }
    }

    private HashMap<Day, List<SubjectModel>> schedule = new HashMap<>(5);

    public ScheduleModel() {
        initSchedule();
    }

    private void initSchedule() {
        for (Day day : Day.values()) {
            schedule.put(day, new ArrayList<SubjectModel>());
        }
    }

    public void parseJson(String json) {
        initSchedule();
        Timber.d(json);

        Gson gson = new Gson();
        JsonObject rootArray = gson.fromJson(json, JsonObject.class);
        for(Map.Entry<String, JsonElement> entry : rootArray.entrySet()) {
            for (JsonElement subjectElement : entry.getValue().getAsJsonArray()) {
                JsonObject subject = subjectElement.getAsJsonObject();
                String classroom = subject.get("classroom").getAsString();
                int endHour = subject.get("endHour").getAsInt();
                String name = subject.get("name").getAsString();
                int startHour = subject.get("startHour").getAsInt();
                String day = subject.get("day").getAsString();
                String professor = subject.get("professor").getAsString();

                schedule.get(Day.parseDay(entry.getKey())).add(new SubjectModel(name, classroom,
                        startHour, endHour, day, professor));
            }
        }
    }

    public void parseICal(String data){
        initSchedule();

        // Emit all the metadata
        data = data.substring(data.indexOf("BEGIN:VEVENT"));
        data = data.substring(0, data.lastIndexOf("END:VEVENT"));
        String[] subjectsData = data.split("END:VEVENT");
        for (String subjectData : subjectsData) {
            SubjectModel subject = extractICalSubject(subjectData.split("\\n"));
            insertSubject(subject);
        }

        if(BuildConfig.DEBUG) {
            printOutSchedule();
        }
    }

    private SubjectModel extractICalSubject(String[] subjectData) {
        HashMap<String, String> data = new HashMap<>();
        for (String lineData : subjectData) {
            String[] splitData = lineData.split(":");
            if (splitData.length < 2) {
                continue;
            }
            // We split by the ; to get only core descriptor
            data.put(splitData[0].split(";")[0], splitData[1]);
        }

        String name = data.get("SUMMARY");
        String classroom = data.get("LOCATION");
        int startHour = Integer.parseInt(data.get("DTSTART").split("T")[1]
                .substring(0, 2));
        int endHour = Integer.parseInt(data.get("DTEND").split("T")[1]
                .substring(0, 2));
        String[] dayData = data.get("RRULE").split("=");
        String day = dayData[dayData.length-1].trim();
        String professor = data.get("DESCRIPTION").split("\\\\")[1].substring(1);

        return new SubjectModel(name, classroom, startHour, endHour, day, professor);
    }

    private void insertSubject(SubjectModel subject) {
        schedule.get(Day.parseDay(subject.getDay())).add(subject);
    }

    public void printOutSchedule() {
        for (Day day : schedule.keySet()) {
            Timber.d(day.getValue() + "===================>");
            for (SubjectModel model : schedule.get(day)) {
                Timber.d(model.toString());
            }
        }
    }

    public HashMap<Day, List<SubjectModel>> getSchedule() {
        return schedule;
    }

    public boolean isEmpty() {
        for (Day day : schedule.keySet()) {
            if (!schedule.get(day).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public int getLastHour() {
        int lastHour = -1;
        for (Day day : schedule.keySet()) {
            for (SubjectModel subject : schedule.get(day)) {
                if (subject.getEndHour() > lastHour) {
                    lastHour = subject.getEndHour();
                }
            }
        }

        return lastHour;
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
