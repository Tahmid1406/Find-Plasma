package com.example.findplasma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Spinner Bloodspinner;
    private Button register;
    private String groups[] = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
    private ArrayAdapter<String> arrayAdapter;
    private String name, phone, location, bloodGroup;
    private EditText nameEdit, locationEdit;
    private TextView phoneEdit;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finding intial components
        Bloodspinner = (Spinner) findViewById(R.id.Bloodspinner);
        register = findViewById(R.id.registerButton);
        nameEdit = (EditText) findViewById(R.id.nameinfo);
        phoneEdit = (TextView) findViewById(R.id.phoneEdit);
        locationEdit = (EditText) findViewById(R.id.locationinfo);


        phone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        phoneEdit.setText(phone);

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
                location = locationEdit.getText().toString();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(MainActivity.this, "Enter your name", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(location)){
                    Toast.makeText(MainActivity.this, "Enter your location", Toast.LENGTH_SHORT).show();
                }else{
                    final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child(bloodGroup);
                    rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            HashMap<String,Object> userDataMap = new HashMap<>();
                            userDataMap.put("name", name);
                            userDataMap.put("phone", phone);
                            userDataMap.put("bloodgroup", bloodGroup);
                            userDataMap.put("location",location);
                            rootRef.updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if( task.isSuccessful()){
                                        Toast.makeText(MainActivity.this, "User Data Added", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });


    }


}
