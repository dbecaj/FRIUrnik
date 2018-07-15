package me.dbecaj.friurnik.data.interactors.student;

import java.util.List;

import me.dbecaj.friurnik.data.interactors.GenericListener;
import me.dbecaj.friurnik.data.models.StudentModel;

/**
 * Created by HP on 10/18/2017.
 */

public interface StudentInteractor {

    interface StudentListener {
        void successful(StudentModel student);
        void failure(int resId);
    }

    StudentModel getDefaultStudent();
    boolean hasDefaultStudent();
    List<StudentModel> getAllStudents();
    StudentModel getStudent(long studentId);
    boolean hasStudent(long studentId);
    void saveStudent(long studentId, StudentListener listener);
    void saveStudent(long studentId, String nickname, StudentListener listener);
    void deleteStudent(long studentId, GenericListener listener);
    void updateStudent(long studentId, String nickname, StudentListener listener);

}
