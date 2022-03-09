package com.challenge.maddev.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.challenge.maddev.repositories.NotesRepository;
import com.challenge.maddev.repositories.NotesRepositoryImpl;

public class MadDevViewModelFactory implements ViewModelProvider.Factory {

    private final NotesRepository notesRepository;

    public MadDevViewModelFactory(Context context) {
        notesRepository = new NotesRepositoryImpl(context);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NotesManagerViewModel.class)){
            return (T) new NotesManagerViewModel(notesRepository);
        }

        if (modelClass.isAssignableFrom(NotesDetailViewModel.class)) {
            return (T) new NotesDetailViewModel(notesRepository);
        }

        throw new UnsupportedOperationException();
    }
}
