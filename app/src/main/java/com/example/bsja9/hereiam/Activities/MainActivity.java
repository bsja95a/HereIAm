package com.example.bsja9.hereiam.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bsja9.hereiam.Medicine.medDbActicityNoEdit;
import com.example.bsja9.hereiam.R;
import com.example.bsja9.hereiam.Reminders.reminders;
import com.example.bsja9.hereiam.Settings.pin_settings;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private BottomNavigationView bottomNavigationView;
    private Button callButton, falls, todo, medicine,info;
    private Sensor mSensor;
    private SensorManager mSensorManager;
    private TextView timeAndDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        callButton();
        fallsButton();
        otherButtons();
        navigationBar();
        timeAndDate();

        permissionsCheck();
    }

    private void permissionsCheck() {
       int count = 0;
        while (count != 1){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 2);
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 3);
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 4);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 5);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 6);
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 7);
        }
            count = 1;

        }
    }

    private void navigationBar() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.homeButton);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.homeButton)
                {return true;}
                else if (item.getItemId() == R.id.mapsButton)
                {startActivity(new Intent(MainActivity.this, map.class));}
                else if (item.getItemId() == R.id.settingsButton)
                {startActivity(new Intent(MainActivity.this, pin_settings.class));}

                return false;
            }
        });
    }

    private void otherButtons() {
        todo = (Button) findViewById(R.id.ToDo);
        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {startActivity(new Intent(MainActivity.this, reminders.class));}

            }
        });
        info = (Button) findViewById(R.id.info_button);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    startActivity(new Intent(MainActivity.this, Info.class));
                }
            }

        });


        medicine = (Button) findViewById(R.id.medicine);
        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {startActivity(new Intent(MainActivity.this, medDbActicityNoEdit.class));}

            }
        });
    }

    private void fallsButton() {
        falls = (Button) findViewById(R.id.View_falls);
        falls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {startActivity(new Intent(MainActivity.this, Popup.class));}

            }
        });
        falls.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                {startActivity(new Intent(MainActivity.this, Fall.class));}
                return false;
            }
        });
    }

    private void callButton() {
        callButton = (Button) findViewById(R.id.call_button_home);
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

    private void timeAndDate() {
        timeAndDate = (TextView) findViewById(R.id.timeAndDate);
        Date today = Calendar.getInstance().getTime();

        SimpleDateFormat YearFormat = new SimpleDateFormat("yyyy");
        String year = YearFormat.format(today);

        SimpleDateFormat MonthFormat = new SimpleDateFormat("MMMM");
        String month = MonthFormat.format(today);

        SimpleDateFormat DateFormat = new SimpleDateFormat("dd");
        String date = DateFormat.format(today);

        SimpleDateFormat DayFormat = new SimpleDateFormat("EEEE");
        String day = DayFormat.format(today);

        String ordinal;
        if(date.equals("1") || date.equals("11") || date.equals("21")|| date.equals("31")){
            ordinal = "st";

        }else if(date.equals("2") || date.equals("12") || date.equals("22")){
             ordinal = "nd";

        }else if(date.equals("3") || date.equals("13") || date.equals("23")){
             ordinal = "rd";

        }else{
             ordinal = "th";
        }

        String date_text = ("Today is "+day + "\nThe "+date+ ordinal+" of " +month + "\nThe year is "+year);

        timeAndDate.setText(date_text);

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
                startActivity(new Intent(MainActivity.this, Fall.class));
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
