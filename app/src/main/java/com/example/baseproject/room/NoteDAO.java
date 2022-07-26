package com.example.baseproject.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDAO {

    @Insert
    void insertNote(Note note);

    @Query(value = "SELECT * from tbl_note")
    LiveData<List<Note>> getAllNotes();

    @Query(value = "SELECT * from tbl_note")
    List<Note> getAllNotesNormal();

    @Update()
    void updateData(Note note);

    @Query(value = "UPDATE tbl_note SET is_done = :isDone Where id = :Id")
    void updateWork(Boolean isDone, int Id);
}
