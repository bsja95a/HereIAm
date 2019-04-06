package com.example.bsja9.hereiam.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bsja9.hereiam.R;

public class edit_info extends AppCompatActivity {

    private Button save;
    private EditText name, age,phone,address,nhs,carName, carPhone,carID, carComp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        name = findViewById(R.id.patName);
        age = findViewById(R.id.patAge);
        phone = findViewById(R.id.patPhoneNumber);
        address = findViewById(R.id.patAddress);
        nhs = findViewById(R.id.patNHS);
        carName = findViewById(R.id.carerName);
        carPhone = findViewById(R.id.carerPhone);
        carID = findViewById(R.id.carerID);
        carComp = findViewById(R.id.carerCom);

       setData();
       saveButton();
}

    private void saveButton() {
        save = findViewById(R.id.save_button_info);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Information Saved", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getSharedPreferences("info", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("name",name.getText().toString());
                editor.apply();
                String name1 = sharedPreferences.getString("name",null);
                name.setText(name1,TextView.BufferType.EDITABLE);

                editor.putString("age",age.getText().toString());
                editor.apply();
                String age1 = sharedPreferences.getString("age",null);
                age.setText(age1,TextView.BufferType.EDITABLE);

                editor.putString("phoneNumber",phone.getText().toString());
                editor.apply();
                String number = sharedPreferences.getString("phoneNumber",null);
                phone.setText(number, TextView.BufferType.EDITABLE);

                editor.putString("address",address.getText().toString());
                editor.apply();
                String address1 = sharedPreferences.getString("address",null);
                address.setText(address1, TextView.BufferType.EDITABLE);

                editor.putString("nhs",nhs.getText().toString());
                editor.apply();
                String nhs1 = sharedPreferences.getString("nhs",null);
                nhs.setText(nhs1,TextView.BufferType.EDITABLE);

                editor.putString("carName",carName.getText().toString());
                editor.apply();
                String carName1 = sharedPreferences.getString("carName",null);
                carName.setText(carName1,TextView.BufferType.EDITABLE);

                editor.putString("carPhone",carPhone.getText().toString());
                editor.apply();
                String carPhone1 = sharedPreferences.getString("carPhone",null);
                carPhone.setText(carPhone1,TextView.BufferType.EDITABLE);

                editor.putString("carID",carID.getText().toString());
                editor.apply();
                String carID1 = sharedPreferences.getString("carID",null);
                carID.setText(carPhone1,TextView.BufferType.EDITABLE);

                editor.putString("carComp",carComp.getText().toString());
                editor.apply();
                String carComp1 = sharedPreferences.getString("carComp",null);
                carComp.setText(carComp1,TextView.BufferType.EDITABLE);


            };


        });
    }

    private void setData() {
        SharedPreferences sharedPreferences = getSharedPreferences("info", Context.MODE_PRIVATE);

        String sName = sharedPreferences.getString("name",null);
        name.setText(sName,TextView.BufferType.EDITABLE);
        String sAge = sharedPreferences.getString("age",null);
        age.setText(sAge,TextView.BufferType.EDITABLE);
        String sPhone = sharedPreferences.getString("phoneNumber",null);
        phone.setText(sPhone,TextView.BufferType.EDITABLE);
        String sAddress = sharedPreferences.getString("address",null);
        address.setText(sAddress,TextView.BufferType.EDITABLE);
        String sNhs = sharedPreferences.getString("nhs",null);
        nhs.setText(sNhs,TextView.BufferType.EDITABLE);

        String sCarName = sharedPreferences.getString("carName", null);
        carName.setText(sCarName, TextView.BufferType.EDITABLE);
        String sCarPhone = sharedPreferences.getString("carPhone", null);
        carPhone.setText(sCarPhone,TextView.BufferType.EDITABLE);
        String sCarID = sharedPreferences.getString("carID", null);
        carID.setText(sCarID, TextView.BufferType.EDITABLE);
        String sCarComp = sharedPreferences.getString("carComp", null);
        carComp.setText(sCarComp,TextView.BufferType.EDITABLE);



    }
}
