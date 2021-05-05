package com.HrishikeshPujari.Note4you;

import android.provider.BaseColumns;

public class NoteContract {
    private NoteContract(){};
    public static final class NoteEntry implements BaseColumns{
        public static final String TABLE_NAME="memories";
        public static final String COLUMN_TITLE="title";
        public static final String COLUMN_DESCRIPTION="description";
        public static final String COLUMN_IMAGE="image";

    }

}
