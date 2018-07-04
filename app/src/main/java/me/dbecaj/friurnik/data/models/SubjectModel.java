package me.dbecaj.friurnik.data.models;

/**
 * Created by Dominik on 20-Oct-17.
 */

public class SubjectModel {

    private String name;
    private String classroom;
    private int startHour;
    private int endHour;
    private String day;
    private String professor;

    public SubjectModel(String name, String classroom, int startHour, int endHour, String day, String professor) {
        this.name = name;
        this.classroom = classroom;
        this.startHour = startHour;
        this.endHour = endHour;
        this.day = day;
        this.professor = professor;
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

    public int getDuration() {
        return endHour - startHour;
    }

    public String getDay() {
        return day;
    }

    public String getProfessor() {
        return professor;
    }

    @Override
    public String toString() {
        return String.format("%s;%d:00-%d:00;%s;%s", name, startHour, endHour, day, professor);
    }
}
