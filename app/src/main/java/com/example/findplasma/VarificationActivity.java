package com.example.findplasma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findplasma.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static maes.tech.intentanim.CustomIntent.customType;

public class VarificationActivity extends AppCompatActivity {
    private EditText editTextPhone, editTextpass;
    private TextView loginText;
    private Button registerNum;
    private DatabaseReference userref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varification);

        editTextPhone = findViewById(R.id.editTextPhone);
        editTextpass= findViewById(R.id.editTextpass);
        registerNum = findViewById(R.id.registerNum);
        loginText = findViewById(R.id.loginText);

        userref = FirebaseDatabase.getInstance().getReference().child("Users");

        registerNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = editTextPhone.getText().toString().trim();
                String pass = editTextpass.getText().toString().trim();
                if(TextUtils.isEmpty(phone) || phone.length() <= 10){
                    editTextPhone.setError("Enter e valid phone no");
                }else if(TextUtils.isEmpty(pass)){
                    editTextpass.setError("Enter password");
                }else{
                    createuser(phone, pass);
                }

            }
        });




        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VarificationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void createuser(String phone, String pass) {

        HashMap<String,Object> userMap = new HashMap<>();
        userMap.put("phone", phone);
        userMap.put("pass", pass);

        userref.child(phone).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(VarificationActivity.this, "User Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VarificationActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    customType(VarificationActivity.this,"left-to-right");
                    finish();

                }
            }
        });
    }

}
