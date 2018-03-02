package com.example.dawak.ui.pharmacy.details;


import com.example.dawak.R;
import com.example.dawak.data.DataManager;
import com.example.dawak.model.Order;
import com.example.dawak.model.Pharmacy;
import com.example.dawak.ui.base.BaseLisener;
import com.example.dawak.ui.base.BasePresenter;

import javax.inject.Inject;

public class PharmacyDetailsPresenter<V extends PharmacyDetailsContract.View> extends BasePresenter<V>
        implements PharmacyDetailsContract.Presenter<V>, BaseLisener<Boolean, String> {

    @Inject
    public PharmacyDetailsPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void askMedicine(String medicineName, int quantity, String userId, Pharmacy pharmacy) {
        Order order = new Order();
        if (medicineName == null || medicineName.isEmpty()) {
            getMvpView().showMessage(R.string.error_medicine_name_required);
            return;
        }

        order.setUserId(userId);
        order.setName(medicineName);
        order.setQuantity(quantity);
        order.setPharmacy(pharmacy);

        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getDataManager().askMedicine(order, this);
        } else {
            getMvpView().showMessage(R.string.error_no_iternet_connection);
        }
    }

    @Override
    public void onSuccess(Boolean data) {
        if (isViewAttached()) {
            getMvpView().hideLoading();
            getMvpView().onSuccess();
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