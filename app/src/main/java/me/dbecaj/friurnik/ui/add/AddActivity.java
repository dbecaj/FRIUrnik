package me.dbecaj.friurnik.ui.add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.ui.add.di.AddActivityComponent;
import me.dbecaj.friurnik.ui.add.di.AddActivivityModule;
import me.dbecaj.friurnik.ui.add.di.DaggerAddActivityComponent;
import me.dbecaj.friurnik.ui.schedule.ScheduleActivity;

/**
 * Created by Dominik on 04-Nov-17.
 */

public class AddActivity extends AppCompatActivity implements AddMvp.View {

    @BindView(R.id.add_input_nickname)
    EditText nicknameInput;

    @BindView(R.id.add_input_student_id)
    EditText studentIdInput;

    @BindView(R.id.add_save_progress)
    ProgressBar saveProgress;

    private AddMvp.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        init();
    }

    @OnClick(R.id.add_save_button)
    protected void onSavedClicked() {
        presenter.processSaveButton();
    }

    @Override
    public void init() {
        ButterKnife.bind(this);

        AddActivityComponent component = DaggerAddActivityComponent.builder()
                .addActivivityModule(new AddActivivityModule(this))
                .build();

        presenter = component.getPresenter();
    }

    @Override
    public void showProgress() {
        saveProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        saveProgress.setVisibility(View.INVISIBLE);
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
        studentIdInput.setError(getString(resId));
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
        return studentIdInput.getText().toString();
    }

    @Override
    public String getNickname() {
        return nicknameInput.getText().toString();
    }

    public static Intent buildIntent(Context context) {
        Intent intent = new Intent(context, AddActivity.class);

        return intent;
    }

}
