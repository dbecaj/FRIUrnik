package me.dbecaj.friurnik.ui;

/**
 * Created by Dominik on 15-Oct-17.
 */

public interface BaseMvp {

    interface View {

        void showProgress();
        void hideProgress();
        void showError(String error);
    }

    interface Presenter {

        void cancel();
    }
}
