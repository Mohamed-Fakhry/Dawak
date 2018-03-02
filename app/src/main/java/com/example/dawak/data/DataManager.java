package com.example.dawak.data;


import com.example.dawak.model.Order;
import com.example.dawak.model.Pharmacy;
import com.example.dawak.ui.base.BaseLisener;
import com.example.dawak.ui.cart.CartContract;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public interface DataManager {

    void signUp(String email, String password, BaseLisener<String, String> lisener);

    void login(String email, String password, BaseLisener<String, String> lisener);

    void getNearPharmacies(LatLng location, BaseLisener<Pharmacy, String> lisener);

    void askMedicine(Order medicine, BaseLisener<Boolean , String> lisener);

    void getOrders(String userId, CartContract.Interactor lisener);
}
