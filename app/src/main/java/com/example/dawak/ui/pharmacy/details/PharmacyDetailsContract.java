package com.example.dawak.ui.pharmacy.details;


import com.example.dawak.model.Pharmacy;
import com.example.dawak.ui.base.MvpPresenter;
import com.example.dawak.ui.base.MvpView;

public interface PharmacyDetailsContract {

    interface View extends MvpView {
        void onSuccess();
    }

    interface Presenter<V extends View> extends MvpPresenter<V> {
        void askMedicine(String medicineName, int quantity, String userId, Pharmacy pharmacy);
    }
}