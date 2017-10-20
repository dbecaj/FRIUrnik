package me.dbecaj.friurnik.data.models.student;

/**
 * Created by Dominik on 20-Oct-17.
 */

public class StudentModel {

    private long studentId;

    public StudentModel(long studentId) {
        this.studentId = studentId;
    }

    public long getStudentId() {
        return studentId;
    }
}
