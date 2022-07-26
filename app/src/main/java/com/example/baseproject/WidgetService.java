package com.example.baseproject;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.core.content.ContextCompat;

import com.example.baseproject.room.Note;
import com.example.baseproject.room.NoteRepository;

import java.util.ArrayList;
import java.util.List;

public class WidgetService extends RemoteViewsService {
    private static final String TAG = "MyLog";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MyRemoteViewFactory(getApplicationContext());
    }

    class MyRemoteViewFactory implements RemoteViewsFactory {
        private final Context context;
        private NoteRepository noteRepository;
        private List<Note> mListData;

        public MyRemoteViewFactory(Context context) {
            this.context = context;
            mListData = new ArrayList<>();
            noteRepository = new NoteRepository((Application) context);
        }

        @Override
        public void onCreate() {
            Log.d(TAG, "Create data: ");
//            mListData = noteRepository.getListNote();
        }

        @Override
        public void onDataSetChanged() {
            mListData = noteRepository.getListNote();
        }

        @Override
        public void onDestroy() {
            mListData.clear();
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Note note = mListData.get(position);
            RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.item_widget);

            Intent fillInIntent = new Intent();
            if (note.isDone()) {
                remoteView.setTextViewText(R.id.tv_item, note.getContent());
                remoteView.setTextColor(R.id.tv_item, ContextCompat.getColor(context, R.color.red));
            } else {
                remoteView.setTextViewText(R.id.tv_item, note.getContent());
                remoteView.setTextColor(R.id.tv_item, ContextCompat.getColor(context, R.color.light_blue_200));
            }
            fillInIntent.putExtra(TodoListWidgetProvider.KEY_ID, note.getId());
            fillInIntent.putExtra(TodoListWidgetProvider.KEY_DONE, note.isDone());
            remoteView.setTextViewText(R.id.tv_item, note.getContent());
            remoteView.setOnClickFillInIntent(R.id.rootView, fillInIntent);
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

        @Override
        public long getItemId(int position) {
            return mListData.get(position).getId();
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
