package me.dbecaj.friurnik.ui.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.ui.login.di.DaggerLoginActivityComponent;
import me.dbecaj.friurnik.ui.login.di.LoginActivityComponent;
import me.dbecaj.friurnik.ui.login.di.LoginActivityModule;
import me.dbecaj.friurnik.ui.schedule.ScheduleActivity;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity implements LoginMvp.View {

    @BindView(R.id.login_loginProgress)
    ProgressBar loginProgress;

    @BindView(R.id.login_inputStudentNumber)
    EditText inputStudentNumber;

    LoginMvp.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this);

        LoginActivityComponent component = DaggerLoginActivityComponent.builder()
                .loginActivityModule(new LoginActivityModule(this)).build();

        presenter = component.getPresenter();
    }

    @OnClick(R.id.login_buttonNext)
    protected void onNextClicked() {
        presenter.processNextClicked(inputStudentNumber.getText().toString());
    }

    @Override
    public void showProgress() {
        loginProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        loginProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showStudentIdInputError(String error) {
        inputStudentNumber.setError(error);
    }

    @Override
    public void showScheduleActivity() {
        Intent intent = ScheduleActivity.buildIntent(this);
        startActivity(intent);
    }
}
