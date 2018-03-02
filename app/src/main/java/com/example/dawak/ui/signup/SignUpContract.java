package com.example.dawak.ui.signup;


import com.example.dawak.ui.base.MvpPresenter;
import com.example.dawak.ui.base.MvpView;

public interface SignUpContract {

    interface View extends MvpView {
        void onSignUpSuccess();

        void onSignUpFail();
    }

    interface Presenter<V extends View> extends MvpPresenter<V> {
        void signUp(String email, String password);
    }
}
