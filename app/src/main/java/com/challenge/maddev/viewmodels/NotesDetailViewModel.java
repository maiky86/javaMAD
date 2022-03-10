package com.challenge.maddev.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.challenge.maddev.data.model.NoteObj;
import com.challenge.maddev.data.utils.NoteColor;
import com.challenge.maddev.repositories.NotesRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NotesDetailViewModel extends ViewModel {


    private NotesRepository notesRepository;

    private boolean mIsEditing = false;
    private MutableLiveData<Boolean> editMode = new MutableLiveData<>(mIsEditing);

    private MutableLiveData<Integer> noteId = new MutableLiveData<>(-1);
    public LiveData<NoteObj> note = Transformations.switchMap(noteId, this::getNoteWithId);

    private MutableLiveData<NoteColor> selectedNoteColor = new MutableLiveData<>(NoteColor.WHITE);

    public MediatorLiveData<NoteColor> noteColor = new MediatorLiveData<>();

    @Inject
    public NotesDetailViewModel(NotesRepository repository) {
        notesRepository = repository;

        noteColor.addSource(note, this::getColorFromNote);
        noteColor.addSource(selectedNoteColor, color -> noteColor.setValue(color));
    }

    private void getColorFromNote(NoteObj note) {
        if(note == null)
            noteColor.setValue(NoteColor.WHITE);
        else
            noteColor.setValue(note.getColor());
    }

    public void saveNote(String title, String description) {
        if (note.getValue() == null) {
            NoteObj note = new NoteObj(title, description, noteColor.getValue());
            notesRepository.addNote(note);
        } else {
            updateNote(title, description);
        }
    }

    public void deleteNote() {
        NoteObj noteObj = note.getValue();
        if (noteObj!= null) {
            notesRepository.removeNote(noteObj);
        }
    }

    private void updateNote(String title, String description) {
        NoteObj currentNote = note.getValue();

        if (currentNote != null) {
            currentNote.setTitle(title);
            currentNote.setDescription(description);
            currentNote.setColor(noteColor.getValue());

            notesRepository.updateNote(currentNote);
        }
    }

    public LiveData<Boolean> getEditMode() { return editMode; }

    public boolean isEditModeOn() {return mIsEditing;}

    public void onEditModeChange() {
        mIsEditing = !mIsEditing;
        editMode.postValue(mIsEditing);
    }

    public LiveData<NoteColor> getNoteColor() {
        return noteColor;
    }

    public void setNoteColor(NoteColor color) {
        selectedNoteColor.postValue(color);
    }

    public void setNoteId(int id) {
        noteId.postValue(id);
    }

    private LiveData<NoteObj> getNoteWithId(int noteId) {
        if (noteId < 0) {
            return new MutableLiveData();
        } else {
            return notesRepository.getNoteWithId(noteId);
        }
    }
}
