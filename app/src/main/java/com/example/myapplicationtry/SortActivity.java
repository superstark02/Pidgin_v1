package com.example.myapplicationtry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SortActivity extends AppCompatActivity {
    DatabaseReference reference;
    ArrayList<Classes> list;
    MyAdapterSort adapter;
    RecyclerView recyclerView;
    String keyword;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        recyclerView = findViewById(R.id.sorted);
        textView = findViewById(R.id.keyword);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        keyword = getIntent().getStringExtra("keyword");
        textView.setText(keyword);

        reference = FirebaseDatabase.getInstance().getReference().child("Classes");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Classes>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Classes p = dataSnapshot1.getValue(Classes.class);
                    list.add(p);
                }
                adapter = new MyAdapterSort(SortActivity.this,list,keyword);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SortActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
