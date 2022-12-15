package com.example.mymood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ContinuationCreateEntryActivity extends AppCompatActivity {

    Button saveEntryButton;
    TextView textViewSmiling;
    ImageView imageViewMood;
    String textSmiling;
    EditText editTextComment;
    int priority;
    String textNameMood;

    private MoodDBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continuation_create_entry);
        dbHelper = new MoodDBHelper(this);
        database = dbHelper.getWritableDatabase();
        ContinuationCreateEntryActivity context = this;
        saveEntryButton = findViewById(R.id.buttonSave);
        imageViewMood = findViewById(R.id.imageViewSelectedSmiling);
        textViewSmiling = findViewById(R.id.textViewSmiling);
        editTextComment = findViewById(R.id.editTextComment);
        Bundle bundle = getIntent().getExtras();
        textViewSmiling.setText(bundle.get("NameMood").toString());
        settingResources();
    }

    public void settingResources() {
        textSmiling = textViewSmiling.getText().toString();
        switch (textSmiling) {
            case "отлично":
                textViewSmiling.setTextColor(getResources().getColor(R.color.text_super));
                imageViewMood.setImageResource(R.drawable.smiling_super);
                priority = 1;
                break;
            case "хорошо":
                textViewSmiling.setTextColor(getResources().getColor(R.color.text_good));
                imageViewMood.setImageResource(R.drawable.smiling_good);
                priority = 2;
                break;
            case "не очень":
                textViewSmiling.setTextColor(getResources().getColor(R.color.text_neutral));
                imageViewMood.setImageResource(R.drawable.smiling_neutral);
                priority = 3;
                break;
            case "плохо":
                textViewSmiling.setTextColor(getResources().getColor(R.color.text_bad));
                imageViewMood.setImageResource(R.drawable.smiling_bad);
                priority = 4;
                break;
            case "ужасно":
                textViewSmiling.setTextColor(getResources().getColor(R.color.text_terrible));
                imageViewMood.setImageResource(R.drawable.smiling_terrible);
                priority = 5;
                break;
        }
    }

    public void saveEntry(View view) {
        textNameMood = textSmiling.substring(0,1).toUpperCase()+textSmiling.substring(1).trim();
        Bundle bundle = getIntent().getExtras();
        String textComment = editTextComment.getText().toString().trim();
        String textDateTimeEntry = bundle.get("dateTimeEntry").toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoodContact.MoodEntry.COLUMN_NAME_MOOD, textNameMood);
        contentValues.put(MoodContact.MoodEntry.COLUMN_COMMENT, textComment);
        contentValues.put(MoodContact.MoodEntry.COLUMN_DATE_TIME_ENTRY, textDateTimeEntry);
        contentValues.put(MoodContact.MoodEntry.COLUMN_PRIORITY, priority);
        database.insert(MoodContact.MoodEntry.TABLE_NAME, null, contentValues);
        Intent intent = new Intent(this, MainActivity.class);
//      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}