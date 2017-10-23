package me.dbecaj.friurnik.ui.login;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import me.dbecaj.friurnik.R;
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

        verify(view).showStudentIdInputError(ResourceProvider
                .getString(R.string.error_empty_student_id));
    }
}