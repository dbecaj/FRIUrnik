package me.dbecaj.friurnik.data.models.schedule;

/**
 * Created by Dominik on 20-Oct-17.
 */

public class SubjectModel {

    private String name;
    private String classroom;
    private int startHour;
    private int endHour;

    public SubjectModel(String name, String classroom, int startHour, int endHour) {
        this.name = name;
        this.classroom = classroom;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public String getName() {
        return name;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public String getClassroom() {
        return classroom;
    }
}
