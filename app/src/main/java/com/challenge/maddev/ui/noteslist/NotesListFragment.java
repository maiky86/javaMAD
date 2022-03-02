package com.challenge.maddev.ui.noteslist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.challenge.maddev.R;
import com.challenge.maddev.data.local.NotesLocalDataSource;
import com.challenge.maddev.data.local.NotesLocalDataSourceCallback;
import com.challenge.maddev.data.local.NotesLocalDataSourceImpl;
import com.challenge.maddev.data.model.NoteObj;
import com.challenge.maddev.data.utils.NoteColor;
import com.challenge.maddev.data.utils.NotesDiffUtil;
import com.challenge.maddev.databinding.FragmentNotesListBinding;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesLocalDataSourceCallback, NotesListAdapterDelegate {

    private FragmentNotesListBinding binding;
    private NotesListAdapter adapter;
    private NotesLocalDataSource localDataSource;

    public NotesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentNotesListBinding.inflate(inflater,container, false);

        localDataSource = new NotesLocalDataSourceImpl(getContext());

        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new NotesListAdapter(new NotesDiffUtil(), this);
        binding.notesList.setAdapter(adapter);

        binding.addNoteFb.setOnClickListener(addButton -> {
            Navigation.findNavController(addButton).navigate(
                    R.id.action_notes_list_to_add_note
            );
        });

        requestNotesByFilter(true, null);
    }

    private void requestNotesByFilter(boolean allNotes, NoteColor filterColor) {
        if (allNotes)
            localDataSource.getAllNotes(this);
        else
            localDataSource.getNoteWithColor(filterColor, this);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_notes_filter, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        return super.onOptionsItemSelected(item);
        boolean isChecked = item.isChecked();
        item.setChecked(!isChecked);

        if (!isChecked) {
            NoteColor filterColor = NoteColor.WHITE;
            boolean filterNotes = false;

            switch (item.getItemId()) {
                case R.id.filter_all:
                    filterNotes = true;
                    break;
                case R.id.filter_blue:
                    filterColor = NoteColor.BLUE;
                    break;
                case R.id.filter_green:
                    filterColor = NoteColor.GREEN;
                    break;
                case R.id.filter_red:
                    filterColor = NoteColor.RED;
                    break;
                case R.id.filter_yellow:
                    filterColor = NoteColor.YELLOW;
                    break;
            }
            requestNotesByFilter(filterNotes, filterColor);
        }

        return true;
    }

    // NotesLocalDataSourceCallback
    @Override
    public void onNotesRetrieved(List<NoteObj> notesList) {
        adapter.submitList(
                notesList,
                () -> {
                    binding.notesList.smoothScrollToPosition(0);
                }
        );
    }

    @Override
    public void onNoteByIdRetrieved(NoteObj note) {
        // Do not apply here
    }

    // NoteListAdapterDelegate
    @Override
    public void onNoteSelected(NoteObj note) {
        NotesListFragmentDirections.ActionNotesListToNoteDetail action =
                NotesListFragmentDirections.actionNotesListToNoteDetail();
        action.setNoteId(note.getId());
        NavHostFragment.findNavController(this).navigate(action);
    }
}