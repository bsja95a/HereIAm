package com.example.bsja9.hereiam.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsja9.hereiam.R;
import com.google.android.gms.maps.GoogleMap;
import com.kristijandraca.backgroundmaillibrary.BackgroundMail;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Fall extends Activity {

    private Button yes, no;
    private TextView time;
    private CountDownTimer countDownTimer;
    private GoogleMap mMap;
    Geocoder geocoder;
    List<Address> addresses;

    //Code for converting lat lon to address (geocoder) is from Stackoverflow https://stackoverflow.com/questions/9409195/how-to-get-complete-address-from-latitude-and-longitude
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fallen);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .5));

        final MediaPlayer sound = MediaPlayer.create(this, R.raw.siren);
        sound.start();

        time = (TextView) findViewById(R.id.timmer);
        start();

        no = (Button) findViewById(R.id.fall_no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                    finish();
                }

            }
        });


        yes = (Button) findViewById(R.id.fall_yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fallen();
                finish();
            }

        });
    }

    private void start() {
        SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        String seconds = sharedPreferences.getString("seconds",null);
        int sec = Integer.parseInt(seconds);
        time.setText(seconds);
        countDownTimer = new CountDownTimer(sec * 1000, 1000) {
            @Override
            public void onTick(long l) {
                time.setText("" + l/1000);
            }

            @Override
            public void onFinish() {
            fallen();
            finish();
            }
        };
        countDownTimer.start();
    }

    private void fallen(){

        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Calendar c = Calendar.getInstance();

        geocoder = new Geocoder(this,Locale.UK);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fall_address = addresses.get(0).getAddressLine(0);
        String knownName = addresses.get(0).getThoroughfare();
        //String message = "Fall detected at " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + " on " + date+ " at Longitude:"+ location.getLongitude()+" Latitude: "+location.getLatitude() ;
        String message = "Fall detected at " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + " on " + date+ " at: "+ knownName+" "+fall_address;
        options(message);
        SharedPreferences sharedPreferences = getSharedPreferences("falls", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Fall detected", message.toString());
        editor.apply();
    }

    private void options(String message) {

        SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean smsOption = sharedPreferences.getBoolean("sms",false);
        boolean emailOption = sharedPreferences.getBoolean("radioEmail",false);
        String number = sharedPreferences.getString("phoneNumber", null);

        if(smsOption){
            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage(number, null, message, null, null);

        }else if(emailOption) {
            SharedPreferences sharedPreferences_email = getSharedPreferences("settings", Context.MODE_PRIVATE);
            String email = sharedPreferences_email.getString("email",null);
            BackgroundMail bm = new BackgroundMail(this);
            bm.setGmailUserName("FallDetectionApp95@gmail.com");
            bm.setGmailPassword("FallDetectionApp95#59");
            bm.setMailTo(email);
            bm.setFormSubject("Fall Detected ");
            bm.setFormBody(message);
            bm.send();
        }



    }
}