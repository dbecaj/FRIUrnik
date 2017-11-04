package me.dbecaj.friurnik.ui.add.di;

import dagger.Component;
import me.dbecaj.friurnik.ui.add.AddMvp;

/**
 * Created by Dominik on 04-Nov-17.
 */

@Component(modules = AddActivivityModule.class)
public interface AddActivityComponent {

    AddMvp.Presenter getPresenter();

}
