package com.challenge.maddev.ui.note_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.challenge.maddev.R;
import com.challenge.maddev.data.local.NotesLocalDataSource;
import com.challenge.maddev.data.local.NotesLocalDataSourceImpl;
import com.challenge.maddev.data.model.NoteObj;
import com.challenge.maddev.databinding.FragmentNoteDetailBinding;

public class NoteDetailFragment extends Fragment {

    private FragmentNoteDetailBinding binding;
    private NotesLocalDataSource localDataSource;

    public NoteDetailFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        binding = FragmentNoteDetailBinding.inflate(
                inflater,
                container,
                false
        );

        localDataSource = new NotesLocalDataSourceImpl(getContext());

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.save_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = binding.titleNoteDetail.getText().toString();
        String description = binding.descriptionNoteDetail.getText().toString();

        NoteObj note = new NoteObj(title, description);

        localDataSource.addNote(note);
    }
}
