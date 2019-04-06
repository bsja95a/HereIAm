package com.example.bsja9.hereiam.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bsja9.hereiam.R;
import com.example.bsja9.hereiam.Settings.pin_settings;

public class Info extends AppCompatActivity implements SensorEventListener {

    private TextView name, age, nhs, address, phone, carName, carPhone, carID, carComp;
    private Button callButton;
    private BottomNavigationView bottomNavigationView;
    private Sensor mSensor;
    private SensorManager mSensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

       callButton();
       setData();
       navigationBar();

    }

    private void navigationBar() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.homeButton)
                {startActivity(new Intent(Info.this, MainActivity.class));}
                else if (item.getItemId() == R.id.mapsButton)
                {startActivity(new Intent(Info.this, map.class));}
                else if (item.getItemId() == R.id.settingsButton)
                {startActivity(new Intent(Info.this, pin_settings.class));}

                return false;
            }
        });
    }

    private void setData() {
        SharedPreferences sharedPreferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        name = findViewById(R.id.infoName);
        age = findViewById(R.id.infoAge);
        nhs = findViewById(R.id.infoNhs);
        address = findViewById(R.id.infoAddress);
        phone = findViewById(R.id.infoPhone);
        carName = findViewById(R.id.infoNameCarer);
        carPhone = findViewById(R.id.infoPhoneCarer);
        carID = findViewById(R.id.infoNhsCarer);
        carComp = findViewById(R.id.infoCarerComp);

        String sName = sharedPreferences.getString("name",null);
        String fName = "Name: "+sName;
        name.setText(fName);

        String sAge = sharedPreferences.getString("age",null);
        String fAge = "Age: "+sAge;
        age.setText(fAge);

        String sPhone = sharedPreferences.getString("phoneNumber",null);
        String fPhone = "Phone number: "+sPhone;
        phone.setText(fPhone);

        String sAddress = sharedPreferences.getString("address",null);
        String fAddress = "Address: "+sAddress;
        address.setText(fAddress);

        String sNhs = sharedPreferences.getString("nhs",null);
        String fNhs = "NHS number: "+sNhs;
        nhs.setText(fNhs);

        String sCarName = sharedPreferences.getString("carName", null);
        String fCarName = "Carer's name "+sCarName;
        carName.setText(fCarName);

        String sCarPhone = sharedPreferences.getString("carPhone", null);
        String fCarPhone = "Carer's phone number "+sCarPhone;
        carPhone.setText(fCarPhone);

        String sCarID = sharedPreferences.getString("carID", null);
        String fCarID = "Carer's ID "+sCarID;
        carID.setText(fCarID);

        String sCarComp = sharedPreferences.getString("carComp", null);
        String fCarComp = "Carer's Company "+sCarComp;
        carID.setText(fCarComp);



    }

    private void callButton() {
        callButton = (Button) findViewById(R.id.call_button_info);
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
                startActivity(new Intent(Info.this, Fall.class));
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
