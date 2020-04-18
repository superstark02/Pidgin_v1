package com.example.myapplicationtry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterChat extends RecyclerView.Adapter<MyAdapterChat.MyViewHolder> {

    Context context;
    ArrayList<Chat> chat;

    public MyAdapterChat(Context c , ArrayList<Chat> p)
    {
        context = c;
        chat = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyAdapterChat.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(chat.get(position).getUser()==null){
            holder.company.setText(""+chat.get(position).getCompany());
            holder.user.setVisibility(View.GONE);
        }
        else if(chat.get(position).getCompany()==null){
            holder.user.setText(""+chat.get(position).getUser());
            holder.company.setVisibility(View.GONE);
        }
        else {
            holder.company.setText(""+chat.get(position).getCompany());
            holder.user.setText(""+chat.get(position).getUser());
        }

    }

    @Override
    public int getItemCount() {
        return chat.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView user,company;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user = (TextView) itemView.findViewById(R.id.user);
            company = (TextView) itemView.findViewById(R.id.company);
        }
    }
}
