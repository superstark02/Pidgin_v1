package com.example.myapplicationtry;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapterBill extends RecyclerView.Adapter<MyAdapterBill.MyViewHolder>{
    private ArrayList<SelectedSchools> mExampleList;
    private OnItemClickListener mListener;
    Context context; int form_number = 0;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public MyAdapterBill.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_schools_item, parent, false);
        MyViewHolder evh = new MyViewHolder(v, (OnItemClickListener) mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterBill.MyViewHolder holder, int position) {
            holder.name.setText(mExampleList.get(position).getName());
            holder.fees.setText("₹"+mExampleList.get(position).getFees());
            holder.classes.setText(mExampleList.get(position).getClasses());
            Picasso.get().load(mExampleList.get(position).getProfilePic()).into(holder.circleImageView);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView name,fees,classes;
        ImageView delete;
        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
                circleImageView = (CircleImageView) itemView.findViewById(R.id.circleImageView2);
                name = (TextView) itemView.findViewById(R.id.school_name);
                fees = (TextView) itemView.findViewById(R.id.fees);
                classes = (TextView) itemView.findViewById(R.id.classes);
                delete = (ImageView) itemView.findViewById(R.id.delete);


                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onDeleteClick(position);

                                /*String personName = "Not sign in";
                                Firebase.setAndroidContext(context);
                                //taking user name
                                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);

                                if (acct != null) {
                                    personName = acct.getDisplayName();
                                }
                                //
                                final String DeviceID = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

                                //setting form number
                                Firebase getFormNumber = new Firebase("https://pidgin-ds.firebaseio.com/" + personName + " deviceID " + DeviceID + "/Form Number");
                                getFormNumber.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        form_number = dataSnapshot.getValue(int.class);
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                    }
                                });

                                Firebase remove = new Firebase("https://pidgin-ds.firebaseio.com/" + personName + " deviceID " + DeviceID + "/Schools Profile "+form_number+"/"+mExampleList.get(position).getName());
                                remove.child("name").removeValue();
                                remove.child("classes").removeValue();
                                remove.child("fees").removeValue();
                                remove.child("profilePic").removeValue()*/;
                            }
                        }
                    }
                });
        }
    }

    public MyAdapterBill(ArrayList<SelectedSchools> exampleList, Context c) {
        mExampleList = exampleList;
        this.context = c;
    }
}
