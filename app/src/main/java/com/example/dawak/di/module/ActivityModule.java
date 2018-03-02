
package com.example.dawak.di.module;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


import com.example.dawak.di.ActivityContext;
import com.example.dawak.di.PerActivity;
import com.example.dawak.model.Order;
import com.example.dawak.model.Pharmacy;
import com.example.dawak.ui.cart.CartContract;
import com.example.dawak.ui.cart.CartPresenter;
import com.example.dawak.ui.cart.adapter.OrderAdapter;
import com.example.dawak.ui.login.LoginContract;
import com.example.dawak.ui.login.LoginPresenter;
import com.example.dawak.ui.map.MapContract;
import com.example.dawak.ui.map.MapPresenter;
import com.example.dawak.ui.pharmacy.details.PharmacyDetailsContract;
import com.example.dawak.ui.pharmacy.details.PharmacyDetailsPresenter;
import com.example.dawak.ui.signup.SignUpContract;
import com.example.dawak.ui.signup.SignUpPresenter;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dagger.Module;
import dagger.Provides;

/**
 * Created by janisharali on 27/01/17.
 */

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    @PerActivity
    FragmentManager provideViewPagerAdapter() {
        return mActivity.getSupportFragmentManager();
    }

    @Provides
    @PerActivity
    SignUpContract.Presenter<SignUpContract.View> provideSignUpPresenter
            (SignUpPresenter<SignUpContract.View> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginContract.Presenter<LoginContract.View> provideLoginPresenter
            (LoginPresenter<LoginContract.View> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    PharmacyDetailsContract.Presenter<PharmacyDetailsContract.View> providePharmacyDetailsPresenter
            (PharmacyDetailsPresenter<PharmacyDetailsContract.View> presenter) {
        return presenter;
    }

    @Provides
    CartContract.Presenter<CartContract.View> provideCartPresenter
            (CartPresenter<CartContract.View> presenter) {
        return presenter;
    }

    @Provides
    OrderAdapter provideOrderAdapter(ArrayList<Order> orders) {
        return new OrderAdapter(orders);
    }

    @Provides
    ArrayList<Order> provideOrders() {
        return new ArrayList<>();
    }

    @Provides
    @PerActivity
    Map<Marker, Pharmacy> provideLoginPresenterPharmacyMap() {
        return new HashMap<>();
    }

    @Provides
    MapContract.Presenter<MapContract.View> provideMapPresenter
            (MapPresenter<MapContract.View> presenter) {
        return presenter;
    }
}
