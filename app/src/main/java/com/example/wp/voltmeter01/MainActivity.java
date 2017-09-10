package com.example.wp.voltmeter01;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.StringTokenizer;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;


public class MainActivity extends AppCompatActivity {
//public class MainActivity extends Activity {
    private ProgressBar firstBar = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    public Lipo rcLipo = new Lipo ();
    public LipoChart lipoChart;



    private final String TAG = MainActivity.class.getSimpleName();


    BluetoothSPP bt;

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
        // nur zum Testen: Akkuspannungen festlegen
        float s1=4.03f;
        float s2=4.01f;
        float s3=4.00f;
        float s4=0f;

        rcLipo.updateData(s1,s1+s2,s1+s2+s3,s1+s2+s3+s4); // Aufruf muss ersetzt werden durch echte Messwerte
        final TextView textViewUges = (TextView)findViewById (R.id.uGes);
        final HorizontalBarChart chart = (HorizontalBarChart) findViewById(R.id.chart);
        final HorizontalBarChart chart2 = (HorizontalBarChart) findViewById(R.id.chart2);
        lipoChart = new LipoChart (rcLipo);
        lipoChart.refresh(rcLipo, textViewUges, chart, chart2);


        final TextView helloTextView = (TextView) findViewById(R.id.readBuffer);


        bt = new BluetoothSPP(this);

        if(!bt.isBluetoothAvailable()) {
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            //finish();
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                helloTextView.setText("Message: " + message);
                try {
                    StringTokenizer tokens = new StringTokenizer(message, ";");
                    float s1 = Float.valueOf(tokens.nextToken()).floatValue();// s1
                    float s2 = Float.valueOf(tokens.nextToken()).floatValue();// s2
                    float s3 = Float.valueOf(tokens.nextToken()).floatValue();// s3
                    float s4 = Float.valueOf(tokens.nextToken()).floatValue();// s4

                    rcLipo.updateData(s1, s1 + s2, s1 + s2 + s3, s1 + s2 + s3 + s4);
                    lipoChart.refresh(rcLipo, textViewUges, chart, chart2);
                    //Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    System.out.println (e.toString());
                }
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() {
                Toast.makeText(getApplicationContext()
                        , "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() {
                Toast.makeText(getApplicationContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnConnect = (Button)findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });









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










    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
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
        if (!bt.isBluetoothEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if(!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            }
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }
    public void setup() {
       /* Button btnSend = (Button)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                bt.send("Text", true);
            }
        });*/
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if(resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if(requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        bt.stopService();

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
