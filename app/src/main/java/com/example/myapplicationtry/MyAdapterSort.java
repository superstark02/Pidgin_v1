package com.example.myapplicationtry;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapterSort extends RecyclerView.Adapter<MyAdapterSort.MyViewHolder>  {

    Context context;
    ArrayList<Classes> classes;
    String keyword;

    public MyAdapterSort(Context c , ArrayList<Classes> p,String k)
    {
        context = c;
        classes = p;
        keyword = k;
    }

    @NonNull
    @Override
    public MyAdapterSort.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyAdapterSort.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.classes_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterSort.MyViewHolder holder, final int position) {
        if(classes.get(position).getType().toLowerCase().contains(keyword)) {
            holder.name.setText(classes.get(position).getName());
            holder.email.setText(classes.get(position).getEmail());
            holder.fees.setText("Fees ₹" + classes.get(position).getFees());
            holder.type.setText(classes.get(position).getType());
            Picasso.get().load(classes.get(position).getProfilePic()).into(holder.profilePic);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,ClassesDisplayActivity.class);
                    intent.putExtra("classes_name",classes.get(position).getName());
                    intent.putExtra("classes_fees",classes.get(position).getFees());
                    intent.putExtra("classes_age",classes.get(position).getType());
                    intent.putExtra("classes_image",classes.get(position).getProfilePic());
                    context.startActivity(intent);
                }
            });
        }
        else {
            holder.cardView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,email,fees,type,rating;
        CircleImageView profilePic;
        ConstraintLayout cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            email = (TextView) itemView.findViewById(R.id.email);
            profilePic = (CircleImageView) itemView.findViewById(R.id.profilePic);
            fees = (TextView) itemView.findViewById(R.id.fees);
            type = (TextView) itemView.findViewById(R.id.type);
            cardView = (ConstraintLayout) itemView.findViewById(R.id.classes_card);
        }
    }
}
