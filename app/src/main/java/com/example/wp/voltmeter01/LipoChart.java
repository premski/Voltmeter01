package com.example.wp.voltmeter01;

import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

/**
 * Created by wp on 04.09.2017.
 */

public class LipoChart {
    private ArrayList<BarEntry> entries1;
    private ArrayList<BarEntry> entries2;
    BarEntry fillLevel;
    BarEntry [] cell = new BarEntry[4];

    public void refresh (Lipo lipo, TextView textViewUGes, HorizontalBarChart chartFillLevel, HorizontalBarChart chartCells) {
        entries1 = new ArrayList<>();
        entries2 = new ArrayList<>();

        // Gesamtspannung
        textViewUGes.setText(""+lipo.getuGes());

        // Füllstand
        fillLevel.setY (lipo.getFillLevel());
        entries1.add(fillLevel);
        BarDataSet dataset = new BarDataSet(entries1, "Füllstand Durchschnitt (Annäherung in %)");

        chartFillLevel.getAxisRight().setAxisMinimum(0f);
        chartFillLevel.getAxisRight().setAxisMaximum(100f);
        chartFillLevel.getAxisLeft().setAxisMinimum(0);
        chartFillLevel.getAxisLeft().setAxisMaximum(100f);
        chartFillLevel.getAxisLeft().setEnabled(false);
        chartFillLevel.getXAxis().setEnabled(false);
        BarData data = new BarData(dataset);
        data.setValueTextSize(20);
        chartFillLevel.setData(data);

        Description description = new Description();
        description.setText("%");
        //description.setTextSize(20);
        chartFillLevel.setDescription(description);
        chartFillLevel.invalidate();

        /* Einzelzellen */
        for (int i = 0; i<lipo.getNrCellsDetected();i++) {
            cell[i].setY (lipo.getCellU(i));
            entries2.add (cell[i]);
            System.out.println ("ChartRefresh: Cell [" + i + "]" + " = " + lipo.getCellU(i));
        }

        BarDataSet dataset2 = new BarDataSet(entries2, "Einzelzellen [V]");
        BarData data2 = new BarData(dataset2);
        chartCells.setData(data2);
        Description description2 = new Description();
        description2.setText("Volt");
        chartCells.setDescription(description2);
        //chart2.setVisibleXRange(0, 4.5f);
        chartCells.getAxisLeft().setAxisMinimum(3.55f);
        chartCells.getAxisLeft().setAxisMaximum(4.25f);
        chartCells.getAxisRight().setAxisMinimum(3.55f);
        chartCells.getAxisRight().setAxisMaximum(4.25f);
        chartCells.getXAxis().setEnabled(false);
        chartCells.invalidate();


    }

    public LipoChart(Lipo lipo) {


        fillLevel = new BarEntry(1,0f);
        for (int i = 0; i<lipo.getNrCellsDetected();i++) {
            cell[i] = new BarEntry (i+1, lipo.getCellU(i));
        }

    }
}
