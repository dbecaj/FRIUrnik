package me.dbecaj.friurnik.ui.add.di;

import dagger.Module;
import dagger.Provides;
import me.dbecaj.friurnik.ui.add.AddMvp;
import me.dbecaj.friurnik.ui.add.AddPresenter;

/**
 * Created by Dominik on 04-Nov-17.
 */

@Module
public class AddActivivityModule {

    AddMvp.View view;

    public AddActivivityModule(AddMvp.View view) {
        this.view = view;
    }

    @Provides
    public AddMvp.View view() {
        return view;
    }

    @Provides
    public AddMvp.Presenter presenter(AddMvp.View view) {
        return new AddPresenter(view);
    }
}
