package com.example.myapplicationtry;

import android.content.Context;
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

public class MyAdapterRequests extends RecyclerView.Adapter<MyAdapterRequests.MyViewHolder> {

    Context context;
    ArrayList<Requests> requests;

    public MyAdapterRequests(Context c , ArrayList<Requests> p)
    {
        context = c;
        requests = p;
    }

    @NonNull
    @Override
    public MyAdapterRequests.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyAdapterRequests.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.request_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterRequests.MyViewHolder holder, int position) {
        holder.name.setText(requests.get(position).getName());
        holder.date.setText(requests.get(position).getDate());
        holder.time.setText(requests.get(position).getTime());
        Picasso.get().load(requests.get(position).getProfilePic()).into(holder.profilePic);
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,date,time;
        CircleImageView profilePic;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.class_name);
            date = (TextView) itemView.findViewById(R.id.request_date);
            time = (TextView) itemView.findViewById(R.id.request_time);
            profilePic = (CircleImageView) itemView.findViewById(R.id.circleImageView);
        }
    }

}
