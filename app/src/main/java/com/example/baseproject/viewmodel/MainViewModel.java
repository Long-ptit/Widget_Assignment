package com.example.baseproject.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.baseproject.room.Note;
import com.example.baseproject.room.NoteRepository;
import com.example.baseproject.util.Util;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Note>> mListNoteLiveData;
    private NoteRepository mNoteRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mNoteRepository =new NoteRepository(application);
        mListNoteLiveData = mNoteRepository.getAllNotes();
    }

    public LiveData<List<Note>> getListNoteLiveData() {
        return mListNoteLiveData;
    }

    public void addData(Note note) {
        mNoteRepository.insertData(note);
        Util.updateData(getApplication());
    }

    public void updateWork(Note note) {
        mNoteRepository.updateDataWork(note.getId(), note.isDone());
        Util.updateData(getApplication());
    }


}
