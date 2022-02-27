package com.challenge.maddev.data.utils;

import java.util.HashMap;
import java.util.Map;

public enum NoteColor {
    WHITE(0),
    RED(1),
    GREEN(2),
    YELLOW(3),
    BLUE(4);

    private int value;
    private static Map map = new HashMap<>();

    private NoteColor(int value) {
        this.value = value;
    }

    static {
        for (NoteColor pageType : NoteColor.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static NoteColor valueOf(int pageType) {
        return (NoteColor) map.get(pageType);
    }

    public int getValue() {
        return value;
    }
}
