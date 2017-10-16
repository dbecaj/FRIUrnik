package me.dbecaj.friurnik.ui.login;

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

public class LoginActivity extends AppCompatActivity implements LoginMvp.View {

    @BindView(R.id.login_loginProgress)
    ProgressBar loginProgress;

    @BindView(R.id.login_inputStudentNumber)
    EditText inputStudentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        init();
    }

    @OnClick(R.id.login_buttonNext)
    protected void onNextClicked() {
        // Call LoginPresenter for action
    }

    private void init() {
        ButterKnife.bind(this);
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
}
