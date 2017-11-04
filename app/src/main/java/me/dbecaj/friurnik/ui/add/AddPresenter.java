package me.dbecaj.friurnik.ui.add;

/**
 * Created by Dominik on 04-Nov-17.
 */

public class AddPresenter implements AddMvp.Presenter{

    private AddMvp.View view;

    public AddPresenter(AddMvp.View view) {
        this.view = view;
    }

    @Override
    public void cancel() {

    }
}
