package me.dbecaj.friurnik.ui.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.ui.add.di.AddActivityComponent;
import me.dbecaj.friurnik.ui.add.di.AddActivivityModule;
import me.dbecaj.friurnik.ui.add.di.DaggerAddActivityComponent;

/**
 * Created by Dominik on 04-Nov-17.
 */

public class AddActivity extends AppCompatActivity implements AddMvp.View {

    private AddMvp.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this);

        AddActivityComponent component = DaggerAddActivityComponent.builder()
                .addActivivityModule(new AddActivivityModule(this))
                .build();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMessage(int resId) {

    }

    @Override
    public void showError(int resId) {

    }

}
