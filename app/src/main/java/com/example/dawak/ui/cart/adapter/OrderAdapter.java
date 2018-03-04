package com.example.dawak.ui.cart.adapter;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.dawak.R;
import com.example.dawak.model.Order;
import com.example.dawak.ui.widget.DawakWidget;

import java.util.ArrayList;

/**
 * Created by Mohamed Fakhry on 02/03/2018.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderVH> {

    ArrayList<Order> orders;

    public OrderAdapter(ArrayList<Order> orders) {
        this.orders = orders;
    }

    @Override
    public OrderVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderVH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_oreder, null));
    }

    @Override
    public void onBindViewHolder(OrderVH holder, int position) {
        holder.bind(orders.get(position));
    }

    public void addOrder(Order order) {
        if (orders != null) {
            orders.add(order);
            notifyDataSetChanged();
        }
    }

    public void addOrders(ArrayList<Order> orders) {
        if (orders != null) {
            this.orders.addAll(orders);
            notifyDataSetChanged();
        }
    }

    public void change(Order order) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).equals(order)) {
                orders.set(i, order);
                notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
}
