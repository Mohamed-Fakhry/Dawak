package com.example.dawak.ui.signup;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.example.dawak.R;
import com.example.dawak.di.component.ActivityComponent;
import com.example.dawak.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mohamed Fakhry on 18/02/2018.
 */

public class SignUpActivity extends BaseActivity implements SignUpContract.View {

    @BindView(R.id.emailET)
    EditText emailET;
    @BindView(R.id.passwordET)
    EditText passwordET;

    @Inject
    SignUpContract.Presenter<SignUpContract.View> presenter;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sign_up);
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

    @OnClick(R.id.signUpB)
    public void signUp() {
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        presenter.signUp(email, password);
    }

    @Override
    public void onSignUpSuccess() {
        finish();
    }

    @Override
    public void onSignUpFail() {
        showMessage("Fail");
    }
}
