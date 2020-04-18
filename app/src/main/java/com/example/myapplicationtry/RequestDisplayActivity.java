package com.example.myapplicationtry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RequestDisplayActivity extends AppCompatActivity {
    DatabaseReference reference;
    ArrayList<Requests> list;
    MyAdapterRequests adapter;
    RecyclerView recyclerView;
    String personName;
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_display);
        recyclerView = (RecyclerView) findViewById(R.id.request_list);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        //taking user name
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if (acct != null) {
            personName = acct.getDisplayName();
        }
        //
        final String DeviceID = Settings.Secure.getString(this.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        reference = FirebaseDatabase.getInstance().getReference().child(personName+" deviceID "+DeviceID).child("Requests");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Requests>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Requests p = dataSnapshot1.getValue(Requests.class);
                    list.add(p);
                }
                adapter = new MyAdapterRequests(RequestDisplayActivity.this,list);
                recyclerView.setAdapter(adapter);
                if(list==null){
                    TextView textView = findViewById(R.id.empty_text);
                    ImageView imageView = findViewById(R.id.empty_image);
                    imageView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RequestDisplayActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
