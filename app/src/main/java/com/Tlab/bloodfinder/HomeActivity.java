package com.Tlab.bloodfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Tlab.bloodfinder.Adapter.UserViewHolder;
import com.Tlab.bloodfinder.Models.UserInfo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import static maes.tech.intentanim.CustomIntent.customType;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<UserInfo> userInfos;
    private EditText searchBar;
    private FirebaseRecyclerOptions<UserInfo> options;
    private FirebaseRecyclerAdapter<UserInfo, UserViewHolder> adapter;
    private DatabaseReference databaseReference;
    private String phoneNo;
    private Button becomeDonotBtn;
    private static final int REQUEST_CALL  = 1;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();


    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        becomeDonotBtn = findViewById(R.id.becomeDonotBtn);
        searchBar = findViewById(R.id.searchBar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("All Users");
        databaseReference.keepSynced(true);
        userInfos = new ArrayList<UserInfo>();

        phoneNo = getIntent().getStringExtra("phone");


        becomeDonotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.putExtra("phone", phoneNo);
                startActivity(intent);
                customType(HomeActivity.this,"left-to-right");

            }
        });


        loadData("");

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null) {
                    loadData(s.toString());
                } else {
                    loadData("");
                }
            }
        });


    }

    private void loadData(String data) {

        Query query = databaseReference.orderByChild("bloodgroup").startAt(data).endAt(data + "\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<UserInfo>().setQuery(query, UserInfo.class).build();

        adapter = new FirebaseRecyclerAdapter<UserInfo, UserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final UserViewHolder holder, int i, @NonNull final UserInfo userInfo) {
                holder.nameCard.setText(userInfo.getName());
                holder.phoneCard.setText(userInfo.getPhone());
                holder.locationCard.setText(userInfo.getLocation());
                holder.treatmentCard.setText(userInfo.getTreatment());
                holder.bloodCard.setText(userInfo.getBloodgroup());


                //click Listener for call button inside cardviews
                holder.itemView.findViewById(R.id.callBtn).setOnClickListener(new View.OnClickListener() {

                    @SuppressLint("MissingPermission")
                    @Override
                    public void onClick(View v) {
                        String pno = userInfo.getPhone().trim();
                        String dial = "tel:+88" + pno;
                        Toast.makeText(HomeActivity.this, dial, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                    }
                });


                //click listener for text button inside cardviews
                holder.itemView.findViewById(R.id.textBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pno = userInfo.getPhone().trim();
                        String dial = "+88" + pno;
                        Uri uri = Uri.parse("smsto:" + dial);
                        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                        intent.putExtra("sms_body", "Urgent blood plasma of " + userInfo.getBloodgroup() + " group is needed." +
                                " Please respond at " + phoneNo);
                        startActivity(intent);
                    }
                });


            }

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                return new UserViewHolder(LayoutInflater.from(HomeActivity.this).inflate(R.layout.card_holder,viewGroup,false));
            }
        };



        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

}
