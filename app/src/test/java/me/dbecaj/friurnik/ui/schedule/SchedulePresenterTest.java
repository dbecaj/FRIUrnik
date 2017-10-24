package me.dbecaj.friurnik.ui.schedule;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SchedulePresenterTest {

    private ScheduleMvp.Presenter presenter;

    @Mock
    private ScheduleMvp.View view;

    @Before
    public void setUp() throws Exception {
        presenter = new SchedulePresenter(view);
    }
}