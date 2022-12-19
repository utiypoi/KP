package com.example.mymood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMood;
    private final ArrayList<Mood> moods = new ArrayList<>();
    private MoodAdapter adapter;
    private MoodDBHelper moodsDBHelper;
    private SQLiteDatabase database;
    EditText searchEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewMood = findViewById(R.id.recyclerViewMoods);
        moodsDBHelper = new MoodDBHelper(this);
        database = moodsDBHelper.getWritableDatabase();
        getData();
        adapter = new MoodAdapter(moods);
        recyclerViewMood.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMood.setAdapter(adapter);
        adapter.setOnMoodClickListener(new MoodAdapter.OnMoodClickListener() {
            @Override
            public void onMoodClick(int position) {
                Toast.makeText(MainActivity.this, "Запись", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                remove(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewMood);

        searchEntry = findViewById(R.id.searchEntry);
        searchEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text){
        ArrayList<Mood> filteredList = new ArrayList<>();
        for(Mood mood : moods){
            if(mood.getComment().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(mood);
            }
            adapter.filterList(filteredList);
        }
    }


    private void remove(int position) {
        int id = moods.get(position).getId();
        String where = MoodContact.MoodEntry._ID + " = ?";
        String[] whereArgs = new String[]{Integer.toString(id)};
        database.delete(MoodContact.MoodEntry.TABLE_NAME, where, whereArgs);
        getData();
        adapter.notifyDataSetChanged();
    }

    public void openCreateEntry(View view) {
        Intent intent = new Intent(this, CreateEntryActivity.class);
        startActivity(intent);
    }
    private void getData(){
        moods.clear();
        Cursor cursor = database.query(MoodContact.MoodEntry.TABLE_NAME, null,null,null,null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MoodContact.MoodEntry._ID));
            String name_mood = cursor.getString(cursor.getColumnIndexOrThrow(MoodContact.MoodEntry.COLUMN_NAME_MOOD));
            String comment = cursor.getString(cursor.getColumnIndexOrThrow(MoodContact.MoodEntry.COLUMN_COMMENT));
            String date_time_entry = cursor.getString(cursor.getColumnIndexOrThrow(MoodContact.MoodEntry.COLUMN_DATE_TIME_ENTRY));
            int priority = cursor.getInt(cursor.getColumnIndexOrThrow(MoodContact.MoodEntry.COLUMN_PRIORITY));
            Mood mood = new Mood(id, name_mood, comment, date_time_entry, priority);
            moods.add(mood);
        }
        cursor.close();
    }

    public void openStatistic(View view) {
        Intent intent = new Intent(this,Statistic.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}