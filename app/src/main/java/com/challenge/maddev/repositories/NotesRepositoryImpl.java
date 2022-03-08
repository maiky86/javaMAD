package com.challenge.maddev.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.challenge.maddev.data.local.NotesLocalDataSource;
import com.challenge.maddev.data.local.NotesLocalDataSourceImpl;
import com.challenge.maddev.data.model.NoteObj;
import com.challenge.maddev.data.utils.NoteColor;

import java.util.List;

public class NotesRepositoryImpl implements NotesRepository {

    private NotesLocalDataSource dataSource;

    public NotesRepositoryImpl(Context context) {
        dataSource = new NotesLocalDataSourceImpl(context);
    }

    @Override
    public void addNote(NoteObj note) {
        dataSource.addNote(note);
    }

    @Override
    public void removeNote(NoteObj note) {
        dataSource.removeNote(note);
    }

    @Override
    public void updateNote(NoteObj note) {
        dataSource.updateNote(note);
    }

    @Override
    public LiveData<List<NoteObj>> getAllNotes() {
        return dataSource.getAllNotes();
    }

    @Override
    public LiveData<List<NoteObj>> getNoteWithColor(NoteColor color) {
        return dataSource.getNoteWithColor(color);
    }

    @Override
    public LiveData<NoteObj> getNoteWithId(Integer id) {
        return dataSource.getNoteWithId(id);
    }
}
