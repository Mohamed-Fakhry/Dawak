package com.example.dawak.ui.cart;


import com.example.dawak.model.Order;
import com.example.dawak.ui.base.BaseLisener;
import com.example.dawak.ui.base.MvpPresenter;
import com.example.dawak.ui.base.MvpView;

import java.util.ArrayList;

public interface CartContract {

    interface View extends MvpView {
        void showOrders(ArrayList<Order> orders);

        void onOrderAdded(Order order);

        void onOrderChange(Order order);
    }

    interface Presenter<V extends View> extends MvpPresenter<V> {
        void getOrders(String userId);
    }

    interface Interactor extends BaseLisener<ArrayList<Order>, String> {
        void onOrderAdded(Order order);

        void onOrderChange(Order order);
    }
}