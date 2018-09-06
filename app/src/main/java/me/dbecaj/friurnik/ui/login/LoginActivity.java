package me.dbecaj.friurnik.ui.login;

import android.app.Activity;
import android.content.Intent;
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

public class LoginActivity extends Activity implements LoginMvp.View {

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

        // If the database has default user saved the application will jump to ScheduleActivity
        presenter.loadDefaultUser();
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
        presenter.processNextClicked();
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
    public void showMessage(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showStudentIdInputError(int resId) {
        inputStudentNumber.setError(getString(resId));
    }

    @Override
    public void showScheduleActivity() {
        Intent intent = ScheduleActivity.buildIntent(this);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }

    @Override
    public String getStudentId() {
        return inputStudentNumber.getText().toString();
    }
}
