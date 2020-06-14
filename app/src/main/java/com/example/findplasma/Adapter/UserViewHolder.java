package com.example.findplasma.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findplasma.R;

public class UserViewHolder extends RecyclerView.ViewHolder {
    public TextView nameCard ,phoneCard ,locationCard , treatmentCard,bloodCard;

    public Button callBtn  ,textBtn;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        nameCard = itemView.findViewById(R.id.nameCard);
        phoneCard = itemView.findViewById(R.id.phoneCard);
        locationCard = itemView.findViewById(R.id.locationCard);
        treatmentCard = itemView.findViewById(R.id.treatmentCard);
        bloodCard = itemView.findViewById(R.id.bloodCard);

        callBtn = itemView.findViewById(R.id.callBtn);
        textBtn = itemView.findViewById(R.id.textBtn);

    }
}
