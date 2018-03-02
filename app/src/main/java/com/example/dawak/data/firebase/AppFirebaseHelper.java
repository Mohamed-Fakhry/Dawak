package com.example.dawak.data.firebase;


import android.support.annotation.NonNull;

import com.example.dawak.data.PharmacyResponed;
import com.example.dawak.model.Order;
import com.example.dawak.model.Pharmacy;
import com.example.dawak.ui.base.BaseLisener;
import com.example.dawak.ui.cart.CartContract;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryDataEventListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AppFirebaseHelper implements FirebaseHelper {

    private static final String PHARMACY = "pharmacy";
    private static final String USER = "user";
    private static final String ORDER = "order";
    private static final String DETAILS = "details";

    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private GeoFire geoFire;

    public AppFirebaseHelper() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        geoFire = new GeoFire(database.getReference().child(PHARMACY));
    }

    @Override
    public Task<AuthResult> signUp(String email, String password) {
        return auth.createUserWithEmailAndPassword(email, password);
    }

    @Override
    public Task<AuthResult> login(String email, String password) {
        return auth.signInWithEmailAndPassword(email, password);
    }

    @Override
    public void getNearPharmacies(LatLng location, final BaseLisener<Pharmacy, String> lisener) {
        geoFire.queryAtLocation(new GeoLocation(location.latitude, location.longitude), 30)
                .addGeoQueryDataEventListener(new GeoQueryDataEventListener() {
                    @Override
                    public void onDataEntered(DataSnapshot dataSnapshot, GeoLocation location) {
                        lisener.onSuccess(dataSnapshot.getValue(PharmacyResponed.class).getDetails());
                    }

                    @Override
                    public void onDataExited(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onDataMoved(DataSnapshot dataSnapshot, GeoLocation location) {

                    }

                    @Override
                    public void onDataChanged(DataSnapshot dataSnapshot, GeoLocation location) {

                    }

                    @Override
                    public void onGeoQueryReady() {

                    }

                    @Override
                    public void onGeoQueryError(DatabaseError error) {
                        lisener.onFail(error.getMessage());
                    }
                });
    }

    @Override
    public void askMedicine(final Order medicine, final BaseLisener<Boolean, String> lisener) {
        final DatabaseReference databaseReference = database.getReference()
                .child(USER).child(medicine.getUserId()).child(ORDER).push();

        databaseReference.setValue(medicine)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        database.getReference().child(PHARMACY).child(medicine.getPharmacy().getId()).child(DETAILS)
                                .child(ORDER).child(databaseReference.getKey()).setValue(medicine)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        lisener.onSuccess(true);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        lisener.onFail(e.getMessage());
                                    }
                                });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                lisener.onFail(e.getMessage());
            }
        });
    }

    @Override
    public void getOrders(String userId, final CartContract.Interactor lisener) {
        database.getReference().child(USER).child(userId).child(ORDER)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Order order = dataSnapshot.getValue(Order.class);
                        order.setId(dataSnapshot.getKey());
                        lisener.onOrderAdded(order);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Order order = dataSnapshot.getValue(Order.class);
                        order.setId(dataSnapshot.getKey());
                        lisener.onOrderChange(order);
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
