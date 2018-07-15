package me.dbecaj.friurnik.ui.login;

import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.data.interactors.student.StudentInteractor;
import me.dbecaj.friurnik.data.interactors.student.StudentInteractorImp;
import me.dbecaj.friurnik.data.models.StudentModel;
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
    public void processNextClicked() {
        String studentId = view.getStudentId();
        if(studentId.isEmpty()) {
            view.showStudentIdInputError(R.string.error_empty_student_id);
            return;
        }
        else if(studentId.length() != 8) {
            view.showStudentIdInputError(R.string.error_invalid_student_id);
            return;
        }

        int id = 0;
        try {
            id = Integer.parseInt(studentId);
        }
        catch (NumberFormatException e) {
            view.showStudentIdInputError(R.string.error_not_a_number);
            Timber.d(e.getMessage());
            return;
        }

        view.showProgress();
        saveStudent(id);
    }

    @Override
    public void saveStudent(long studentId) {
        StudentInteractor interactor = new StudentInteractorImp();
        interactor.saveStudent(studentId, new StudentInteractor.StudentListener() {
            @Override
            public void successful(StudentModel student) {
                view.showMessage(R.string.success_student_saved);
                view.showScheduleActivity();
                view.hideProgress();
            }

            @Override
            public void failure(int resId) {
                view.showError(resId);
                view.hideProgress();
            }
        });
    }

    @Override
    public void loadDefaultUser() {
        StudentInteractor interactor = new StudentInteractorImp();
        if(!interactor.hasDefaultStudent()) {
            return;
        }

        view.showScheduleActivity();
    }

}
