package me.dbecaj.friurnik.ui.login;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.data.database.FRIUrnikDatabase;
import me.dbecaj.friurnik.data.models.student.StudentModel;
import me.dbecaj.friurnik.data.models.student.StudentModel_Table;
import me.dbecaj.friurnik.data.system.ResourceProvider;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    private LoginMvp.Presenter presenter;

    @Mock
    private LoginMvp.View view;

    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(view);
    }

    @Test
    public void shouldShowErrorMessageWhenStudentIdIsEmpty() throws Exception {
        when(view.getStudentId()).thenReturn("");
        presenter.processNextClicked();

        verify(view).showStudentIdInputError(R.string.error_empty_student_id);
    }

    @Test
    public void shouldShowErrorMessageWhenStudentIdHasIncorrectDigetsLength() throws Exception {
        when(view.getStudentId()).thenReturn("123");
        presenter.processNextClicked();

        verify(view).showStudentIdInputError(R.string.error_invalid_student_id);
    }

    @Test
    public void shouldShowErrorMessageWhenStudentIdAlreadyExistsInStudentTable() throws Exception {
        DatabaseDefinition database = FlowManager.getDatabase(FRIUrnikDatabase.class);
        StudentModel_Table table = new StudentModel_Table(database);
        table.insert(new StudentModel(63170050, true));
        when(view.getStudentId()).thenReturn("63170050");
        presenter.processNextClicked();

        verify(view).showStudentIdInputError(R.string.error_student_already_in_database);
    }

}