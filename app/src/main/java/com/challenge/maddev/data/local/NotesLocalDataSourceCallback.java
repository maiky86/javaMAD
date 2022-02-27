package com.challenge.maddev.data.local;

import com.challenge.maddev.data.model.NoteObj;

import java.util.List;

public interface NotesLocalDataSourceCallback {

    void onNotesRetrieved(List<NoteObj> notesList);

    void onNoteByIdRetrieved(NoteObj note);
}
