package com.example.bsja9.hereiam.Activities;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bsja9.hereiam.Manifest;
import com.example.bsja9.hereiam.R;
import com.example.bsja9.hereiam.Settings.pin_settings;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.kristijandraca.backgroundmaillibrary.BackgroundMail;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class map extends AppCompatActivity implements SensorEventListener, OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    //code to conveert postcode to lat lon is from stackoverflow, https://stackoverflow.com/questions/3574644/how-can-i-find-the-latitude-and-longitude-from-address
    private BottomNavigationView bottomNavigationView;
    private Button callButton;
    private GoogleMap mMap;
    private TextView distanceText;
    String unitOptions;

    private Sensor mSensor;
    private SensorManager mSensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        navigationBar();
        callButton();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void callButton() {
        callButton = (Button) findViewById(R.id.call_button_map);
        callButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                String number = sharedPreferences.getString("phoneNumber", null);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);
            }
        });
    }

    private void navigationBar() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.homeButton) {
                    startActivity(new Intent(map.this, MainActivity.class));
                } else if (item.getItemId() == R.id.mapsButton) {
                    return true;
                } else if (item.getItemId() == R.id.settingsButton) {
                    startActivity(new Intent(map.this, pin_settings.class));
                }
                return false;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        } else {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(this);
            mMap.setOnMyLocationClickListener(this);
            mMap.setOnMyLocationClickListener(this);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.setTrafficEnabled(false);

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            assert locationManager != null;
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
            String postcode = sharedPreferences.getString("postcode", null);
            Geocoder coder = new Geocoder(this);
            List<Address> address;
            List<Address> current_addresses = null;
            LatLng p1 = null;
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            try {
                address = coder.getFromLocationName(postcode, 1);
                Address postcode_location = address.get(0);
                double postcode_lat = postcode_location.getLatitude();
                double postcode_lon = postcode_location.getLongitude();
                p1 = new LatLng(postcode_lat, postcode_lon);
                System.out.println(p1);
                current_addresses = coder.getFromLocation(latitude, longitude, 1);


            } catch (IOException e) {
                e.printStackTrace();
            }
            String current_addresses1 = current_addresses.get(0).getAddressLine(0);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude /*location.getLatitude()*/, longitude/* location.getLongitude()*/), 16.0f));
            String meter = sharedPreferences.getString("miles", null);
            int meters = Integer.parseInt(meter);
            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(p1)
                    .radius(meters)
                    .strokeColor(Color.RED)
            );

            boolean kmOption = sharedPreferences.getBoolean("km",false);
            boolean milesOption = sharedPreferences.getBoolean("rMiles",false);

            LatLng latLngA = new LatLng(latitude, longitude);
            LatLng latLngB = p1;
            Location locationA = new Location("point A");
            locationA.setLatitude(latLngA.latitude);
            locationA.setLongitude(latLngA.longitude);
            Location locationB = new Location("point B");
            locationB.setLatitude(latLngB.latitude);
            locationB.setLongitude(latLngB.longitude);
            double distance = locationA.distanceTo(locationB);


            if(milesOption) {
                distance = distance * 0.000621371;
                distance = (double) Math.round(distance * 100) / 100;
                 unitOptions = " miles from ";
            }else if (kmOption){
                distance = (double) Math.round(distance)/1000;
                unitOptions = " km from ";
            }


            distanceText = (TextView) findViewById(R.id.distance_text);
            distanceText.setText("You are " + distance + unitOptions + postcode);
            String miles = sharedPreferences.getString("miles", null);

            int mile = Integer.parseInt(miles);
            double distanceInMeters = distance * 1609.344;

            options(mile, distanceInMeters, distance, latitude, longitude, current_addresses1);

        }
    }

    private void options(int mile,double distanceInMeters,double distance,double latitude,double longitude,String current_addresses1 ) {
        SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean smsOption = sharedPreferences.getBoolean("sms",false);
        boolean emailOption = sharedPreferences.getBoolean("radioEmail",false);
        String message = "User has left the safe zone and is currently "+distance+" miles away at\nLatitude +"+latitude +"\nLongitude "+ longitude+"\n("+current_addresses1+")";
        String number = sharedPreferences.getString("phoneNumber", null);

        if(smsOption){
            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage(number, null, message, null, null);
            notification();

        }else if(emailOption) {
            if (distanceInMeters > mile ){
                SharedPreferences sharedPreferences_email = getSharedPreferences("settings", Context.MODE_PRIVATE);
                String email = sharedPreferences_email.getString("email",null);
                BackgroundMail bm = new BackgroundMail(this);
                bm.setGmailUserName("FallDetectionApp95@gmail.com");
                bm.setGmailPassword("FallDetectionApp95#59");
                bm.setMailTo(email);
                bm.setFormSubject("User has left the safe zone");
                bm.setFormBody(message);
                bm.send();
                notification();
            }
        }


    }

    private void notification() {
        SharedPreferences sharedPreferences1 = getSharedPreferences("info", Context.MODE_PRIVATE);
        String sName = sharedPreferences1.getString("name",null);
        String sCarName = sharedPreferences1.getString("carName", null);
        String sCarPhone = sharedPreferences1.getString("carPhone", null);

        String notMessage = "My name is "+sName+" and suffer from dementia, my carer is "+sCarName+" Their number is "+sCarPhone;
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, Info.class), 0);

        NotificationManager newNotification = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(getApplicationContext()).setContentTitle("I suffer from dementia and is lost").setContentText(notMessage)
                .setContentIntent(contentIntent).setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                .setStyle(new Notification.BigTextStyle().bigText(notMessage)).build();
        newNotification.notify(0,notification);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }
    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "You are here:\n" + location, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Calendar c = Calendar.getInstance();
        Sensor mySensor = sensorEvent.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            double x = sensorEvent.values[0];
            double y = sensorEvent.values[1];
            double z = sensorEvent.values[2];
            double vLength = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
            if (vLength < 2) {
                String message = "Fall detected";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(map.this, Fall.class));
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