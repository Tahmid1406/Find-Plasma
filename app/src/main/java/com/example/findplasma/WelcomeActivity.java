package com.example.findplasma;

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
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {
    Spinner treatmentCategory;
    String category[] ={"Hospital Treatment","Home Treatment"};
    ArrayAdapter<String> trecat;
    EditText hospitalName;
    String recover = "Hospital Treatment";
    Button becomeDonor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //finding the onCreate initial components
        treatmentCategory = (Spinner) findViewById(R.id.treatmentCategory);
        becomeDonor = (Button) findViewById(R.id.becomeDonor);
        hospitalName = findViewById(R.id.hospitalName);


        //setting adapter to the recovery type spinner
        trecat = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, category);
        treatmentCategory.setAdapter(trecat);

        //retrieving text from recovery type spinner
        treatmentCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                recover = category[position];
                Toast.makeText(WelcomeActivity.this, recover, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //become donor button on click listener
        becomeDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                intent.putExtra("recover", recover);
                String hospitalname = hospitalName.getText().toString();
                if(TextUtils.isEmpty(hospitalname)){
                    intent.putExtra("hospitalname", "Not Applicable");
                }else{
                    intent.putExtra("hospitalname", hospitalname);
                }
                startActivity(intent);
            }
        });

    }
}
