package com.example.mymood;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MoodDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mood.db";
    private static final int DB_VERSION = 1;

    public MoodDBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(MoodContact.MoodEntry.CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(MoodContact.MoodEntry.DROP_COMMAND);
        onCreate(sqLiteDatabase);
    }
}
