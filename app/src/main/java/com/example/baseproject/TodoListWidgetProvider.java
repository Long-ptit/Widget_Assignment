package com.example.baseproject;

import android.app.Application;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.baseproject.room.NoteRepository;

/**
 * Implementation of App Widget functionality.
 */
public class TodoListWidgetProvider extends AppWidgetProvider {
    public static final String ACTION_CLICK_COLLECTION = "ACTION_CLICK_COLLECTION";
    public static final String KEY_ID = "KEY_ID";
    public static final String KEY_DONE = "KEY_DONE";

    public PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, WidgetService.class);

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.todo_list_widget);
            views.setRemoteAdapter(R.id.list_item, intent);
            views.setEmptyView(R.id.list_item, R.id.appwidget_text);
            views.setPendingIntentTemplate(R.id.list_item, getPendingSelfIntent(context, ACTION_CLICK_COLLECTION));

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
       if (intent.getAction().equals(ACTION_CLICK_COLLECTION)) {
           int id = intent.getIntExtra(KEY_ID, 0);
           boolean isCheck = intent.getBooleanExtra(KEY_DONE, false);
           new NoteRepository((Application) context.getApplicationContext()).updateDataWork(id, isCheck);
       }
        super.onReceive(context, intent);
    }

}