package me.dbecaj.friurnik.ui.login.di;

import dagger.Module;
import dagger.Provides;
import me.dbecaj.friurnik.ui.login.LoginMvp;
import me.dbecaj.friurnik.ui.login.LoginPresenter;

/**
 * Created by HP on 10/17/2017.
 */

@Module
public class LoginActivityModule {

    private LoginMvp.View view;

    public LoginActivityModule(LoginMvp.View view) {
        this.view = view;
    }

    @Provides
    public LoginMvp.View view() {
        return view;
    }

    @Provides
    public LoginMvp.Presenter loginPresenter(LoginMvp.View view) {
        return new LoginPresenter(view);
    }

}
