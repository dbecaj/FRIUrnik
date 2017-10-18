package me.dbecaj.friurnik.ui.schedule.di;

import dagger.Module;
import dagger.Provides;
import me.dbecaj.friurnik.ui.schedule.ScheduleMvp;
import me.dbecaj.friurnik.ui.schedule.SchedulePresenter;

/**
 * Created by HP on 10/18/2017.
 */

@Module
public class ScheduleModule {

    private ScheduleMvp.View view;

    public ScheduleModule(ScheduleMvp.View view) {
        this.view = view;
    }

    @Provides
    public ScheduleMvp.Presenter presenter(ScheduleMvp.View view) {
        return new SchedulePresenter(view);
    }

    @Provides
    public ScheduleMvp.View view() {
        return view;
    }

}
