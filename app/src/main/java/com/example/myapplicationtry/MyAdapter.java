package com.example.myapplicationtry;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.protobuf.StringValue;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Profile> profiles;

    public MyAdapter(Context c , ArrayList<Profile> p)
    {
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.name.setText(profiles.get(position).getName());
        holder.email.setText("Classes "+profiles.get(position).getEmail());
        holder.fees.setText("Form Fees ₹" + profiles.get(position).getFees());
        Picasso.get().load(profiles.get(position).getProfilePic()).into(holder.profilePic);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SchoolDisplay.class);
                Bundle bundle = new Bundle();
                intent.putExtra("url",profiles.get(position).getUrl());
                intent.putExtra("image",profiles.get(position).getProfilePic());
                intent.putExtra("distance",profiles.get(position).getX());
                intent.putExtra("fees",profiles.get(position).getFees());
                intent.putExtra("name",profiles.get(position).getName());
                intent.putExtra("class",profiles.get(position).getEmail());
                intent.putExtra("profilePic",profiles.get(position).getProfilePic());
                intent.putExtra("distance",profiles.get(position).getDistance());
                intent.putExtra("image1",profiles.get(position).getImage1());
                intent.putExtra("image1",profiles.get(position).getImage2());
                intent.putExtra("image1",profiles.get(position).getImage3());
                intent.putExtra("image1",profiles.get(position).getImage4());
                intent.putExtra("image1",profiles.get(position).getImage5());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,email,fees;
        CircleImageView profilePic;
        ConstraintLayout constraintLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            email = (TextView) itemView.findViewById(R.id.classes);
            profilePic = (CircleImageView) itemView.findViewById(R.id.profilePic);
            fees = (TextView) itemView.findViewById(R.id.fees);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.item);
        }
    }
}
