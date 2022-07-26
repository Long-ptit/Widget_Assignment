package com.example.baseproject.util;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;

import com.example.baseproject.R;
import com.example.baseproject.TodoListWidgetProvider;

public class Util {

    public static void updateData(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        ComponentName thisAppWidget = new ComponentName(
                context.getApplicationContext().getPackageName(), TodoListWidgetProvider.class.getName()
        );
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_item);
    }
}
