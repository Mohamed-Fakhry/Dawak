package com.example.dawak.ui.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.dawak.R;
import com.example.dawak.model.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private ArrayList<Order> orders;

    public ListProvider(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @Override
    public void onCreate() {
        Log.d("test", "test");
    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String ordersJson = preferences.getString("orders", null);

        Type type = new TypeToken<ArrayList<Order>>(){}.getType();
        ArrayList<Order> orders = new Gson().fromJson(ordersJson, type);
        this.orders = orders;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return orders != null ? orders.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.item_wedgit_order);

        Resources resources = context.getResources();

        Order order = orders.get(position);

        remoteView.setTextViewText(R.id.medicineNameTV, order.getName());
        remoteView.setTextViewText(R.id.quantityTV, String.valueOf(order.getQuantity()));

        int messageRes;
        int colorRes;

        if (order.getAvailable() == null) {
            messageRes = R.string.pending;
            colorRes = R.color.colorPending;
        } else if (order.getAvailable()) {
            messageRes = R.string.available;
            colorRes = R.color.colorAvailable;
        } else {
            messageRes = R.string.not_available;
            colorRes = R.color.colorNotAvailable;
        }

        colorRes = resources.getColor(colorRes);
        String message = resources.getString(messageRes);

        remoteView.setTextViewText(R.id.messageTV, message);
        remoteView.setInt(R.id.messageL, "setBackgroundColor", colorRes);

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}
