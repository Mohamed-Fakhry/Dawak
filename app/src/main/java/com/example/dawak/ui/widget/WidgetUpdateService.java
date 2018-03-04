package com.example.dawak.ui.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.dawak.R;
import com.example.dawak.model.Order;

import java.util.ArrayList;

/**
 * Created by Mohamed Fakhry on 04/03/2018.
 */

public class WidgetUpdateService extends IntentService {

    public static final String ACTION_UPDATE_APP_WIDGETS = "com.example.dawak.ui.widget.widgetupdateservice.update_app_widget";
    public static final String ACTION_UPDATE_LIST_VIEW = "com.example.dawak.ui.widget.widgetupdateservice.update_app_widget_list";

    private static final String EXTRA_ORDERS = "EXTRA_ORDERS";

    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_APP_WIDGETS.equals(action)) {
                handleActionUpdateAppWidgets();
            } else if (ACTION_UPDATE_LIST_VIEW.equals(action)) {
                handleActionUpdateListView();
            }
        }
    }

    private void handleActionUpdateListView() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, DawakWidget.class));

        DawakWidget.updateAllAppWidget(this, appWidgetManager, appWidgetIds);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.orderLV);
    }

    private void handleActionUpdateAppWidgets() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, DawakWidget.class));

        DawakWidget.updateAllAppWidget(this, appWidgetManager, appWidgetIds);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.orderLV);
    }

    public static void startActionUpdateAppWidgets(Context context) {
        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.setAction(ACTION_UPDATE_APP_WIDGETS);
        context.startService(intent);
    }

    public static void startActionUpdateAppWidgets(Context context, ArrayList<Order> orders) {
        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.setAction(ACTION_UPDATE_LIST_VIEW);
        intent.putExtra(EXTRA_ORDERS, orders);
        context.startService(intent);
    }
}
