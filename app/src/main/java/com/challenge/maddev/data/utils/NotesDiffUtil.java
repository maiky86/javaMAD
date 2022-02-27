package com.challenge.maddev.data.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.challenge.maddev.data.model.NoteObj;

public class NotesDiffUtil extends DiffUtil.ItemCallback<NoteObj> {

    @Override
    public boolean areItemsTheSame(@NonNull NoteObj oldItem, @NonNull NoteObj newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull NoteObj oldItem, @NonNull NoteObj newItem) {
        return oldItem.getTitle().equals(newItem.getTitle())
                && oldItem.getDescription().equals(newItem.getDescription());
    }
}
