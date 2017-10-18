package me.dbecaj.friurnik.ui.login;

import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.data.system.ResourceProvider;
import timber.log.Timber;

/**
 * Created by Dominik on 15-Oct-17.
 */

public class LoginPresenter implements LoginMvp.Presenter {

    LoginMvp.View view;

    public LoginPresenter(LoginMvp.View view) {
        this.view = view;
    }

    @Override
    public void cancel() {

    }

    @Override
    public void processNextClicked(String studentId) {
        if(studentId.isEmpty()) {
            view.showStudentIdInputError(ResourceProvider.getString(R.string.error_pleaseInsertNumber));
            return;
        }
        // Temporary solution (user is dumb)
        else if(studentId.length() != 8) {
            view.showStudentIdInputError(ResourceProvider.getString(R.string.error_invalidStudentId));
            return;
        }

        int id = 0;
        try {
            id = Integer.parseInt(studentId);
        }
        catch (NumberFormatException e) {
            view.showStudentIdInputError(ResourceProvider.getString(R.string.error_notANumber));
            Timber.d(e.getMessage());
            return;
        }

        view.showProgress();
        view.showScheduleActivity();
        view.hideProgress();
    }
}
