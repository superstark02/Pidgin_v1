package com.example.myapplicationtry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    DatabaseReference reference,reference1;
    ImageButton imageButton;
    ConstraintLayout constraintLayout;
    Firebase myFirebase;
    CircleImageView user_image;
    SearchView home_search;
    RecyclerView recyclerView,recyclerView1;

    ArrayList<Profile> list;
    MyAdapter adapter;
    MyAdapterContent content;
    ArrayList<HomeImages> list1;

    TextView title;
    Button show_selected_schools;
    LinearLayout demo;
    private LocationListener listener;
    BottomNavigationView bottomNavigation;
    LinearLayoutManager HorizontalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        demo = findViewById(R.id.demo);
        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);
        recyclerView1 = (RecyclerView) findViewById(R.id.images);
        imageButton = (ImageButton) findViewById(R.id.form_image_button);
        constraintLayout = (ConstraintLayout) findViewById(R.id.common_form_button);
        title = (TextView) findViewById(R.id.title);
        home_search = (SearchView) findViewById(R.id.home_search);
        user_image = (CircleImageView) findViewById(R.id.user_image);
        show_selected_schools = (Button) findViewById(R.id.show_selected_schools);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));

        HorizontalLayout
                = new LinearLayoutManager(
                MainActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView1.setLayoutManager(HorizontalLayout);


        //visible
        demo.setVisibility(View.VISIBLE); show_selected_schools.setVisibility(View.VISIBLE);
        //

        //taking user name//
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String personName = "Not sign in";
        if (acct != null) {
            personName = acct.getDisplayName();
        }
        title.setText(personName);
        Uri personImage = acct.getPhotoUrl();
        Glide.with(this).load(personImage).into(user_image);
        //
        //Login status and form number 0 and chat number 0.
        Firebase.setAndroidContext(this);
        String DeviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        myFirebase = new Firebase("https://pidgin-ds.firebaseio.com/" + personName + " deviceID " + DeviceID);
        final Firebase firebase = new Firebase("https://pidgin-ds.firebaseio.com/" + personName + " deviceID " + DeviceID + "/Form Number");
        firebase.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    firebase.setValue(0);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        myFirebase = new Firebase("https://pidgin-ds.firebaseio.com/" + personName + " deviceID " + DeviceID);
        final Firebase firebase1 = new Firebase("https://pidgin-ds.firebaseio.com/" + personName + " deviceID " + DeviceID + "/Chat Number");
        firebase.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    firebase1.setValue(0);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Firebase myNewChild = myFirebase.child("Status");
        myNewChild.setValue("Logged In");
        //

        //bottom navigation
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        //

        reference = FirebaseDatabase.getInstance().getReference().child("Profiles");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Profile>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Profile p = dataSnapshot1.getValue(Profile.class);
                    list.add(p);
                }
                adapter = new MyAdapter(MainActivity.this, list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        if (home_search != null) {
            home_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    demo.setVisibility(View.VISIBLE); show_selected_schools.setVisibility(View.VISIBLE);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    demo.setVisibility(View.GONE);  show_selected_schools.setVisibility(View.GONE);
                    search(newText);
                    demo.setVisibility(View.VISIBLE); show_selected_schools.setVisibility(View.VISIBLE);
                    return true;
                }
            });
        }

        reference1 = FirebaseDatabase.getInstance().getReference().child("Images").child("Home");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list1 = new ArrayList<HomeImages>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    HomeImages p = dataSnapshot1.getValue(HomeImages.class);
                    list1.add(p);
                }
               content = new MyAdapterContent(MainActivity.this, list1);
                recyclerView1.setAdapter(content);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //visible
        demo.setVisibility(View.VISIBLE); show_selected_schools.setVisibility(View.VISIBLE);
        //

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Form.class);
                startActivity(intent);

            }
        });

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Form.class);
                startActivity(intent);
            }
        });

        show_selected_schools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ShowSelectedSchoolsActivity.class);
                startActivity(intent);
            }
        });

    }


    private void search(String str) {
        ArrayList<Profile> myList = new ArrayList<>();
        for (Profile object : list) {
            if (object.getName().toLowerCase().contains(str.toLowerCase()) || object.getEmail().toLowerCase().contains(str.toLowerCase())) {
                myList.add(object);
                MyAdapter myAdapter = new MyAdapter(MainActivity.this, myList);
                recyclerView.setAdapter(myAdapter);
            }
        }
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.home:
                            Intent myIntent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(myIntent);
                            return true;
                        case R.id.classes:
                            openFragment(ClassesFragment.newInstance("", ""));
                            return true;
                        case R.id.help:
                            openFragment(ProfileFragment.newInstance("", ""));
                            return true;
                    }
                    return false;
                }
            };


}