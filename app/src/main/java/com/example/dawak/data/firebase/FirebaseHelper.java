package com.example.dawak.data.firebase;


import com.example.dawak.model.Order;
import com.example.dawak.model.Pharmacy;
import com.example.dawak.ui.base.BaseLisener;
import com.example.dawak.ui.cart.CartContract;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

/**
 * Created by Mohamed Fakhry on 18/02/2018.
 */

public interface FirebaseHelper {

    Task<AuthResult> signUp(String email, String password);

    Task<AuthResult> login(String email, String password);

    void getNearPharmacies(LatLng location, BaseLisener<Pharmacy, String> lisener);

    void askMedicine(Order medicine, BaseLisener<Boolean, String> lisener);

    void getOrders(String userId, CartContract.Interactor lisener);
}
