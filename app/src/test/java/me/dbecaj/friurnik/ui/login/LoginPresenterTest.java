package me.dbecaj.friurnik.ui.login;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import me.dbecaj.friurnik.R;

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
    public void shouldShowErrorMessageWhenStudentIdAlreadyExistsInDatabase() throws Exception {
        when(view.getStudentId()).thenReturn("63170050");
        presenter.processNextClicked();

        verify(view).showError(R.string.error_student_already_in_database);
    }
}