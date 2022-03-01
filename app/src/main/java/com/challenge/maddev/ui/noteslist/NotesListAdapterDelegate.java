package com.challenge.maddev.ui.noteslist;

import com.challenge.maddev.data.model.NoteObj;

public interface NotesListAdapterDelegate {

    void onNoteSelected(NoteObj note);
}
