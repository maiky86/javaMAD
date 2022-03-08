package com.challenge.maddev.data.local;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.challenge.maddev.data.model.NoteObj;
import com.challenge.maddev.data.utils.NoteColor;

import java.util.List;

public class NotesLocalDataSourceImpl implements NotesLocalDataSource {

    private NotesDAO notesDAO;

    public NotesLocalDataSourceImpl(Context context) {
        this.notesDAO = NotesRoomDatabase.getInstance(context).notesDAO();
    }

    @Override
    public void addNote(NoteObj note) {
        new Thread(() ->notesDAO.insertNote(note)).start();
    }

    @Override
    public void removeNote(NoteObj note) {
        new Thread(() ->notesDAO.deleteNote(note)).start();
    }

    @Override
    public void updateNote(NoteObj note) {
        new Thread(() ->notesDAO.updateNote(note)).start();
    }

    @Override
    public LiveData<List<NoteObj>> getAllNotes() {
        return notesDAO.getNotes();
    }

    @Override
    public LiveData<List<NoteObj>> getNoteWithColor(NoteColor color) {
        return notesDAO.getNotesByColor(color);
    }

    @Override
    public LiveData<NoteObj> getNoteWithId(Integer id) {
        return notesDAO.getNoteById(id);
    }
}
