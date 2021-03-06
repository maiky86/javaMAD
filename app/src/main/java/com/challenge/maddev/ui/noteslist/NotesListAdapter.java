package com.challenge.maddev.ui.noteslist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.challenge.maddev.data.model.NoteObj;
import com.challenge.maddev.databinding.ItemNoteListBinding;

public class NotesListAdapter extends ListAdapter<NoteObj, NoteViewHolder> {

    private NotesListAdapterDelegate delegate;

    protected NotesListAdapter(
            @NonNull DiffUtil.ItemCallback<NoteObj> diffCallback,
            NotesListAdapterDelegate adapterDelegate
            ) {
        super(diffCallback);
        this.delegate = adapterDelegate;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(
                ItemNoteListBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bindNote(getItem(position));
        holder.itemView.setOnClickListener(view ->
                delegate.onNoteSelected(
                        getItem(position)
                )
        );
    }
}

class NoteViewHolder extends RecyclerView.ViewHolder {

    private final ItemNoteListBinding binding;

    public NoteViewHolder(@NonNull ItemNoteListBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindNote(NoteObj note) {
        binding.titleNoteItem.setText(note.getTitle());
        binding.descriptionNoteItem.setText(note.getDescription());
        binding.noteListItemCard.setCardBackgroundColor(
                ContextCompat.getColor(
                        binding.getRoot().getContext(),
                        note.getColor().getColorId()
                )
        );
    }
}
