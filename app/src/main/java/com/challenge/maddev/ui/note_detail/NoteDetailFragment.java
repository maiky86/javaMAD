package com.challenge.maddev.ui.note_detail;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.challenge.maddev.R;
import com.challenge.maddev.data.local.NotesLocalDataSource;
import com.challenge.maddev.data.local.NotesLocalDataSourceImpl;
import com.challenge.maddev.data.model.NoteObj;
import com.challenge.maddev.data.utils.NoteColor;
import com.challenge.maddev.databinding.FragmentNoteDetailBinding;

public class NoteDetailFragment extends Fragment {

    private FragmentNoteDetailBinding binding;
    private NotesLocalDataSource localDataSource;
    private NoteColor selectedColor = NoteColor.WHITE;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.colorGreen.setOnClickListener(view1 -> colorSelected(NoteColor.GREEN));
        binding.colorRed.setOnClickListener(view1 -> colorSelected(NoteColor.RED));
        binding.colorBlue.setOnClickListener(view1 -> colorSelected(NoteColor.BLUE));
        binding.colorYellow.setOnClickListener(view1 -> colorSelected(NoteColor.YELLOW));
        binding.colorWhite.setOnClickListener(view1 -> colorSelected(NoteColor.WHITE));
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

        NoteObj note = new NoteObj(title, description, selectedColor);

        localDataSource.addNote(note);

        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private void colorSelected(@NonNull NoteColor colorNote) {
        Drawable border = ContextCompat.getDrawable(requireContext(),R.drawable.colored_circle_border);
        clearSelectedColor(selectedColor);
        selectedColor = colorNote;

        switch (colorNote) {
            case RED:
                binding.colorRed.setBackground(border);
                break;
            case BLUE:
                binding.colorBlue.setBackground(border);
                break;
            case GREEN:
                binding.colorGreen.setBackground(border);
                break;
            case YELLOW:
                binding.colorYellow.setBackground(border);
                break;
            default:
                binding.colorWhite.setBackground(border);
                break;
        }

        int color = ContextCompat.getColor(requireContext(), colorNote.getColorId());
        binding.noteDetailContainer.setBackgroundColor(color);
    }

    private void clearSelectedColor(NoteColor colorNote){
        switch (colorNote) {
            case RED:
                binding.colorRed.setBackgroundColor(Color.TRANSPARENT);
                break;
            case BLUE:
                binding.colorBlue.setBackgroundColor(Color.TRANSPARENT);
                break;
            case GREEN:
                binding.colorGreen.setBackgroundColor(Color.TRANSPARENT);
                break;
            case YELLOW:
                binding.colorYellow.setBackgroundColor(Color.TRANSPARENT);
                break;
            default:
                binding.colorWhite.setBackgroundColor(Color.TRANSPARENT);
                break;
        }
    }
}
