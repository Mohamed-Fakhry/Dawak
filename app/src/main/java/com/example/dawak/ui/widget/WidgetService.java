package com.example.dawak.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViewsService;

import com.example.dawak.model.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class WidgetService extends RemoteViewsService {

    static Intent createIntent(Context context) {
        return new Intent(context, WidgetService.class);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        SharedPreferences preferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String ordersJson = preferences.getString("orders", null);

        Type type = new TypeToken<ArrayList<Order>>(){}.getType();
        ArrayList<Order> orders = new Gson().fromJson(ordersJson, type);

        return new ListProvider(this.getApplicationContext(), orders);
    }
}

