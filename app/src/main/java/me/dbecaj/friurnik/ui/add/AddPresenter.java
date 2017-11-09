package me.dbecaj.friurnik.ui.add;

import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.data.interactors.student.StudentInteractor;
import me.dbecaj.friurnik.data.interactors.student.StudentInteractorImp;
import me.dbecaj.friurnik.data.models.StudentModel;
import timber.log.Timber;

/**
 * Created by Dominik on 04-Nov-17.
 */

public class AddPresenter implements AddMvp.Presenter{

    private AddMvp.View view;
    private boolean editing = false;

    public AddPresenter(AddMvp.View view) {
        this.view = view;
    }

    @Override
    public void processSaveButton() {
        String studentId = view.getStudentId();
        String nickname = view.getNickname();

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
        if(!editing) {
            saveStudent(id, nickname);
        }
        else {
            updateStudent(id, nickname);
        }
    }

    @Override
    public void saveStudent(long studentId, String nickname) {
        StudentInteractor interactor = new StudentInteractorImp();
        interactor.saveStudent(studentId, nickname, new StudentInteractor.StudentListener() {
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
    public void updateStudent(long studentId, String nickname) {
        StudentInteractor interactor = new StudentInteractorImp();
        interactor.updateStudent(studentId, nickname, new StudentInteractor.StudentListener() {
            @Override
            public void successful(StudentModel student) {
                view.showMessage(R.string.success_student_updated);
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
    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    @Override
    public void cancel() {

    }
}
