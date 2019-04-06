package com.example.bsja9.hereiam.Medicine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bsja9.hereiam.Activities.Fall;
import com.example.bsja9.hereiam.R;
import com.example.bsja9.hereiam.Reminders.reminders;


/**
 * Created by bsja9 on 11/04/2018.
 */

public class medDbActicityNoEdit extends AppCompatActivity implements medicineAdapter.onMedicineClickListener, SensorEventListener {

    private RecyclerView mRecyclerView;
    private medicineAdapter mAdapter;
    private Cursor mCursor;
    private MedicineDataSource meDataSource;
    private Button callButton;
    private Sensor mSensor;
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_db_no_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mRecyclerView = findViewById(R.id.medicineListNewNoEdit);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        meDataSource = new MedicineDataSource(this);
        meDataSource.open();
        updateUI();
        callButton();


    }


        private void callButton() {
            callButton = (Button) findViewById(R.id.call_button_no_edit);
            callButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onClick(View view) {

                    SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                    String number = sharedPreferences.getString("phoneNumber",null);

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+number));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(callIntent);
                }
            });
        }


    private void updateUI() {
        mCursor = meDataSource.findAll();
        if(mAdapter == null){
            mAdapter = new medicineAdapter(mCursor,this);
            mRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.swapCursor(mCursor);
        }
    }



    @Override
    public void onMedicineClick(dbNew medicine) {
    }



    protected void onResume() {
        super.onResume();
        meDataSource.open();
        updateUI();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mCursor != null && !mCursor.isClosed()) mCursor.close();
        meDataSource.close();
        mSensorManager.unregisterListener(this);

    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            double x = sensorEvent.values[0];
            double y = sensorEvent.values[1];
            double z = sensorEvent.values[2];
            double vLength = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
            if (vLength < 2) {

                String message = "Fall detected";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(medDbActicityNoEdit.this, Fall.class));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }




}
