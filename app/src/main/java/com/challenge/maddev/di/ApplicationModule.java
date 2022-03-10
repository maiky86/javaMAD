package com.challenge.maddev.di;

import android.content.Context;

import com.challenge.maddev.data.local.NotesLocalDataSource;
import com.challenge.maddev.data.local.NotesLocalDataSourceImpl;
import com.challenge.maddev.data.local.NotesRoomDatabase;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class ApplicationModule {

    @Provides
    public NotesLocalDataSource provideNotesLocalDataSource(
            NotesRoomDatabase database
    ) {
        return new NotesLocalDataSourceImpl(database.notesDAO());
    }

    @Provides
    public NotesRoomDatabase provideDataBase(
            @ApplicationContext Context context
            ) {
        return NotesRoomDatabase.getInstance(context);
    }
}
