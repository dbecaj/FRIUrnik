package me.dbecaj.friurnik.ui.schedule.di;

import dagger.Component;
import me.dbecaj.friurnik.ui.schedule.ScheduleMvp;
import me.dbecaj.friurnik.ui.schedule.SchedulePresenter;

/**
 * Created by HP on 10/18/2017.
 */

@Component(modules = ScheduleModule.class)
public interface ScheduleComponent {

    ScheduleMvp.Presenter getPresenter();

}
