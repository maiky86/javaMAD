package com.challenge.maddev.ui.noteslist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
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
import com.challenge.maddev.data.local.NotesLocalDataSourceImpl;
import com.challenge.maddev.data.model.NoteObj;
import com.challenge.maddev.data.utils.NoteColor;
import com.challenge.maddev.data.utils.NotesDiffUtil;
import com.challenge.maddev.databinding.FragmentNotesListBinding;
import com.challenge.maddev.repositories.NotesRepository;
import com.challenge.maddev.repositories.NotesRepositoryImpl;
import com.challenge.maddev.viewmodels.MadDevViewModelFactory;
import com.challenge.maddev.viewmodels.NotesManagerViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotesListFragment extends Fragment implements NotesListAdapterDelegate {

    private FragmentNotesListBinding binding;
    private NotesListAdapter adapter;
    private NotesManagerViewModel viewModel;

    public NotesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentNotesListBinding.inflate(inflater,container, false);

//        MadDevViewModelFactory factory = new MadDevViewModelFactory(requireContext());
        viewModel = new ViewModelProvider(requireActivity())
                .get(NotesManagerViewModel.class);

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

        viewModel.listOfNotes.observe(getViewLifecycleOwner(), this::onNotesRetrieved);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_notes_filter, menu);

        NoteColor filter = viewModel.getFilterNoteColorSelected();
        if (filter != null) {
            menu.findItem(getFilterIdFromNoteColor(filter))
                    .setChecked(true);
        }

    }

    private int getFilterIdFromNoteColor(NoteColor color) {
        if (color == NoteColor.BLUE)
            return R.id.filter_blue;
        else if (color == NoteColor.RED)
            return R.id.filter_red;
        else if (color == NoteColor.GREEN)
            return R.id.filter_green;
        else if (color == NoteColor.YELLOW)
            return R.id.filter_yellow;
        else // (color == NoteColor.WHITE)
            return R.id.filter_white;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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
            setFilter(filterNotes, filterColor);
        }

        return true;
    }

    private void setFilter(boolean allNotes, NoteColor filterColor) {
        if (allNotes) {
            viewModel.doNotFilterNotes();
        } else {
            viewModel.filterNotesByColor(filterColor);
        }

    }

    public void onNotesRetrieved(List<NoteObj> notesList) {
        if (notesList == null)
            return;

        adapter.submitList(
                notesList,
                () -> binding.notesList.smoothScrollToPosition(0)
        );
    }

    // NoteListAdapterDelegate
    @Override
    public void onNoteSelected(NoteObj note) {
        if (note == null)
            return;

        NotesListFragmentDirections.ActionNotesListToNoteDetail action =
                NotesListFragmentDirections.actionNotesListToNoteDetail();
        action.setNoteId(note.getId());
        NavHostFragment.findNavController(this).navigate((NavDirections) action);
    }
}