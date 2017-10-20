package me.dbecaj.friurnik.data.interactors.student;

/**
 * Created by HP on 10/18/2017.
 */

public interface StudentInteractor {

    interface StudentListener {
        void successful(int studentId);
        void failure(String error);
    }

    void getStudent(StudentListener listener);
    void saveStudent(int studentId);

}
