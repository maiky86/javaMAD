package com.challenge.maddev.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.challenge.maddev.data.utils.NoteColor;

@Entity(tableName = "notes_table")
public class NoteObj {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String title;
    private String description;
    private NoteColor color = NoteColor.WHITE;

    public NoteObj(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NoteColor getColor() {
        return color;
    }

    public void setColor(NoteColor color) {
        this.color = color;
    }
}
