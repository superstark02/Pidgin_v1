package com.example.myapplicationtry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BillActivity extends AppCompatActivity {

    DatabaseReference reference;
    ArrayList<SelectedSchools> list;
    MyAdapterBill adapter;
    RecyclerView recyclerView;
    String personName;
    int form_number,total;
    TextView amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        recyclerView = findViewById(R.id.schools);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        amount = findViewById(R.id.amount);
        final String DeviceID = Settings.Secure.getString(this.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Firebase.setAndroidContext(this);
        //taking user name
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if (acct != null) {
            personName = acct.getDisplayName();
        }
        //

        reference = FirebaseDatabase.getInstance().getReference().child(personName+" deviceID "+DeviceID).child("Schools Profile "+form_number);

        reference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                list = new ArrayList<SelectedSchools>();
                for (com.google.firebase.database.DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    SelectedSchools p = dataSnapshot1.getValue(SelectedSchools.class);
                    list.add(p);
                }
                adapter = new MyAdapterBill(list,BillActivity.this);
                recyclerView.setAdapter(adapter);

                adapter.setOnItemClickListener(new MyAdapterBill.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }

                    @Override
                    public void onDeleteClick(int position) {

                    }
                });

                total = 0;
                if(list!=null) {
                    for (int i = 0; i < list.size(); i++) {
                        total = total + list.get(i).getFees();
                    }
                    amount.setText("₹" + total);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BillActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
