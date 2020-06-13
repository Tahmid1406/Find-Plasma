package com.example.findplasma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findplasma.Models.User;
import com.example.findplasma.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class LoginActivity extends AppCompatActivity {

    private EditText loginPhone, loginPass;
    private Button loginButton;
    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPhone = findViewById(R.id.loginPhone);
        loginPass = findViewById(R.id.loginPass);
        loginButton = findViewById(R.id.loginButton);

        rootRef = FirebaseDatabase.getInstance().getReference();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = loginPhone.getText().toString().trim();
                String pass = loginPass.getText().toString().trim();
                if(TextUtils.isEmpty(phone)){
                    loginPhone.setError("Enter Phone No.");
                }else if(TextUtils.isEmpty(pass)){
                    loginPass.setError("Enter Password");
                }else{
                    accountAccess(phone,pass);
                    //Toast.makeText(LoginActivity.this, phone + pass, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void accountAccess(final String phone, final String pass) {

        //Paper.book().write(Prevalent.UserPhoneKey,phone);
        //Paper.book().write(Prevalent.UserPasswordKey,pass);

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(phone).exists()){
                    User userData = dataSnapshot.child("Users").child(phone).getValue(User.class);
                    if(userData.getPhone().equals(phone)) {
                        if(userData.getPass().equals(pass)){
                            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                            startActivity(intent);

                            Toast.makeText(LoginActivity.this, "Exist", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(LoginActivity.this, "Account Does not Exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }





}
