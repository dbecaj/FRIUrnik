package me.dbecaj.friurnik.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.ui.schedule.ScheduleActivity;

public class LoginActivity extends AppCompatActivity implements LoginMvp.View {

    @BindView(R.id.login_login_progress)
    ProgressBar loginProgress;

    @BindView(R.id.login_input_student_id)
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

        presenter = new LoginPresenter(this);
    }

    @OnClick(R.id.login_button_next)
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
    public void showError(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
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
