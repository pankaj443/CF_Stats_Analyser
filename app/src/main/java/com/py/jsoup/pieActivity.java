package com.py.jsoup;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class pieActivity extends AppCompatActivity {


    private static String TAG = "MainActivity";

    private float[] yData;
    private String[] xData = {"LEVEL A", "LEVEL B" , "LEVEL C" , "LEVEL D", "LEVEL E", "LEVEL F"};
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);

        Intent intent = getIntent();
        String a = intent.getStringExtra("a");
        String b = intent.getStringExtra("b");
        String c = intent.getStringExtra("c");
        String d = intent.getStringExtra("d");
        String k = intent.getStringExtra("k");
        String f = intent.getStringExtra("f");





        pieChart = (PieChart) findViewById(R.id.idPieChart);
        yData = new float[]{Float.parseFloat(a),Float.parseFloat(b), Float.parseFloat(c), Float.parseFloat(d), Float.parseFloat(k), Float.parseFloat(f)};

        pieChart.setRotationEnabled(true);
        pieChart.setUsePercentValues(true);

        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setHoleRadius(55f);

        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("LEVEL");
        pieChart.setCenterTextSize(20);
        pieChart.setDrawEntryLabels(true);
        pieChart.setEntryLabelTextSize(10);


        addDataSet();


    }


    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();


        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , xData[i]));
        }



        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "CODEFORCES");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.DKGRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.LTGRAY);
        colors.add(Color.CYAN);
        colors.add(Color.MAGENTA);


        pieDataSet.setColors(colors);

        PieData data = new PieData(pieDataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);


    }
}
