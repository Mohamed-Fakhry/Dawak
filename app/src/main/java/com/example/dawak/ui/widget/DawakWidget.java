package com.example.dawak.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.example.dawak.R;
import com.example.dawak.model.Order;
import com.example.dawak.ui.MainActivity;
import com.example.dawak.ui.login.LoginActivity;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class DawakWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        WidgetUpdateService.startActionUpdateAppWidgets(context);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,int appWidgetId, Bundle newOptions) {
        WidgetUpdateService.startActionUpdateAppWidgets(context);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    public static void updateAllAppWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.dawak_widget);

        Intent appIntent = new Intent(context, LoginActivity.class);
        PendingIntent appPendingIntent = PendingIntent
                .getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews.setOnClickPendingIntent(R.id.shortcut, appPendingIntent);

        Intent serviceIntent = WidgetService.createIntent(context);
        remoteViews.setRemoteAdapter(R.id.orderLV, serviceIntent);

        remoteViews.setEmptyView(R.id.orderLV, R.id.shortcut);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }
}

