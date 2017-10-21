package me.dbecaj.friurnik.data.interactors.student;

import java.util.List;

import me.dbecaj.friurnik.data.models.student.StudentModel;

/**
 * Created by HP on 10/18/2017.
 */

public interface StudentInteractor {

    interface StudentListener {
        void successful(long studentId);
        void failure(String error);
    }

    void getDefaultStudent(StudentListener listener);
    boolean hasDefaultStudent();
    List<StudentModel> getAllStudents();
    void saveStudent(long studentId, StudentListener listener);

}
