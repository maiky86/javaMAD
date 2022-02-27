package com.challenge.maddev.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.challenge.maddev.data.model.NoteObj;

@Database(entities = {NoteObj.class}, version = 1)
public abstract class NotesRoomDatabase extends RoomDatabase {

    private static NotesRoomDatabase database;

    public abstract NotesDAO notesDAO();
    public static synchronized NotesRoomDatabase getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(
                    context,
                    NotesRoomDatabase.class,
                    "notes_db"
            ).build();
        }

        return database;
    }
}
