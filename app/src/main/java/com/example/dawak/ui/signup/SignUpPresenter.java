package com.example.dawak.ui.signup;


import com.example.dawak.R;
import com.example.dawak.data.DataManager;
import com.example.dawak.ui.base.BaseLisener;
import com.example.dawak.ui.base.BasePresenter;
import com.example.dawak.utils.CommonUtils;

import javax.inject.Inject;

public class SignUpPresenter<V extends SignUpContract.View>
        extends BasePresenter<V> implements SignUpContract.Presenter<V>, BaseLisener<String, String> {

    @Inject
    public SignUpPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void signUp(String email, String password) {

        if (email.isEmpty()) {
            getMvpView().showMessage(R.string.error_email_required);
            return;
        } else if (!CommonUtils.isEmailValid(email)) {
            getMvpView().showMessage(R.string.error_invalid_email);
            return;
        }

        if (password.isEmpty()) {
            getMvpView().showMessage(R.string.error_password_empty);
            return;
        }

        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getDataManager().signUp(email, password, this);
        } else {
            getMvpView().showMessage(R.string.error_no_iternet_connection);
        }
    }

    @Override
    public void onSuccess(String userId) {
        getMvpView().hideLoading();
        if (isViewAttached())
            getMvpView().onSignUpSuccess();
    }

    @Override
    public void onFail(String error) {
        if (isViewAttached()) {
            getMvpView().hideLoading();
            getMvpView().showMessage(error);
        }
    }
}
