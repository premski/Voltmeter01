package com.example.wp.voltmeter01;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import static android.R.color.holo_green_light;

public class MainActivity extends AppCompatActivity {
    private ProgressBar firstBar = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    public Lipo rcLipo = new Lipo ();
    public LipoChart lipoChart;



    /* Füllstand UI
    public BarEntry level = new BarEntry (1,0f);
    public BarEntry s1 = new BarEntry (1,0f);
    public BarEntry s2 = new BarEntry (2,0f);
    public BarEntry s3 = new BarEntry (3,0f);
    public BarEntry s4 = new BarEntry (4,0f);

    public ArrayList<BarEntry> entries1 = new ArrayList<>();
    public ArrayList<BarEntry> entries2 = new ArrayList<>();
    */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*firstBar = (ProgressBar) findViewById(R.id.firstBar);
        firstBar.setVisibility(View.VISIBLE);
        firstBar.setMax(150);
        firstBar.setProgress(100);
        */
        rcLipo.updateData(3.96f,7.95f,11.98f,0f);
        TextView textViewUges = (TextView)findViewById (R.id.uGes);
        HorizontalBarChart chart = (HorizontalBarChart) findViewById(R.id.chart);
        HorizontalBarChart chart2 = (HorizontalBarChart) findViewById(R.id.chart2);
        lipoChart = new LipoChart (rcLipo);
        lipoChart.refresh(rcLipo, textViewUges, chart, chart2);



        //ArrayList<BarEntry> entries = new ArrayList<>();
       /* level.setY (78f);
        entries1.add(level);
        BarDataSet dataset = new BarDataSet(entries1, "Füllstand (Annäherung in %)");*/


       /* HorizontalBarChart chart = (HorizontalBarChart) findViewById(R.id.chart);
        chart.getAxisRight().setAxisMinimum(0f);
        chart.getAxisRight().setAxisMaximum(100f);
        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisLeft().setAxisMaximum(100f);
        chart.getAxisLeft().setEnabled(false);
        chart.getXAxis().setEnabled(false);
        BarData data = new BarData(dataset);
        data.setValueTextSize(20);
        chart.setData(data);

        Description description = new Description();
        description.setText("%");
        //description.setTextSize(20);
        chart.setDescription(description);


        chart.invalidate();*/



/*
        //BarEntry s1 = new BarEntry (1,0f);
        s1.setY(4.0f);

        //BarEntry s2 = new BarEntry (2,0f);
        s2.setY(3.9f);

        //BarEntry s3 = new BarEntry (3,0f);
        s3.setY(3.87f);
        entries2.add(s1);
        entries2.add(s2);
        entries2.add(s3);
        BarDataSet dataset2 = new BarDataSet(entries2, "Einzelzellen [V]");
        HorizontalBarChart chart2 = (HorizontalBarChart) findViewById(R.id.chart2);
        BarData data2 = new BarData(dataset2);
        chart2.setData(data2);
        Description description2 = new Description();
        description2.setText("Volt");
        chart2.setDescription(description2);
        //chart2.setVisibleXRange(0, 4.5f);
        chart2.getAxisLeft().setAxisMinimum(3.55f);
        chart2.getAxisLeft().setAxisMaximum(4.5f);
        chart2.getAxisRight().setAxisMinimum(3.55f);
        chart2.getAxisRight().setAxisMaximum(4.5f);
        chart2.getXAxis().setEnabled(false);

        chart2.invalidate();*/


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void initUi () {

    }
    public void refreshUI () {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
