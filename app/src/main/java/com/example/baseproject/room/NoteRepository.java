package com.example.baseproject.room;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.baseproject.R;
import com.example.baseproject.TodoListWidgetProvider;
import com.example.baseproject.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class NoteRepository {
    private NoteDAO mNoteDAO;
    private List<Note> listNote;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private Application application;

    public NoteRepository(Application application) {
        this.application = application;
        listNote = new ArrayList<>();
        MyDataBase myDataBase = MyDataBase.getInstance(application);
        mNoteDAO = myDataBase.noteDAO();
        mHandlerThread = new HandlerThread("IO");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());// bắt tay
    }



    public LiveData<List<Note>> getAllNotes() {
        return mNoteDAO.getAllNotes();
    }

    public void insertData(Note note) {
        mHandler.post(() -> mNoteDAO.insertNote(note));
    }


    public List<Note> getListNote() {
        return mNoteDAO.getAllNotesNormal();
    }

    public void updateData(Note note) {
         mNoteDAO.updateData(note);
    }

    public void updateDataWork(int id, Boolean isCheck) {
        Log.d("ptit", "updateDataWork: ");
        mHandler.post(() -> {
            mNoteDAO.updateWork(!isCheck, id);
        });
        Util.updateData(application);
    }
}
