package com.example.mymood;

import android.provider.BaseColumns;

public class MoodContact {
    public static final class MoodEntry implements BaseColumns{
        public static final String TABLE_NAME = "moods";
        public static final String COLUMN_NAME_MOOD = "name_mood";
        public static final String COLUMN_COMMENT = "comment";
        public static final String COLUMN_DATE_TIME_ENTRY = "date_time_entry";
        public static final String COLUMN_PRIORITY = "priority";

        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INTEGER = "INTEGER";

        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + "(" + _ID + " " + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME_MOOD + " " + TYPE_TEXT + ", " + COLUMN_COMMENT + " " + TYPE_TEXT +
                ", " + COLUMN_DATE_TIME_ENTRY + " " + TYPE_TEXT + ", " + COLUMN_PRIORITY +
                " " + TYPE_INTEGER + ")";
        public static final String DROP_COMMAND = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
