package com.example.dawak.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.dawak.R;
import com.example.dawak.ui.MainActivity;
import com.example.dawak.ui.login.LoginActivity;

/**
 * Implementation of App Widget functionality.
 */
public class DawakWidget extends AppWidgetProvider {

    public static int randomNumber = 5;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; ++i) {
            RemoteViews remoteViews = getOrderRemoteV(context, appWidgetIds[i]);
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews getOrderRemoteV(Context context, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.dawak_widget);

        Intent appIntent = new Intent(context, LoginActivity.class);
        PendingIntent appPendingIntent = PendingIntent
                .getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.shortcut, appPendingIntent);

        return remoteViews;
    }

    public static void updateOrdersWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetId, null);
            RemoteViews rv = getOrderListRemoteView(context, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }
    }

    private static RemoteViews getOrderListRemoteView(Context context, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(), R.layout.dawak_widget);

        Intent appIntent = MainActivity.getStartIntent(context);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.shortcut, appPendingIntent);

        remoteViews.setTextViewText(R.id.messageTV, context.getString(R.string.go_map));

        return remoteViews;
    }
}

