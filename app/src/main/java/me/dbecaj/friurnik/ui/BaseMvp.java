package me.dbecaj.friurnik.ui;

/**
 * Created by Dominik on 15-Oct-17.
 */

public interface BaseMvp {

    interface View {

        void init();
        void showProgress();
        void hideProgress();
        void showMessage(int resId);
        void showMessage(String message);
        void showError(int resId);
        void showError(String error);
    }

    interface Presenter {

        void cancel();
    }
}
