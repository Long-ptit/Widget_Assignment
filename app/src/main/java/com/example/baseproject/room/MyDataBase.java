package com.example.baseproject.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class MyDataBase extends RoomDatabase {

    private static MyDataBase instance;
    public abstract NoteDAO noteDAO();


    public MyDataBase() {
    }

    public static MyDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, MyDataBase.class, "MyDataBase").build();
        }
        return instance;
    }

}
