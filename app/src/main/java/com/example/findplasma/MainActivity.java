package com.example.findplasma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Spinner Bloodspinner;
    Button register;
    String groups[] = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
    ArrayAdapter<String> arrayAdapter;
    String name, phone, location, bloodGroup;
    EditText nameEdit, phoneEdit, locationEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finding intial components
        Bloodspinner = (Spinner) findViewById(R.id.Bloodspinner);
        register = findViewById(R.id.register);
        nameEdit = (EditText) findViewById(R.id.nameEdit);
        phoneEdit = (EditText) findViewById(R.id.phoneEdit);
        locationEdit = (EditText) findViewById(R.id.locationEdit);


        //getting the intent extra information from welcome activity
        String recoverType = getIntent().getStringExtra("recover");
        String hospital = getIntent().getStringExtra("hospitalname");

        Toast.makeText(this, recoverType + "  " + hospital, Toast.LENGTH_SHORT).show();



        arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, groups);
        Bloodspinner.setAdapter(arrayAdapter);

        // getting blood group from the spinner
        Bloodspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodGroup = groups[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        // register button onClict event
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEdit.getText().toString();
                phone = phoneEdit.getText().toString().trim();
                location = locationEdit.getText().toString();
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(MainActivity.this, "Enter your name", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(phone)){
                    Toast.makeText(MainActivity.this, "Enter phone number", Toast.LENGTH_SHORT).show();
                }else if(phone.length() < 10){
                    Toast.makeText(MainActivity.this, "Phone number is not valid", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(location)){
                    Toast.makeText(MainActivity.this, "Enter your location", Toast.LENGTH_SHORT).show();
                }else{
                    validateUser();
                }
            }
        });


    }

    private void validateUser() {

    }

}
