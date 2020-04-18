package com.example.myapplicationtry;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;

public class SchoolDisplay extends AppCompatActivity {

    TextView name,fees,classes,distance,title,check;
    ImageView profilePic;
    int form_fees,form_number,position;
    String personName,schoolName,url,schoolImage,classes_string;
    ToggleButton toggleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_display);
        profilePic = (ImageView) findViewById(R.id.school_display_image);
        distance = findViewById(R.id.distance);
        name = findViewById(R.id.school_display_name);
        check = findViewById(R.id.check);
        title = findViewById(R.id.title);
        fees = findViewById(R.id.school_display_fees);
        toggleButton = findViewById(R.id.select_school);
        title.setText(getIntent().getStringExtra("name"));
        name.setText(getIntent().getStringExtra("name"));
        classes = findViewById(R.id.school_display_classes);
        classes.setText(getIntent().getStringExtra("class"));
        schoolImage = getIntent().getStringExtra("image");
        classes_string = getIntent().getStringExtra("class");

        Picasso.get().load(getIntent().getStringExtra("image")).into(profilePic);

        schoolName = getIntent().getStringExtra("name");
        form_fees = getIntent().getIntExtra("fees",0);
        fees.setText("Form Fees ₹" + form_fees);
        distance.setText(getIntent().getDoubleExtra("distance",0)+"km");

        url = getIntent().getStringExtra("url");
        //taking user name
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        personName = "Not sign in";
        if(acct != null){
            personName = acct.getDisplayName();
        }
        //

        //
        Firebase.setAndroidContext(this);
        final String DeviceID = Settings.Secure.getString(this.getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        //setting form number
        Firebase getFormNumber = new Firebase("https://pidgin-ds.firebaseio.com/"+personName+" deviceID "+DeviceID+"/Form Number");
        getFormNumber.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                form_number = dataSnapshot.getValue(int.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        //
        //toggle button
        Firebase setSchool = new Firebase("https://pidgin-ds.firebaseio.com/"+personName+" deviceID "+DeviceID+"/Schools for "+form_number+"/"+schoolName);
        setSchool.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    toggleButton.setChecked(true);
                    check.setVisibility(View.VISIBLE);
                }
                else {
                    toggleButton.setChecked(false);
                    check.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Firebase set_school = new Firebase("https://pidgin-ds.firebaseio.com/"+personName+" deviceID "+DeviceID+"/Schools Profile "+form_number);
                Firebase firebase  = set_school.child(schoolName).child("name");
                Firebase firebase_fees  = set_school.child(schoolName).child("fees");
                Firebase set_classes = set_school.child(schoolName).child("classes");
                Firebase set_url = new Firebase("https://pidgin-ds.firebaseio.com/"+personName+" deviceID "+DeviceID+"/Schools url for "+form_number);
                Firebase firebase_url = set_url.child(schoolName);
                Firebase set_image = set_school.child(schoolName).child("profilePic");
                if(isChecked){
                    //put name and fees to firebase
                    firebase.setValue(schoolName);
                    firebase_fees.setValue(form_fees);
                    firebase_url.setValue(url);
                    set_image.setValue(schoolImage);
                    set_classes.setValue(classes_string);
                }
                else{
                    firebase.removeValue();
                    firebase_fees.removeValue();
                    firebase_url.removeValue();
                    set_image.removeValue();
                    set_classes.removeValue();
                    check.setVisibility(View.GONE);
                }
            }
        });
        //

    }
}
