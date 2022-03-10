package com.challenge.maddev.ui.note_detail;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.challenge.maddev.R;
import com.challenge.maddev.data.model.NoteObj;
import com.challenge.maddev.databinding.FragmentNoteDetailBinding;
import com.challenge.maddev.viewmodels.NotesDetailViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NoteDetailFragment extends Fragment {

    private FragmentNoteDetailBinding binding;
    private NotesDetailViewModel viewModel;

    private boolean mArgIsAdd = true;
    private int mArgNoteId = -1;

    public NoteDetailFragment(){}

    private void toggleVisibility(View view, boolean visible) {
        if (visible)
            view.setVisibility(View.VISIBLE);
        else
            view.setVisibility(View.GONE);
    }

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

        binding.setLifecycleOwner(getViewLifecycleOwner());

//        MadDevViewModelFactory factory = new MadDevViewModelFactory(getContext());
        viewModel = new ViewModelProvider(this)
                .get(NotesDetailViewModel.class);

        // Edit mode is off by default
        // so if mArgIsAdd we need to turn edit mode on
        if (mArgIsAdd)
            viewModel.onEditModeChange();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.note.observe(getViewLifecycleOwner(), this::onNoteByIdRetrieved);

        viewModel.getEditMode().observe(getViewLifecycleOwner(),this::setEditMode);

        viewModel.setNoteId(mArgNoteId);

        binding.setNotesDetailVM(viewModel);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (mArgIsAdd || viewModel.isEditModeOn())
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
        viewModel.onEditModeChange();
    }

    private void deleteNote() {
        viewModel.deleteNote();
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private void saveNote() {
        String title = binding.titleNoteDetail.getText().toString();
        String description = binding.descriptionNoteDetail.getText().toString();

        if (!title.trim().isEmpty() && !description.trim().isEmpty()) {
            viewModel.saveNote(title, description);

            if (mArgIsAdd) {
                Navigation.findNavController(binding.getRoot()).navigateUp();
            } else {
                viewModel.onEditModeChange();
            }
        } else {
            noTitleOrDescription();
        }
    }

    private void showEmptyFieldsDialog() {
        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setTitle(R.string.empty_fields_title)
                .setMessage(R.string.empty_fields_text)
                .setPositiveButton(R.string.empty_fields_button,
                        (dialogInterface, i) -> dialogInterface.dismiss()
                )
                .create();

        dialog.show();
    }

    private void noTitleOrDescription() {
        binding.titleNoteDetail.setText("");
        binding.descriptionNoteDetail.setText("");
        showEmptyFieldsDialog();
    }

    private void setEditMode(boolean enabled) {

        toggleVisibility(binding.colorContainer, enabled);
        if (!enabled)
            closeKeyboard();

        enableEditText(
                binding.titleNoteDetail,
                enabled
        );
        enableEditText(
                binding.descriptionNoteDetail,
                enabled
        );

        requireActivity().invalidateOptionsMenu();
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

    @BindingAdapter("dynamicBackground")
    public static void setBackground(View view, int resource) {
        Drawable background = ContextCompat.getDrawable(view.getContext(),resource);
        view.setBackground(background);
    }

    public void onNoteByIdRetrieved(NoteObj note) {
        if (note == null)
            return;

        binding.titleNoteDetail.setText(note.getTitle());
        binding.descriptionNoteDetail.setText(note.getDescription());
    }
}
