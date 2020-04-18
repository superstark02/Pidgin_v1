package com.example.myapplicationtry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HelpActivity extends AppCompatActivity {
    DatabaseReference reference;
    ArrayList<Chat> list;
    MyAdapterChat adapter;
    RecyclerView recyclerView;
    String personName;

    ImageButton imageButton;
    EditText editText;

    Firebase firebase;
    int chatnumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        recyclerView = (RecyclerView) findViewById(R.id.chat);
        editText = (EditText) findViewById(R.id.meassage_input);
        imageButton = (ImageButton) findViewById(R.id.send_message);
        recyclerView.setLayoutManager( new LinearLayoutManager(HelpActivity.this));

        Firebase.setAndroidContext(this);

        //taking user name
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if (acct != null) {
            personName = acct.getDisplayName();
        }
        //

        final String DeviceID = Settings.Secure.getString(this.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        //setting chat number
        Firebase getFormNumber = new Firebase("https://pidgin-ds.firebaseio.com/" + personName + " deviceID " + DeviceID + "/Chat Number");
        getFormNumber.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                chatnumber = dataSnapshot.getValue(int.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        //
        reference = FirebaseDatabase.getInstance().getReference().child(personName+" deviceID "+DeviceID).child("Chat");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Chat>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Chat p = dataSnapshot1.getValue(Chat.class);
                    list.add(p);
                }
                adapter = new MyAdapterChat(HelpActivity.this,list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HelpActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase = new Firebase("https://pidgin-ds.firebaseio.com/" + personName + " deviceID " + DeviceID +"/Chat/Chat "+ chatnumber);
                Firebase user = firebase.child("user");
                Firebase chat_number = new Firebase("https://pidgin-ds.firebaseio.com/" + personName + " deviceID " + DeviceID +"/Chat Number");
                user.setValue(editText.getText().toString());
                editText.setText("");
                chatnumber++;
                chat_number.setValue(chatnumber);
            }
        });

    }
}
