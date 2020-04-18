package com.example.myapplicationtry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;

public class Form extends AppCompatActivity {

    TextView mTextViewShowUploads;
    String name_1;
    ProgressBar mProgressBar;
    int form_number;
    Button submit_form, submit_confirm;
    EditText full_name, father_name, mother_name, dd, mm, yyyy, place_of_birth, city, state,mEditTextFileName;
    ConstraintLayout form_scroll_view;
    ImageButton back_from_sheet, back_home;
    Context context = Form.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        ImageButton back_home = (ImageButton) findViewById(R.id.back_home_from_form);
        ConstraintLayout item1 = findViewById(R.id.item1);
        final TextView user_name_1 = findViewById(R.id.user_name_1);
        user_name_1.setText("Fill Form");
        mTextViewShowUploads = findViewById(R.id.gone2);

        mEditTextFileName = findViewById(R.id.gone);
        submit_form = findViewById(R.id.submit_form);
        form_scroll_view = findViewById(R.id.form_scroll_view);
        full_name = findViewById(R.id.full_name);
        father_name = findViewById(R.id.father_name);
        mother_name = findViewById(R.id.mother_name);
        place_of_birth = findViewById(R.id.place_of_birth);
        dd = findViewById(R.id.dob_day);
        mm = findViewById(R.id.dob_month);
        yyyy = findViewById(R.id.dob_year);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        submit_confirm = findViewById(R.id.confirm_submit);
        back_from_sheet = findViewById(R.id.back_from_sheet);
        back_home = findViewById(R.id.back_home_from_form);

        //taking user name
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String personName = "Not sign in";
        if (acct != null) {
            personName = acct.getDisplayName();
        }
        //

        Firebase.setAndroidContext(this);
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


        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                form_scroll_view.setVisibility(View.VISIBLE);

