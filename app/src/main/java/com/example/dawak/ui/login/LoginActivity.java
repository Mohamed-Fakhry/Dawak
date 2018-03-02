package com.example.dawak.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.example.dawak.R;
import com.example.dawak.di.component.ActivityComponent;
import com.example.dawak.ui.MainActivity;
import com.example.dawak.ui.base.BaseActivity;
import com.example.dawak.ui.signup.SignUpActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.emailET)
    EditText emailET;
    @BindView(R.id.passwordET)
    EditText passwordET;

    @Inject
    LoginContract.Presenter<LoginContract.View> presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        setUp();
    }

    @Override
    protected void setUp() {
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this));
            presenter.onAttach(this);
        }
    }

    @OnClick(R.id.signInB)
    public void signIn() {
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        presenter.login(email, password);
    }

    @OnClick(R.id.signUpL)
    public void navigateToSignUp() {
        startActivity(SignUpActivity.getStartIntent(this));
    }

    @Override
    public void openMainFragment() {
        startActivity(MainActivity.getStartIntent(this));
    }

    @Override
    public void saveUserId(String userId) {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        preferences.edit().putString("userId", userId).apply();
    }
}
