package com.example.dawak.ui.map;


import com.example.dawak.model.Pharmacy;
import com.example.dawak.ui.base.MvpPresenter;
import com.example.dawak.ui.base.MvpView;
import com.google.android.gms.maps.model.LatLng;

public interface MapContract {

    interface View extends MvpView {
        void showNearPharmacies(Pharmacy pharmacy);
    }

    interface Presenter<V extends View> extends MvpPresenter<V> {
        void showNearPharmacies(LatLng location);
    }
}