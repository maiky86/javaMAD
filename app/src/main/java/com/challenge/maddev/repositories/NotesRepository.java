package com.challenge.maddev.repositories;

import androidx.lifecycle.LiveData;

import com.challenge.maddev.data.model.NoteObj;
import com.challenge.maddev.data.utils.NoteColor;

import java.util.List;

public interface NotesRepository {

    void addNote(NoteObj note);

    void removeNote(NoteObj note);

    void updateNote(NoteObj note);

    LiveData<List<NoteObj>> getAllNotes();

    LiveData<List<NoteObj>> getNoteWithColor(NoteColor color);

    LiveData<NoteObj> getNoteWithId(Integer id);
}
