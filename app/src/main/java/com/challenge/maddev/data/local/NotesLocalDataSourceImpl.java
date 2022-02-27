package com.challenge.maddev.data.local;

import android.content.Context;

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
    public void getAllNotes(NotesLocalDataSourceCallback callback) {
        new Thread(() -> {
            List<NoteObj> notesList = notesDAO.getNotes();
            callback.onNotesRetrieved(notesList);
        }).start();
    }

    @Override
    public void getNoteWithColor(NoteColor color, NotesLocalDataSourceCallback callback) {
        new Thread(() -> {
            List<NoteObj> notesList = notesDAO.getNotesByColor(color);
            callback.onNotesRetrieved(notesList);
        }).start();
    }

    @Override
    public void getNoteWithId(Integer id, NotesLocalDataSourceCallback callback) {
        new Thread(() -> {
            NoteObj note = notesDAO.getNoteById(id);
            callback.onNoteByIdRetrieved(note);
        }).start();
    }
}
