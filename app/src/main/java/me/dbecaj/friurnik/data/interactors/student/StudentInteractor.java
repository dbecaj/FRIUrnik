package me.dbecaj.friurnik.data.interactors.student;

import java.util.List;

import me.dbecaj.friurnik.data.models.StudentModel;

/**
 * Created by HP on 10/18/2017.
 */

public interface StudentInteractor {

    interface StudentListener {
        void successful(StudentModel student);
        void failure(int resId);
    }

    void getDefaultStudent(StudentListener listener);
    boolean hasDefaultStudent();
    List<StudentModel> getAllStudents();
    void getStudent(long studentId, StudentListener listener);
    boolean hasStudent(long studentId);
    void saveStudent(long studentId, StudentListener listener);
    void saveStudent(long studentId, String nickname, StudentListener listener);

}
