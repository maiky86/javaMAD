package com.challenge.maddev.data.local;

import com.challenge.maddev.data.model.NoteObj;
import com.challenge.maddev.data.utils.NoteColor;

public interface NotesLocalDataSource {

    void addNote(NoteObj note);

    void removeNote(NoteObj note);

    void updateNote(NoteObj note);

    void getAllNotes(NotesLocalDataSourceCallback callback);

    void getNoteWithColor(NoteColor color, NotesLocalDataSourceCallback callback);

    void getNoteWithId(Integer id, NotesLocalDataSourceCallback callback);
}
