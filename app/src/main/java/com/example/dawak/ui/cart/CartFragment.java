package com.example.dawak.ui.cart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dawak.R;
import com.example.dawak.di.component.ActivityComponent;
import com.example.dawak.model.Order;
import com.example.dawak.ui.base.BaseFragment;
import com.example.dawak.ui.cart.adapter.OrderAdapter;
import com.example.dawak.ui.widget.WidgetUpdateService;
import com.google.gson.Gson;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartFragment extends BaseFragment implements CartContract.View {

    private String title;

    @Inject
    CartContract.Presenter<CartContract.View> presenter;

    @Inject
    OrderAdapter adapter;
    @Inject
    ArrayList<Order> orders;

    @BindView(R.id.orderRV)
    RecyclerView orderRV;

    public static CartFragment newInstance(String title) {
        CartFragment fragment = new CartFragment();
        fragment.title = title;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    protected void setUp(View view) {
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
            SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String userId = preferences.getString("userId", null);
            presenter.getOrders(userId);
        }
        orderRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        orderRV.setAdapter(adapter);
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void showOrders(ArrayList<Order> orders) {
        adapter.addOrders(orders);
    }

    @Override
    public void onOrderAdded(Order order) {
        adapter.addOrder(order);
        updateWidget();
    }

    @Override
    public void onOrderChange(Order order) {
        adapter.change(order);
        updateWidget();
    }

    public void updateWidget() {
        ArrayList<Order> orders = adapter.getOrders();
        String ordersJson = new Gson().toJson(orders);
        try {
            SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            preferences.edit().putString("orders", ordersJson).apply();
            WidgetUpdateService.startActionUpdateAppWidgets(getBaseActivity(), orders);
        }catch (Exception e) {}
    }

}
