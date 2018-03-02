package com.example.dawak.ui.map;


import com.example.dawak.data.DataManager;
import com.example.dawak.model.Pharmacy;
import com.example.dawak.ui.base.BaseLisener;
import com.example.dawak.ui.base.BasePresenter;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

public class MapPresenter<V extends MapContract.View> extends BasePresenter<V>
        implements MapContract.Presenter<V>, BaseLisener<Pharmacy, String> {

    @Inject
    public MapPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void showNearPharmacies(LatLng location) {
        if (getMvpView().isNetworkConnected()) {
            getDataManager().getNearPharmacies(location, this);
        }
    }

    @Override
    public void onSuccess(Pharmacy pharmacy) {
        if (isViewAttached()) {
            getMvpView().showNearPharmacies(pharmacy);
        }
    }

    @Override
    public void onFail(String error) {
        if (isViewAttached())
            getMvpView().showMessage(error);
    }
}