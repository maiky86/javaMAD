package com.challenge.maddev.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.challenge.maddev.data.model.NoteObj;
import com.challenge.maddev.data.utils.NoteColor;

import java.util.List;

@Dao
public abstract class NotesDAO {

    @Insert
    public abstract void insertNote(NoteObj note);

    @Delete
    public abstract void deleteNote(NoteObj note);

    @Update
    public abstract void updateNote(NoteObj note);

    @Query("select * from notes_table")
    public abstract List<NoteObj> getNotes();

    @Query("select * from notes_table where color = :color")
    public abstract List<NoteObj> getNotesByColor(NoteColor color);

    @Query("select * from notes_table where id = :id")
    public abstract NoteObj getNoteById(Integer id);
}
