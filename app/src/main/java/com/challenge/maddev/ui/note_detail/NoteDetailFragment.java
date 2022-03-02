package com.challenge.maddev.ui.note_detail;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.challenge.maddev.R;
import com.challenge.maddev.data.local.NotesLocalDataSource;
import com.challenge.maddev.data.local.NotesLocalDataSourceCallback;
import com.challenge.maddev.data.local.NotesLocalDataSourceImpl;
import com.challenge.maddev.data.model.NoteObj;
import com.challenge.maddev.data.utils.NoteColor;
import com.challenge.maddev.databinding.FragmentNoteDetailBinding;

import java.util.List;

public class NoteDetailFragment extends Fragment implements NotesLocalDataSourceCallback {

    private FragmentNoteDetailBinding binding;
    private NotesLocalDataSource localDataSource;
    private NoteColor selectedColor = NoteColor.WHITE;

    private boolean mArgIsAdd = true;
    private int mArgNoteId = -1;

    private boolean mIsEditing = false;

    private NoteObj mCurrentNote = null;

    public NoteDetailFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        mArgIsAdd = NoteDetailFragmentArgs.fromBundle(getArguments()).getIsAdd();
        mArgNoteId = NoteDetailFragmentArgs.fromBundle(getArguments()).getNoteId();
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

        if (mArgIsAdd || mIsEditing) {
            binding.colorContainer.setVisibility(View.VISIBLE);
        } else {
            binding.colorContainer.setVisibility(View.GONE);
            setEditMode(false);
        }

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

        if (mArgNoteId != -1) {
            localDataSource.getNoteWithId(mArgNoteId, this);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (mArgIsAdd || mIsEditing)
            inflater.inflate(R.menu.save_menu, menu);
        else
            inflater.inflate(R.menu.menu_edit_delete, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            case R.id.edit_note:
                editNote();
                return true;
            case R.id.delete_note:
                deleteNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void editNote() {
        setEditMode(true);
        requireActivity().invalidateOptionsMenu();
    }

    private void deleteNote() {
        if (mCurrentNote != null) {
            localDataSource.removeNote(mCurrentNote);
        }

        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private void saveNote() {
        String title = binding.titleNoteDetail.getText().toString();
        String description = binding.descriptionNoteDetail.getText().toString();

        if (mCurrentNote == null) {
            NoteObj note = new NoteObj(title, description, selectedColor);
            localDataSource.addNote(note);
            Navigation.findNavController(binding.getRoot()).navigateUp();
        } else {
            mCurrentNote.setTitle(title);
            mCurrentNote.setDescription(description);
            mCurrentNote.setColor(selectedColor);
            localDataSource.updateNote(mCurrentNote);
            setEditMode(false);
            requireActivity().invalidateOptionsMenu();
        }

    }

    private void setEditMode(boolean enabled) {
        mIsEditing = enabled;

        if (enabled) {
            binding.colorContainer.setVisibility(View.VISIBLE);
            setSelectedColorCircle(selectedColor);
        } else {
            binding.colorContainer.setVisibility(View.GONE);
            closeKeyboard();
        }

        enableEditText(
                binding.titleNoteDetail,
                enabled
        );
        enableEditText(
                binding.descriptionNoteDetail,
                enabled
        );
    }

    private void closeKeyboard() {
        EditText activeEditText = binding.titleNoteDetail;

        if (binding.descriptionNoteDetail.hasFocus())
            activeEditText = binding.descriptionNoteDetail;

        InputMethodManager imm =
                (InputMethodManager) requireActivity().getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activeEditText.getWindowToken(), 0);
    }

    private void enableEditText(EditText editText, boolean isEnabled) {
        editText.setFocusableInTouchMode(isEnabled);
        editText.setFocusable(isEnabled);
        editText.setCursorVisible(isEnabled);
    }

    private void colorSelected(@NonNull NoteColor colorNote) {
        setSelectedColorCircle(colorNote);
        setBackgroundColor(colorNote);
    }

    private void setSelectedColorCircle(NoteColor colorNote) {
        Drawable border = ContextCompat.getDrawable(requireContext(),R.drawable.colored_circle_border);
        clearSelectedColor(selectedColor);

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

    private void setBackgroundColor(NoteColor colorNote) {
        int color = ContextCompat.getColor(requireContext(), colorNote.getColorId());
        binding.noteDetailContainer.setBackgroundColor(color);
        selectedColor = colorNote;
    }

    // NotesLocalDataSourceCallback methods
    @Override
    public void onNotesRetrieved(List<NoteObj> notesList) {
        // Does not apply here
    }

    @Override
    public void onNoteByIdRetrieved(NoteObj note) {
        binding.titleNoteDetail.setText(note.getTitle());
        binding.descriptionNoteDetail.setText(note.getDescription());
//        setBackgroundColor(note.getColor());
        mCurrentNote = note;

        colorSelected(note.getColor());
    }
}
