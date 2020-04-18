package com.example.myapplicationtry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UploadActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    ImageView photo;
    Button choose_photo,upload_photo;
    EditText photo_name;
    ProgressBar mProgressBar;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;
    int form_number;
    ArrayList<String> arrayList = new ArrayList<>();
    String personName;
    //form fields
    String full_name_string ;
    String father_name_string;
    String mother_name_string;
    String dd_string;
    String mm_string;
    String yyyy_string;
    String place_of_birth_string;
    String city_string;
    String state_string;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        photo = (ImageView) findViewById(R.id.passportsize_photo);
        choose_photo = (Button) findViewById(R.id.choose_photo);
        upload_photo = (Button) findViewById(R.id.submit);
        photo_name = (EditText) findViewById(R.id.photo_name);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);


        //taking user name
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        personName = "Not sign in";
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
        //

        //getting url list
        Firebase getUrl;
        getUrl = new Firebase("https://pidgin-ds.firebaseio.com/" + personName + " deviceID " + DeviceID + "/Schools url for "+form_number);
        getUrl.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String myChildValues = dataSnapshot.getValue(String.class);
                arrayList.add(myChildValues);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        //



        mStorageRef = FirebaseStorage.getInstance().getReference(personName+" deviceID "+DeviceID);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(personName+" deviceID "+DeviceID);

        choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });

        //getting data
        full_name_string = getIntent().getStringExtra("full_name");
        father_name_string = getIntent().getStringExtra("father_name");
        mother_name_string = getIntent().getStringExtra("mother_name");
        dd_string = getIntent().getStringExtra("date");
        mm_string = getIntent().getStringExtra("month");
        yyyy_string = getIntent().getStringExtra("year");
        place_of_birth_string = getIntent().getStringExtra("place_of_birth");
        city_string = getIntent().getStringExtra("city");
        state_string = getIntent().getStringExtra("state");
        //
        //sending data

        //

        upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(UploadActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadPhoto();
                }

                for(int i = 0; i<arrayList.size();i++) {
                    Firebase myFirebase = new Firebase(arrayList.get(i) + personName + " deviceID " + DeviceID + "/Form Submitted " + form_number);
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
                }

                form_number++;
                Firebase firebase = new Firebase("https://pidgin-ds.firebaseio.com/" + personName + " deviceID " + DeviceID + "/Form Number");
                firebase.setValue(form_number);
                Toast.makeText(UploadActivity.this, "Relax, Form Successfully Submitted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void choosePhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(photo);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadPhoto() {
        if (mImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(UploadActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                            Upload upload = new Upload(photo_name.getText().toString().trim(),
                                    fileReference.getDownloadUrl().toString());
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}
