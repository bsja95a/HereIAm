package com.example.bsja9.hereiam.Settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bsja9.hereiam.Activities.Fall;
import com.example.bsja9.hereiam.Activities.MainActivity;
import com.example.bsja9.hereiam.R;
import com.example.bsja9.hereiam.Activities.map;
import com.goodiebag.pinview.Pinview;
import com.kristijandraca.backgroundmaillibrary.BackgroundMail;

public class pin_settings extends AppCompatActivity implements SensorEventListener {

    private Button callButton;
    private BottomNavigationView bottomNavigationView;
    private TextView wrongPin;
    private Sensor mSensor;
    private SensorManager mSensorManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        navigationBar();
        callButton();
        pinView();


    }

    private void pinView() {
        Pinview pinview =(Pinview)findViewById(R.id.pinView);
        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {

                SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);

                String pincode = sharedPreferences.getString("pincode",null);
                String defult_pincode = "0000";

                wrongPin = findViewById(R.id.wrongPin);
                if(pinview.getValue().equals(pincode) || pinview.getValue().equals(defult_pincode)){
                    Toast.makeText(pin_settings.this," Pincode correct", Toast.LENGTH_SHORT).show();
                    {startActivity(new Intent(pin_settings.this, settings.class));}

                }else{
                    Toast.makeText(pin_settings.this,"Incorrect Pincode", Toast.LENGTH_SHORT).show();
                    wrongPin.setText("Incorrect pin entered");

                    email();
                    }

            }
        });
    }

    private void email() {
        SharedPreferences sharedPreferences_email = getSharedPreferences("settings", Context.MODE_PRIVATE);
        String email = sharedPreferences_email.getString("email",null);

        BackgroundMail bm = new BackgroundMail(this);
        bm.setGmailUserName("FallDetectionApp95@gmail.com");
        bm.setGmailPassword("FallDetectionApp95#59");
        bm.setMailTo(email);
        bm.setFormSubject("Wrong pin has been entered");
        bm.setFormBody("The wrong pin code has been entered into the application");
        bm.send();
    }

    private void callButton() {
        callButton = (Button) findViewById(R.id.call_button_pin);
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

    private void navigationBar() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.settingsButton);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.homeButton)
                {startActivity(new Intent(pin_settings.this, MainActivity.class));}
                else if (item.getItemId() == R.id.mapsButton)
                {startActivity(new Intent(pin_settings.this, map.class));}
                else if (item.getItemId() == R.id.settingsButton)
                {return true;}
                return false;
            }
        });
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
                startActivity(new Intent(pin_settings.this, Fall.class));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

}
