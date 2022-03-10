package com.challenge.maddev.di;

import com.challenge.maddev.data.local.NotesLocalDataSource;
import com.challenge.maddev.repositories.NotesRepository;
import com.challenge.maddev.repositories.NotesRepositoryImpl;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

@Module
@InstallIn(ViewModelComponent.class)
public class RepositoriesModule {

    @Provides
    public NotesRepository provideNotesRepository(
            NotesLocalDataSource dataSource
    ) {
        return new NotesRepositoryImpl(dataSource);
    }
}
