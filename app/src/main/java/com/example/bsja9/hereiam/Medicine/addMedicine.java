package com.example.bsja9.hereiam.Medicine;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bsja9.hereiam.R;

public class addMedicine extends AppCompatActivity {
    private EditText name,dose,time,comments;
    private dbNew medicine;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        name = findViewById(R.id.addMedicineName);
        dose = findViewById(R.id.addMedicineDose);
        time = findViewById(R.id.addMedicineTimes);
        comments = findViewById(R.id.addMedicineComments);

        save = findViewById(R.id.saveMed);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewMedicine();
            }
        });

    }

    private void saveNewMedicine() {
        String addName = name.getText().toString();
        String addDode = dose.getText().toString();
        String addTime = time.getText().toString();
        String addComments = comments.getText().toString();

        if(name.getText().toString().trim().length() == 0){
            name.setError("You need to enter the medicine name");
        }else if (dose.getText().toString().trim().length() == 0){
            dose.setError("You need to enter the medicine dose");
        }else if (time.getText().toString().trim().length() == 0){
            time.setError("You need to enter how many times a day the medicine needs to be taken");
        }else {
            dbNew medicine = new dbNew(addName, addDode, addTime, addComments);
            MedicineDataSource dataSource = new MedicineDataSource(this);
            dataSource.open();
            dataSource.save(medicine);
            finish();
        }
    }

}
