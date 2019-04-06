package com.example.bsja9.hereiam.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bsja9.hereiam.R;

/**
 * Created by bsja9 on 10/03/2018.
 */

public class Popup extends Activity {

    private TextView falls_info_box;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.falls_pop);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.5));

        falls_info_box = (TextView) findViewById(R.id.falls_info);

        SharedPreferences sharedPreferences = getSharedPreferences("falls", Context.MODE_PRIVATE);
        String falls = sharedPreferences.getString("Fall detected", null);
        falls_info_box.setText(falls, TextView.BufferType.EDITABLE);



    }
}
