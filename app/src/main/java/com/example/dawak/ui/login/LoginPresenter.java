package com.example.dawak.ui.login;

import com.example.dawak.R;
import com.example.dawak.data.DataManager;
import com.example.dawak.ui.base.BaseLisener;
import com.example.dawak.ui.base.BasePresenter;

import javax.inject.Inject;

public class LoginPresenter<V extends LoginContract.View> extends BasePresenter<V> implements LoginContract.Presenter<V>, BaseLisener<String, String> {

    @Inject
    public LoginPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void login(String email, String password) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getDataManager().login(email, password, this);
        } else {
            getMvpView().showMessage(R.string.error_no_iternet_connection);
        }
    }

    @Override
    public void onSuccess(String userId) {
        if (isViewAttached()) {
            getMvpView().hideLoading();
            getMvpView().saveUserId(userId);
            getMvpView().openMainFragment();
        }
    }

    @Override
    public void onFail(String error) {
        if (isViewAttached()) {
            getMvpView().hideLoading();
            getMvpView().showMessage(error);
        }
    }
}