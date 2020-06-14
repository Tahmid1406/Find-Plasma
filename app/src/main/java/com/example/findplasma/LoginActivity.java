package com.example.findplasma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findplasma.Models.User;
import com.example.findplasma.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.PrivateKey;

import io.paperdb.Paper;

import static com.google.firebase.database.FirebaseDatabase.getInstance;
import static maes.tech.intentanim.CustomIntent.customType;

public class LoginActivity extends AppCompatActivity {

    private EditText loginPhone, loginPass;
    private Button loginButton;
    private TextView createText;
    private CheckBox checkBox;
    private SharedPreferences mPrefs;
    private static final String Prefs_phone = "phone";
    private DatabaseReference rootRef;
    private String phonenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPhone = findViewById(R.id.loginPhone);
        loginPass = findViewById(R.id.loginPass);
        loginButton = findViewById(R.id.loginButton);
        createText = findViewById(R.id.createText);
        checkBox = findViewById(R.id.checkBox);
        Paper.init(this);

        //for shared preferrences
        mPrefs = getSharedPreferences(Prefs_phone, MODE_PRIVATE);
        bindWidget();

        createText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, VarificationActivity.class);
                startActivity(intent);
            }
        });

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


        getPrefData();
    }

    private void getPrefData() {
        SharedPreferences sp = getSharedPreferences(Prefs_phone, MODE_PRIVATE);
        if(sp.contains("pref_phone")){
            String u = sp.getString("pref_phone", "Not Found");
            loginPhone.setText(u.toString());
        }

        if(sp.contains("Pref_passs")){
            String p = sp.getString("Pref_passs", "Not found");
            loginPass.setText(p.toString());
        }

        if(sp.contains("Pref_checked")){
            Boolean b = sp.getBoolean("Pref_checked", false);
            checkBox.setChecked(b);
        }
    }



    private void bindWidget() {
        loginPhone = findViewById(R.id.loginPhone);
        loginPass = findViewById(R.id.loginPass);
        loginButton = findViewById(R.id.loginButton);
        createText = findViewById(R.id.createText);
        checkBox = findViewById(R.id.checkBox);


    }


    private void accountAccess(final String phone, final String pass) {

        if(checkBox.isChecked()){
            Boolean boolIsChecked = checkBox.isChecked();
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString("pref_phone", loginPhone.getText().toString().trim());
            editor.putString("Pref_passs", loginPass.getText().toString().trim());
            editor.putBoolean("Pref_checked", boolIsChecked);
            editor.apply();
            Toast.makeText(this, "Login saved to this device", Toast.LENGTH_SHORT).show();
        }else{
            mPrefs.edit().clear().apply();
        }

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(phone).exists()){
                    User userData = dataSnapshot.child("Users").child(phone).getValue(User.class);
                    if(userData.getPhone().equals(phone)) {
                        if(userData.getPass().equals(pass)){
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            phonenumber = loginPhone.getText().toString();
                            intent.putExtra("phone", phonenumber);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            customType(LoginActivity.this,"left-to-right");
                            finish();
                            loginPass.getText().clear();
                            loginPhone.getText().clear();

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
