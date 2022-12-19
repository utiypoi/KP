package com.example.mymood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Statistic extends AppCompatActivity {

    private MoodDBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        MoodDBHelper dbHelper = new MoodDBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        TextView quantityEntry = findViewById(R.id.quantityEntry);
        //количество записей всего
        int n = (int) DatabaseUtils.queryNumEntries(database,"moods");
        //long n = DatabaseUtils.queryNumEntries(database,"moods",null);
        quantityEntry.setText(String.valueOf(n));
    }

    public void openEntries(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}