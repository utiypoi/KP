package com.example.mymood;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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

    Button buttonCircle;
    EditText dateTimePicker;
    Calendar calendar = Calendar.getInstance();
    TextView textViewSuper, textViewGood, textViewNeutral, textViewBad, textViewTerrible;
    int textViewVisibility;
    String textViewVisibilityString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);
        buttonCircle = findViewById(R.id.buttonForth);
        dateTimePicker = findViewById(R.id.editTextDateTime);
        setInitialDateTime();
        dateTimePicker.setInputType(InputType.TYPE_CLASS_DATETIME);
        dateTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(dateTimePicker);
            }
        });
        textViewSuper = findViewById(R.id.textViewSuper);
        textViewGood = findViewById(R.id.textViewGood);
        textViewNeutral = findViewById(R.id.textViewNeutral);
        textViewBad = findViewById(R.id.textViewBad);
        textViewTerrible = findViewById(R.id.textViewTerrible);
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
        textViewVisibility = 0;
    }

    public void openSuperButton(View view) {
        stealthVisibility(view);
        textViewSuper.setVisibility(View.VISIBLE);
        textViewVisibility = 1;
    }

    public void openGoodButton(View view) {
        stealthVisibility(view);
        textViewGood.setVisibility(View.VISIBLE);
        textViewVisibility = 2;
    }

    public void openNeutralButton(View view) {
        stealthVisibility(view);
        textViewNeutral.setVisibility(View.VISIBLE);
        textViewVisibility = 3;
    }

    public void openBadButton(View view) {
        stealthVisibility(view);
        textViewBad.setVisibility(View.VISIBLE);
        textViewVisibility = 4;
    }

    public void openTerribleButton(View view) {
        stealthVisibility(view);
        textViewTerrible.setVisibility(View.VISIBLE);
        textViewVisibility = 5;
    }


    public void openContinuationEntry(View view) {
        if (textViewVisibility == 0) {
            Toast.makeText(this, R.string.toast_mood_selection_warning, Toast.LENGTH_SHORT).show();
        } else {
            switch (textViewVisibility) {
                case 1:
                    textViewVisibilityString = getString(R.string.name_smiling_super);
                    break;
                case 2:
                    textViewVisibilityString = getString(R.string.name_smiling_good);
                    break;
                case 3:
                    textViewVisibilityString = getString(R.string.name_smiling_neutral);
                    break;
                case 4:
                    textViewVisibilityString = getString(R.string.name_smiling_bad);
                    break;
                case 5:
                    textViewVisibilityString = getString(R.string.name_smiling_terrible);
                    break;
            }
            String dateTimeEntry = dateTimePicker.getText().toString();
            Intent intent = new Intent(this, ContinuationCreateEntryActivity.class);
            //название настроения - continuation activity
            intent.putExtra("NameMood", textViewVisibilityString);
            //строковое значение даты и времени
            intent.putExtra("dateTimeEntry", dateTimeEntry);
            //видимое настроение - continuation activity
            intent.putExtra("IdMood", textViewVisibility);
            startActivity(intent);
        }
    }
}