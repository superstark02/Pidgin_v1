package com.example.myapplicationtry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClassesDisplayActivity extends AppCompatActivity {
    TextView name,fees,classes,distance,title,check;
    int form_fees,form_number,position;
    String personName,schoolName;
    ImageView classes_display_image;
    ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes_display);
        name = findViewById(R.id.classes_display_name);
        title = findViewById(R.id.classes_title);
        fees = findViewById(R.id.classes_display_fees);
        toggleButton = findViewById(R.id.request);
        classes_display_image = findViewById(R.id.classes_display_image);
        name.setText(getIntent().getStringExtra("classes_name"));
        title.setText(getIntent().getStringExtra("classes_name"));
        fees.setText(getIntent().getStringExtra("classes_fees"));

        schoolName = getIntent().getStringExtra("classes_name");

        Picasso.get().load(getIntent().getStringExtra("classes_image")).into(classes_display_image);

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
        Firebase setSchool = new Firebase("https://pidgin-ds.firebaseio.com/"+personName+" deviceID "+DeviceID+"/Requests");
        setSchool.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    toggleButton.setChecked(true);
                }
                else {
                    toggleButton.setChecked(false);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dt = new SimpleDateFormat("HH:mm");
        final String formattedDate = df.format(c.getTime());
        final String formattedTime = dt.format(c.getTime());

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Firebase set_school = new Firebase("https://pidgin-ds.firebaseio.com/"+personName+" deviceID "+DeviceID+"/Requests");
                Firebase firebase  = set_school.child(schoolName).child("name");
                Firebase image = set_school.child(schoolName).child("profilePic");
                Firebase date = set_school.child(schoolName).child("date");
                Firebase time = set_school.child(schoolName).child("time");
                if(isChecked){
                    //put name and fees to firebase
                    firebase.setValue(schoolName);
                    image.setValue(getIntent().getStringExtra("classes_image"));
                    date.setValue(formattedDate);
                    time.setValue(formattedTime);
                }
                else{
                    firebase.removeValue();
                    image.removeValue();
                    date.removeValue();
                    time.removeValue();
                }
            }
        });

    }
}
