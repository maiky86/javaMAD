package com.challenge.maddev.data.utils;

import com.challenge.maddev.R;

public enum NoteColor {
    WHITE(R.color.white),
    RED(R.color.red),
    GREEN(R.color.green),
    YELLOW(R.color.yellow),
    BLUE(R.color.blue);

    private int colorId;

    private NoteColor(int value) {
        this.colorId = value;
    }

    public int getColorId() {
        return colorId;
    }
}
