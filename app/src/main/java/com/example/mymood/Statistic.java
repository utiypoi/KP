package com.example.mymood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Statistic extends AppCompatActivity {

    TextView quantityEntry, textMoodSuper, textMoodGood, textMoodNeutral, textMoodBad, textMoodTerrible;
    private PieChart chart;
    int e, s, g, n, b, t;
    int colorArray[];
    PieDataSet pieDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        Init();
        queryDatabaseInfo();
        if (s == 0 & g == 0 & n == 0 & b == 0 & t == 0) {
            chart.setVisibility(View.GONE);
        } else {
            PieDataSet pieDataSet = new PieDataSet(dataPie(), "");
            pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);
//            pieDataSet.setColors(getResources().getColor(R.color.text_super),
//                    getResources().getColor(R.color.text_good),
//                    getResources().getColor(R.color.text_neutral),
//                    getResources().getColor(R.color.text_bad),
//                    getResources().getColor(R.color.text_terrible));
            chart.setHoleRadius(0f);
            chart.setTransparentCircleRadius(0);
            chart.setTransparentCircleColor(getColor(R.color.backgroung_layout));
            chart.setUsePercentValues(true);
            chart.setRotationEnabled(false);
            chart.setDrawSliceText(false);
            chart.getDescription().setEnabled(false);

            Legend l = chart.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setForm(Legend.LegendForm.CIRCLE);

            PieData pieData = new PieData(pieDataSet);
            chart.setData(pieData);
            pieData.setValueFormatter(new DecimalRemover(new DecimalFormat("###,###,###")));
            pieData.setValueTextColor(Color.WHITE);
            pieData.setValueTextSize(11f);
            chart.invalidate();
        }
    }

    private ArrayList<PieEntry> dataPie() {
        ArrayList<PieEntry> dataPie = new ArrayList<>();
        if (s != 0) {
            dataPie.add(new PieEntry(s, "Отлично"));
        }
        if (g != 0) {
            dataPie.add(new PieEntry(g, "Хорошо"));
        }
        if (n != 0) {
            dataPie.add(new PieEntry(n, "Не очень"));
        }
        if (b != 0) {
            dataPie.add(new PieEntry(b, "Плохо"));
        }
        if (t != 0) {
            dataPie.add(new PieEntry(t, "Ужасно"));
        }
        return dataPie;
    }

    private void queryDatabaseInfo() {
        MoodDBHelper dbHelper = new MoodDBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        e = (int) DatabaseUtils.queryNumEntries(database, "moods");
        quantityEntry.setText(String.valueOf(e));

        String sQuery = "select * from " + MoodContact.MoodEntry.TABLE_NAME + " where " + MoodContact.MoodEntry.COLUMN_PRIORITY + " =1";
        Cursor sCursor = database.rawQuery(sQuery, null);
        s = sCursor.getCount();
        textMoodSuper.setText(String.valueOf(s));
        sCursor.close();

        String gQuery = "select * from " + MoodContact.MoodEntry.TABLE_NAME + " where " + MoodContact.MoodEntry.COLUMN_PRIORITY + " =2";
        Cursor gCursor = database.rawQuery(gQuery, null);
        g = gCursor.getCount();
        textMoodGood.setText(String.valueOf(g));
        gCursor.close();

        String nQuery = "select * from " + MoodContact.MoodEntry.TABLE_NAME + " where " + MoodContact.MoodEntry.COLUMN_PRIORITY + " =3";
        Cursor nCursor = database.rawQuery(nQuery, null);
        n = nCursor.getCount();
        textMoodNeutral.setText(String.valueOf(n));
        gCursor.close();

        String bQuery = "select * from " + MoodContact.MoodEntry.TABLE_NAME + " where " + MoodContact.MoodEntry.COLUMN_PRIORITY + " =4";
        Cursor bCursor = database.rawQuery(bQuery, null);
        b = bCursor.getCount();
        textMoodBad.setText(String.valueOf(b));
        gCursor.close();

        String tQuery = "select * from " + MoodContact.MoodEntry.TABLE_NAME + " where " + MoodContact.MoodEntry.COLUMN_PRIORITY + " =5";
        Cursor tCursor = database.rawQuery(tQuery, null);
        t = tCursor.getCount();
        textMoodTerrible.setText(String.valueOf(t));
        tCursor.close();

    }

    private void Init() {
        quantityEntry = findViewById(R.id.quantityEntry);
        textMoodSuper = findViewById(R.id.textMoodSuper);
        textMoodGood = findViewById(R.id.textMoodGood);
        textMoodNeutral = findViewById(R.id.textMoodNeutral);
        textMoodBad = findViewById(R.id.textMoodBad);
        textMoodTerrible = findViewById(R.id.textMoodTerrible);
        chart = findViewById(R.id.pieChart);
    }

    public void openEntries(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void openCreateEntry(View view) {
        Intent intent = new Intent(this, CreateEntryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}