package com.example.mymood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;

public class Statistic extends AppCompatActivity {

    TextView quantityEntry;
    PieChart pieChartStatistic;
    String[] moodsName = {"Отлично", "Хорошо", "Не очень", "Плохо", "Ужасно"};
    int[] valuesMoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        Init();
        MoodDBHelper dbHelper = new MoodDBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //количество записей всего
        int n = (int) DatabaseUtils.queryNumEntries(database,"moods");
        quantityEntry.setText(String.valueOf(n));

        setupChartView();
    }

    private void setupChartView() {

    }

    private void Init() {
        pieChartStatistic = findViewById(R.id.pieChartStatistic);
        quantityEntry = findViewById(R.id.quantityEntry);
    }

    public void openEntries(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void openCreateEntry(View view) {
        Intent intent = new Intent(this,CreateEntryActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}