package com.example.findplasma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.findplasma.Adapter.UserViewHolder;
import com.example.findplasma.Models.UserInfo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<UserInfo> userInfos;
    private FirebaseRecyclerOptions<UserInfo> options;
    private FirebaseRecyclerAdapter<UserInfo, UserViewHolder> adapter;
    private DatabaseReference databaseReference;
    private String phoneNo;
    private Button becomeDonotBtn;

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

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("All Users");
        databaseReference.keepSynced(true);
        userInfos = new ArrayList<UserInfo>();

        phoneNo = getIntent().getStringExtra("phone");

        options = new FirebaseRecyclerOptions.Builder<UserInfo>().setQuery(databaseReference, UserInfo.class).build();

        adapter = new FirebaseRecyclerAdapter<UserInfo, UserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, int i, @NonNull UserInfo userInfo) {
                holder.nameCard.setText(userInfo.getName());
                holder.phoneCard.setText(userInfo.getPhone());
                holder.locationCard.setText(userInfo.getLocation());
                holder.treatmentCard.setText(userInfo.getTreatment());
                holder.bloodCard.setText(userInfo.getBloodgroup());
                Toast.makeText(HomeActivity.this, userInfo.getBloodgroup(), Toast.LENGTH_SHORT).show();
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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



        becomeDonotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.putExtra("phone", phoneNo);
                startActivity(intent);

            }
        });



    }


}