                //taking user name
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Form.this);
                String personName = "Not sign in";
                if (acct != null) {
                    personName = acct.getDisplayName();
                }
                //

                //taking data
                final Firebase getName = new Firebase("https://pidgin-ds.firebaseio.com/" + personName + " deviceID " + DeviceID + "/Form/Full Name");
                getName.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            full_name.setText(dataSnapshot.getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                final Firebase getFatherName = new Firebase("https://pidgin-ds.firebaseio.com/" + personName + " deviceID " + DeviceID + "/Form/Father Name");
                getFatherName.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            father_name.setText(dataSnapshot.getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                final Firebase getMotherName = new Firebase("https://pidgin-ds.firebaseio.com/" + personName + " deviceID " + DeviceID + "/Form/Mother Name");
                getMotherName.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            mother_name.setText(dataSnapshot.getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                final Firebase getdd = new Firebase("https://pidgin-ds.firebaseio.com/" + personName + " deviceID " + DeviceID + "/Form/Date");
                getdd.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            dd.setText(dataSnapshot.getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                final Firebase getmm = new Firebase("https://pidgin-ds.firebaseio.com/" + personName + " deviceID " + DeviceID + "/Form/Month");
                getmm.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            mm.setText(dataSnapshot.getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                final Firebase getyyyy = new Firebase("https://pidgin-ds.firebaseio.com/" + personName + " deviceID " + DeviceID + "/Form/Year");
                getyyyy.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            yyyy.setText(dataSnapshot.getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                final Firebase getpob = new Firebase("https://pidgin-ds.firebaseio.com/" + personName + " deviceID " + DeviceID + "/Form/Place Of Birth");
                getpob.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            place_of_birth.setText(dataSnapshot.getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                final Firebase getCity = new Firebase("https://pidgin-ds.firebaseio.com/" + personName + " deviceID " + DeviceID + "/Form/City");
                getCity.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            city.setText(dataSnapshot.getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                //

                submit_form.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //taking user name
                        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Form.this);
                        String personName = "Not sign in";
                        if (acct != null) {
                            personName = acct.getDisplayName();
                        }
                        //

                        String full_name_string = full_name.getText().toString();
                        String father_name_string = father_name.getText().toString();
                        String mother_name_string = mother_name.getText().toString();
                        String dd_string = dd.getText().toString();
                        String mm_string = mm.getText().toString();
                        String yyyy_string = yyyy.getText().toString();
                        String place_of_birth_string = place_of_birth.getText().toString();
                        String city_string = city.getText().toString();
                        String state_string = state.getText().toString();

                        Firebase myFirebase = new Firebase("https://pidgin-ds.firebaseio.com/" + personName + " deviceID " + DeviceID + "/Form");
                        Firebase full_name = myFirebase.child("Full Name");
                        Firebase father_name = myFirebase.child("Father Name");
                        Firebase mother_name = myFirebase.child("Mother Name");
                        Firebase dd = myFirebase.child("Date");
                        Firebase mm = myFirebase.child("Month");
                        Firebase yyyy = myFirebase.child("Year");
                        Firebase place_of_birth = myFirebase.child("Place Of Birth");
                        Firebase city = myFirebase.child("City");
                        Firebase state = myFirebase.child("State");

                        full_name.setValue(full_name_string, 1);
                        father_name.setValue(father_name_string, 2);
                        mother_name.setValue(mother_name_string, 3);
                        dd.setValue(dd_string, 4);
                        mm.setValue(mm_string, 5);
                        yyyy.setValue(yyyy_string, 6);
                        place_of_birth.setValue(place_of_birth_string, 7);
                        city.setValue(city_string, 8);
                        state.setValue(state_string, 9);

                        Toast.makeText(Form.this, "Form Saved", Toast.LENGTH_SHORT).show();
                    }
                });

                submit_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String full_name_string = full_name.getText().toString();
                        String father_name_string = father_name.getText().toString();
                        String mother_name_string = mother_name.getText().toString();
                        String dd_string = dd.getText().toString();
                        String mm_string = mm.getText().toString();
                        String yyyy_string = yyyy.getText().toString();
                        String place_of_birth_string = place_of_birth.getText().toString();
                        String city_string = city.getText().toString();
                        String state_string = state.getText().toString();

                        if (full_name_string.isEmpty()) {
                            Toast.makeText(Form.this, "Please fill Full Name", Toast.LENGTH_SHORT).show();
                        } else if (father_name_string.isEmpty()) {
                            Toast.makeText(Form.this, "Please fill Father Name", Toast.LENGTH_SHORT).show();
                        } else if (mother_name_string.isEmpty()) {
                            Toast.makeText(Form.this, "Please fill Mother Name", Toast.LENGTH_SHORT).show();
                        } else if (dd_string.isEmpty()) {
                            Toast.makeText(Form.this, "Please fill Date of Birth", Toast.LENGTH_SHORT).show();
                        } else if (mm_string.isEmpty()) {
                            Toast.makeText(Form.this, "Please fill Date of Birth", Toast.LENGTH_SHORT).show();
                        } else if (yyyy_string.isEmpty()) {
                            Toast.makeText(Form.this, "Please fill Date of Birth", Toast.LENGTH_SHORT).show();
                        } else if (place_of_birth_string.isEmpty()) {
                            Toast.makeText(Form.this, "Please fill Place of Birth", Toast.LENGTH_SHORT).show();
                        } else if (city_string.isEmpty()) {
                            Toast.makeText(Form.this, "Please fill City", Toast.LENGTH_SHORT).show();
                        } else if (state_string.isEmpty()) {
                            Toast.makeText(Form.this, "Please fill State", Toast.LENGTH_SHORT).show();
                        } else {
                            //taking user name
                            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Form.this);
                            String personName = "Not sign in";
                            if (acct != null) {
                                personName = acct.getDisplayName();
                            }
                            //
                            Intent intent1 = new Intent(context,UploadActivity.class);
                            intent1.putExtra("full_name",full_name_string);
                            intent1.putExtra("father_name",father_name_string);
                            intent1.putExtra("mother_name",mother_name_string);
                            intent1.putExtra("date",dd_string);
                            intent1.putExtra("month",mm_string);
                            intent1.putExtra("year",yyyy_string);
                            intent1.putExtra("place_of_birth",place_of_birth_string);
                            intent1.putExtra("city",city_string);
                            intent1.putExtra("state",state_string);

                            startActivity(intent1);
                        }
                    }

                });

            }
        });

    }

}
