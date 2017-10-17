package me.dbecaj.friurnik.ui.login.di;

import dagger.Component;
import me.dbecaj.friurnik.ui.login.LoginMvp;
import me.dbecaj.friurnik.ui.login.LoginPresenter;

/**
 * Created by HP on 10/17/2017.
 */

@Component(modules = LoginActivityModule.class)
public interface LoginActivityComponent {

    LoginMvp.Presenter getPresenter();

}
