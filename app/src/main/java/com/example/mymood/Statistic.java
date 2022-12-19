package com.example.mymood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class Statistic extends AppCompatActivity {

    TextView quantityEntry;
    private LineChart chart;

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
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1f, 5f));
        entries.add(new Entry(2f, 2f));
        entries.add(new Entry(3f, 1f));
        entries.add(new Entry(4f, -3f));
        entries.add(new Entry(5f, 4f));
        entries.add(new Entry(6f, 1f));

// На основании массива точек создадим первую линию с названием
        LineDataSet dataset = new LineDataSet(entries, "График первый");

// Создадим переменную данных для графика
        LineData data = new LineData(dataset);
// Передадим данные для графика в сам график
        chart.setData(data);

// Не забудем отправить команду на перерисовку кадра, иначе график не отобразится
        chart.invalidate();
    }

    private void Init() {
        quantityEntry = findViewById(R.id.quantityEntry);
        chart = findViewById(R.id.lineChartStatistic);
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