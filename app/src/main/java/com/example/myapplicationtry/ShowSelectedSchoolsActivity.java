package com.example.myapplicationtry;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ShowSelectedSchoolsActivity extends AppCompatActivity {

    DatabaseReference reference;
    ArrayList<SelectedSchools> list;
    MyAdapterBill adapter;
    RecyclerView recyclerView;
    String schoolName;
    int form_number;
    int remove,position,total;
    TextView amount;
    ConstraintLayout empty;
    LinearLayout linearLayout;

    String personName = "Not sign in";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_selected_schools);
        recyclerView = findViewById(R.id.schools);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        amount = findViewById(R.id.amount);
        empty = (ConstraintLayout) findViewById(R.id.empty);
        linearLayout = (LinearLayout) findViewById(R.id.empty_layout);

        schoolName = getIntent().getStringExtra("schoolName");
        position = getIntent().getIntExtra("position",0);

        Firebase.setAndroidContext(this);
        //taking user name
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if (acct != null) {
            personName = acct.getDisplayName();
        }
        //
        final String DeviceID = Settings.Secure.getString(this.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
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
        reference = FirebaseDatabase.getInstance().getReference().child(personName+" deviceID "+DeviceID).child("Schools Profile "+form_number);

        reference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                list = new ArrayList<SelectedSchools>();
                for (com.google.firebase.database.DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    SelectedSchools p = dataSnapshot1.getValue(SelectedSchools.class);
                    list.add(p);
                }
                adapter = new MyAdapterBill(list,ShowSelectedSchoolsActivity.this);

                recyclerView.setAdapter(adapter);
                total = 0;
                if(list!=null) {
                    for (int i = 0; i < list.size(); i++) {
                        total = total + list.get(i).getFees();
                    }
                    amount.setText("₹" + total);
                }
                if(list.size()==0){
                    empty.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                }

                adapter.setOnItemClickListener(new MyAdapterBill.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }

                    @Override
                    public void onDeleteClick(int position) {
                        Firebase remove = new Firebase("https://pidgin-ds.firebaseio.com/" + personName + " deviceID " + DeviceID + "/Schools Profile "+form_number+"/"+list.get(position).getName());
                        remove.child("name").removeValue();
                        remove.child("classes").removeValue();
                        remove.child("fees").removeValue();
                        remove.child("profilePic").removeValue();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ShowSelectedSchoolsActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void removeItem(int position){
        list.remove(position);
    }

}
