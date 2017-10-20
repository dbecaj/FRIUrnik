package me.dbecaj.friurnik.ui.login;

import me.dbecaj.friurnik.ui.BaseMvp;

/**
 * Created by Dominik on 15-Oct-17.
 */

public interface LoginMvp {

    interface View extends BaseMvp.View {

        void showStudentIdInputError(String error);
        void showScheduleActivity();
    }

    interface Presenter extends BaseMvp.Presenter {

        void processNextClicked(String studentId);
        void saveStudent(long studentId);
    }
}
