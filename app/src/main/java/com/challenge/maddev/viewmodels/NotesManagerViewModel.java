package com.challenge.maddev.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.challenge.maddev.data.model.NoteObj;
import com.challenge.maddev.data.utils.NoteColor;
import com.challenge.maddev.repositories.NotesRepository;

import java.util.List;

public class NotesManagerViewModel extends ViewModel {

    private NotesRepository notesRepository;

    public LiveData<List<NoteObj>> listOfNotes;

    private MutableLiveData<NoteColor> filterColor = new MutableLiveData<NoteColor>(null);

    public NotesManagerViewModel(NotesRepository repository) {
        notesRepository = repository;
        initLiveData();
    }

    public NoteColor getFilterNoteColorSelected() {
        return filterColor.getValue();
    }

    private void initLiveData() {
        listOfNotes = Transformations.switchMap(filterColor,this::getFilteredNotes);
    }

    private LiveData<List<NoteObj>> getFilteredNotes(NoteColor color) {
        if (color == null)
            return notesRepository.getAllNotes();
        else
            return notesRepository.getNoteWithColor(color);
    }

    public void filterNotesByColor(NoteColor color) {
        filterColor.postValue(color);
    }

    public void doNotFilterNotes() {
        filterColor.postValue(null);
    }

}
