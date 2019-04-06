package com.example.bsja9.hereiam.Settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bsja9.hereiam.Activities.MainActivity;
import com.example.bsja9.hereiam.Medicine.MedDbActivity;
import com.example.bsja9.hereiam.R;
import com.example.bsja9.hereiam.Activities.map;

//code on line 156 email valiation is from https://stackoverflow.com/questions/22348212/android-check-if-an-email-address-is-valid-or-not/22348303
public class settings extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private EditText phoneno,pinCode, second, email, miles,postcode;
    private Button button, medical_button, info,fabSave;
    private RadioButton SMS,radioEmail,Km,Miles;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SMS = findViewById(R.id.radioSMS);
        radioEmail = findViewById(R.id.radioEmail);
        Km = findViewById(R.id.radioKM);
        Miles = findViewById(R.id.radioM);

        saveButton();
        navagationBar();
        setSavedData();
        buttons();
    }

    private void buttons() {
        medical_button = (Button) findViewById(R.id.add_medicine);
        medical_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {startActivity(new Intent(settings.this, MedDbActivity.class));}
            }
        });


        info = findViewById(R.id.add_info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {startActivity(new Intent(settings.this, edit_info.class));}
            }
        });
    }

    private void setSavedData() {
        SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);

        phoneno = (EditText) findViewById(R.id.PhoneNumber);
        String number = sharedPreferences.getString("phoneNumber",null);
        phoneno.setText(number,TextView.BufferType.EDITABLE);

        pinCode = (EditText) findViewById(R.id.changePin);
        String pincode = sharedPreferences.getString("pincode",null);
        pinCode.setText(pincode,TextView.BufferType.EDITABLE);

        second = (EditText) findViewById(R.id.seconds);
        String seconds = sharedPreferences.getString("seconds",null);
        second.setText(seconds,TextView.BufferType.EDITABLE);

        email = (EditText) findViewById(R.id.email);
        String email_a = sharedPreferences.getString("email",null);
        email.setText(email_a,TextView.BufferType.EDITABLE);

        miles = (EditText) findViewById(R.id.miles);
        String miles_a = sharedPreferences.getString("miles",null);
        miles.setText(miles_a,TextView.BufferType.EDITABLE);

        postcode = (EditText) findViewById(R.id.postcode);
        String postcode_a = sharedPreferences.getString("postcode",null);
        postcode.setText(postcode_a,TextView.BufferType.EDITABLE);

        boolean smsOption = sharedPreferences.getBoolean("sms",false);
        boolean emailOption = sharedPreferences.getBoolean("radioEmail",false);

        if(smsOption){
            SMS.setChecked(true);
        }else if(emailOption) {
            radioEmail.setChecked(true);
        }

        boolean kmOption = sharedPreferences.getBoolean("km",false);
        boolean milesOption = sharedPreferences.getBoolean("rMiles",false);

        if(kmOption){
            Km.setChecked(true);
        }else if(milesOption) {
            Miles.setChecked(true);
        }
    }

    private void navagationBar() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.settingsButton);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.homeButton)
                {startActivity(new Intent(settings.this, MainActivity.class));}
                else if (item.getItemId() == R.id.mapsButton)
                {startActivity(new Intent(settings.this, map.class));}
                else if (item.getItemId() == R.id.settingsButton)
                {return true;}

                return false;
            }
        });
    }

    private void saveButton() {
        button = (Button) findViewById(R.id.save_button);
        FloatingActionButton fab = findViewById(R.id.fab_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {savingData();}
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savingData();
            }
        });
    }

    private void savingData() {

        SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("sms",SMS.isChecked());
        editor.apply();
        editor.putBoolean("radioEmail",radioEmail.isChecked());
        editor.apply();
        editor.putBoolean("km",Km.isChecked());
        editor.apply();
        editor.putBoolean("rMiles", Miles.isChecked());

        editor.putString("phoneNumber",phoneno.getText().toString());
        editor.apply();
        String number = sharedPreferences.getString("phoneNumber",null);
        phoneno.setText(number,TextView.BufferType.EDITABLE);

        if(phoneno.getText().toString().trim().length() == 0){
            phoneno.setError("You need to enter a phone number");
        }else if(phoneno.getText().toString().trim().length() > 0 && phoneno.getText().toString().trim().length() <11){
            phoneno.setError("Entered phone number is to short");
        }else if(phoneno.getText().toString().trim().length() >11){
            phoneno.setError("Entered phone number is to long");
        }

        editor.putString("postcode",postcode.getText().toString());
        editor.apply();
        String postcode_a = sharedPreferences.getString("postcode",null);
        postcode.setText(postcode_a,TextView.BufferType.EDITABLE);
        if(postcode.getText().toString().trim().length() == 0){
            postcode.setError("You need to enter a postcode or address ");
        }

        editor.putString("pincode",pinCode.getText().toString());
        editor.apply();
        String pincode = sharedPreferences.getString("pincode",null);
        pinCode.setText(pincode,TextView.BufferType.EDITABLE);
        if(pinCode.getText().toString().trim().length() == 0){
            pinCode.setError("A pin code is required");
        }else if (pinCode.getText().toString().trim().length() < 4 ||pinCode.getText().toString().trim().length() >4 ){
            pinCode.setError("The pin code needs to be 4 digits");
        }

        editor.putString("seconds",second.getText().toString());
        editor.apply();
        String seconds = sharedPreferences.getString("seconds",null);
        second.setText(seconds,TextView.BufferType.EDITABLE);
        if(second.getText().toString().trim().length() == 0){
            second.setError("You need to enter the length in seconds");
        }


        editor.putString("email",email.getText().toString());
        editor.apply();
        String email_a = sharedPreferences.getString("email",null);
        email.setText(email_a,TextView.BufferType.EDITABLE);
        if(email.getText().toString().trim().length() == 0){
            email.setError("You need to enter a email address");
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            email.setError("Please enter a valid email address");
        }

        editor.putString("miles",miles.getText().toString());
        editor.apply();
        String miles_a = sharedPreferences.getString("miles",null);
        miles.setText(miles_a,TextView.BufferType.EDITABLE);
        if(miles.getText().toString().trim().length() == 0){
            miles.setError("You need to enter a radius distance");
        }
        Toast.makeText(getApplicationContext(),"Information saved",Toast.LENGTH_SHORT).show();
    }


}
