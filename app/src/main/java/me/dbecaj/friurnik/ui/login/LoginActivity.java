package me.dbecaj.friurnik.ui.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.dbecaj.friurnik.R;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Timber.d("LoginActivity");
    }
}
