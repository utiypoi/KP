package com.example.mymood;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateEntryActivity extends AppCompatActivity {

    EditText dateTimePicker, editTextComment;
    Calendar calendar = Calendar.getInstance();
    TextView textViewSuper, textViewGood, textViewNeutral, textViewBad, textViewTerrible;
    int priority;
    String textViewVisibilityString;

    private MoodDBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);
        Init();
        setInitialDateTime();
        dateTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(dateTimePicker);
            }
        });
    }

    private void Init() {
        dbHelper = new MoodDBHelper(this);
        database = dbHelper.getWritableDatabase();
        dateTimePicker = findViewById(R.id.editTextDateTime);
        dateTimePicker.setInputType(InputType.TYPE_CLASS_DATETIME);
        textViewSuper = findViewById(R.id.textViewSuper);
        textViewGood = findViewById(R.id.textViewGood);
        textViewNeutral = findViewById(R.id.textViewNeutral);
        textViewBad = findViewById(R.id.textViewBad);
        textViewTerrible = findViewById(R.id.textViewTerrible);
        editTextComment = findViewById(R.id.editTextComment);
    }

    private void setInitialDateTime() {
        dateTimePicker.setText(DateUtils.formatDateTime(this,
                calendar.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME));
    }

    private void showDateTimeDialog(final EditText dateTimePicker) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy, HH:mm");
                        dateTimePicker.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(CreateEntryActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        };
        new DatePickerDialog(CreateEntryActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void stealthVisibility(View view) {
        textViewSuper.setVisibility(View.INVISIBLE);
        textViewGood.setVisibility(View.INVISIBLE);
        textViewNeutral.setVisibility(View.INVISIBLE);
        textViewBad.setVisibility(View.INVISIBLE);
        textViewTerrible.setVisibility(View.INVISIBLE);
        textViewVisibilityString = "";
        priority=0;
    }

    public void openSuperButton(View view) {
        stealthVisibility(view);
        textViewSuper.setVisibility(View.VISIBLE);
        textViewVisibilityString = "Отлично";
        priority=1;
    }

    public void openGoodButton(View view) {
        stealthVisibility(view);
        textViewGood.setVisibility(View.VISIBLE);
        textViewVisibilityString = "Хорошо";
        priority=2;
    }

    public void openNeutralButton(View view) {
        stealthVisibility(view);
        textViewNeutral.setVisibility(View.VISIBLE);
        textViewVisibilityString = "Не очень";
        priority=3;
    }

    public void openBadButton(View view) {
        stealthVisibility(view);
        textViewBad.setVisibility(View.VISIBLE);
        textViewVisibilityString = "Плохо";
        priority=4;
    }

    public void openTerribleButton(View view) {
        stealthVisibility(view);
        textViewTerrible.setVisibility(View.VISIBLE);
        textViewVisibilityString = "Ужасно";
        priority=5;
    }

    public void saveEntry(View view) {
        if(priority==0){
            Toast.makeText(this,"Выберите соответствующее настроение", Toast.LENGTH_SHORT).show();
        }else {
            String textComment = editTextComment.getText().toString().trim();
            String textDateTimeEntry = dateTimePicker.getText().toString();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MoodContact.MoodEntry.COLUMN_NAME_MOOD, textViewVisibilityString);
            contentValues.put(MoodContact.MoodEntry.COLUMN_COMMENT, textComment);
            contentValues.put(MoodContact.MoodEntry.COLUMN_DATE_TIME_ENTRY, textDateTimeEntry);
            contentValues.put(MoodContact.MoodEntry.COLUMN_PRIORITY, priority);
            database.insert(MoodContact.MoodEntry.TABLE_NAME, null, contentValues);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        }

    public void openEntries(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
