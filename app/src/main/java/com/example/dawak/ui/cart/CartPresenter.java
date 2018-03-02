package com.example.dawak.ui.cart;


import com.example.dawak.R;
import com.example.dawak.data.DataManager;
import com.example.dawak.model.Order;
import com.example.dawak.ui.base.BaseLisener;
import com.example.dawak.ui.base.BasePresenter;

import java.util.ArrayList;

import javax.inject.Inject;

public class CartPresenter<V extends CartContract.View> extends BasePresenter<V>
        implements CartContract.Presenter<V>, CartContract.Interactor {

    @Inject
    public CartPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getOrders(String userId) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getDataManager().getOrders(userId, this);
        } else {
            getMvpView().showMessage(R.string.error_no_iternet_connection);
        }
    }

    @Override
    public void onSuccess(ArrayList<Order> orders) {
        if (isViewAttached()) {
            getMvpView().hideLoading();
            getMvpView().showOrders(orders);
        }
    }

    @Override
    public void onOrderAdded(Order order) {
        if (isViewAttached()) {
            getMvpView().hideLoading();
            getMvpView().onOrderAdded(order);
        }
    }

    @Override
    public void onOrderChange(Order order) {
        if (isViewAttached()) {
            getMvpView().hideLoading();
            getMvpView().onOrderChange(order);
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