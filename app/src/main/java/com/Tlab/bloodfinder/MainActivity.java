package com.Tlab.bloodfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static maes.tech.intentanim.CustomIntent.customType;

public class MainActivity extends AppCompatActivity {
    private Spinner Bloodspinner,treatSpinner;
    private Button registerButton;
    private String groups[] = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
    private String treat[] = {"Hospital Treatment", "Home Treatment"};
    private ArrayAdapter<String> arrayAdapter;
    private ArrayAdapter<String> treatAdapter;
    private String name, phone, location, bloodGroup, treatment;
    private EditText nameinfo, locationinfo;
    private TextView phoneEdit;
    //private FirebaseAuth mAuth;
    private DatabaseReference rootRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finding intial components
        Bloodspinner = (Spinner) findViewById(R.id.Bloodspinner);
        treatSpinner = findViewById(R.id.treatSpinner);
        registerButton = findViewById(R.id.registerButton);
        nameinfo = (EditText) findViewById(R.id.nameinfo);
        locationinfo = (EditText) findViewById(R.id.locationinfo);
        phoneEdit = findViewById(R.id.phoneEdit);

        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        phoneEdit.setText("+88" + phone);

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, groups);
        Bloodspinner.setAdapter(arrayAdapter);

        treatAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, treat);
        treatSpinner.setAdapter(treatAdapter);


       rootRef = FirebaseDatabase.getInstance().getReference().child("All Users");



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


        treatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                treatment = treat[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameinfo.getText().toString().trim();
                location = locationinfo.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    nameinfo.setError("Enter your name");
                }else if(TextUtils.isEmpty(location)){
                    locationinfo.setError("Enter your location");
                }else {
                    pushData(name, phone, bloodGroup, treatment, location);
                }
            }
        });


    }

    private void pushData(String name, final String phone, String bloodGroup, String treatment, String location) {

        HashMap<String,Object> userMap = new HashMap<>();
        userMap.put("name", name);
        userMap.put("phone", phone);
        userMap.put("bloodgroup", bloodGroup);
        userMap.put("treatment", treatment);
        userMap.put("location", location);

        rootRef.child(phone).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Information Added to Database", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
                    customType(MainActivity.this,"left-to-right");
                    finish();
                }
            }
        });

    }


}
