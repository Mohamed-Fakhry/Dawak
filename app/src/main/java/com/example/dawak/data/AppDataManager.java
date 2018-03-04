package com.example.dawak.data;


import android.support.annotation.NonNull;

import com.example.dawak.data.firebase.AppFirebaseHelper;
import com.example.dawak.data.firebase.FirebaseHelper;
import com.example.dawak.model.Order;
import com.example.dawak.model.Pharmacy;
import com.example.dawak.ui.base.BaseLisener;
import com.example.dawak.ui.cart.CartContract;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Mohamed Fakhry on 18/02/2018.
 */
@Singleton
public class AppDataManager implements DataManager {

    FirebaseHelper firebaseAppHelper;

    @Inject
    public AppDataManager(AppFirebaseHelper firebaseAppHelper) {
        this.firebaseAppHelper = firebaseAppHelper;
    }

    @Override
    public void signUp(String email, String password, final BaseLisener<String, String> lisener) {
        firebaseAppHelper.signUp(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                    lisener.onSuccess(task.getResult().getUser().getUid());
                else
                    lisener.onFail(task.getException().getMessage());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                lisener.onFail(e.getMessage());
            }
        });
    }

    @Override
    public void login(String email, String password, final BaseLisener<String, String> lisener) {
        firebaseAppHelper.login(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                    lisener.onSuccess(task.getResult().getUser().getUid());
                else
                    lisener.onFail(task.getException().getMessage());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                lisener.onFail(e.getMessage());
            }
        });
    }

    @Override
    public void getNearPharmacies(LatLng location, BaseLisener<Pharmacy, String> lisener) {
        firebaseAppHelper.getNearPharmacies(location, lisener);
    }

    @Override
    public void askMedicine(Order medicine, BaseLisener<Boolean, String> lisener) {
        firebaseAppHelper.askMedicine(medicine, lisener);
    }

    @Override
    public void getOrders(String userId, CartContract.Interactor lisener) {
        firebaseAppHelper.getOrders(userId, lisener);
    }
}
