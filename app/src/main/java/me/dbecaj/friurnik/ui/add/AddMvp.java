package me.dbecaj.friurnik.ui.add;

import me.dbecaj.friurnik.ui.BaseMvp;

/**
 * Created by Dominik on 04-Nov-17.
 */

public interface AddMvp {

    interface View extends BaseMvp.View {

        void showStudentIdInputError(int resId);
        void showScheduleActivity();
        String getStudentId();
        String getNickname();
    }

    interface Presenter extends BaseMvp.Presenter {

        void processSaveButton();
        void saveStudent(long studentId, String nickname);
    }

}
