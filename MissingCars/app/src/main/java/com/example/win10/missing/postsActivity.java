package com.example.win10.missing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class postsActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ImageButton carbtn;
    private EditText placeText;
    private EditText colorText;
    private EditText numberText;
    private EditText DescriptionText;
    private FloatingActionButton btn;
    private Uri ImageUri;
    private ProgressDialog loadingbar;



    private String SaveCurrentDate, SaveCurrentTime, PostRandomName, downloadUrl, Current_user_id;
    private String place;
    private String color;
    private String number;
    private String description;

    private StorageReference postsref;
    private DatabaseReference userRef, postsrefrence;
    private FirebaseAuth mAuth;
    private final static int Gallery_Pick = 1;
    private ProgressDialog Loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        mToolbar = (Toolbar) findViewById(R.id.all_users_app_par);
        loadingbar = new ProgressDialog(this);


        mAuth = FirebaseAuth.getInstance();
        Current_user_id = mAuth.getCurrentUser().getUid();


        Loadingbar = new ProgressDialog(this);


        postsref = FirebaseStorage.getInstance().getReference();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        postsrefrence = FirebaseDatabase.getInstance().getReference().child("Posts");


        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("upload post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        carbtn = (ImageButton) findViewById(R.id.imageButton4);
        placeText = (EditText) findViewById(R.id.place);
        colorText = (EditText) findViewById(R.id.color);
        numberText = (EditText) findViewById(R.id.number);
        btn = (FloatingActionButton) findViewById(R.id.addbtn);
        DescriptionText = (EditText) findViewById(R.id.des);




        carbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        /*
        Saving the image in the storage
        */

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Validatepostinfo();


            }
        });


    }

    private void Validatepostinfo() {
        place = placeText.getText().toString();
        color = colorText.getText().toString();
        number = numberText.getText().toString();
        description = DescriptionText.getText().toString();

        if (ImageUri == null) {
            Toast.makeText(postsActivity.this, " Please select an image", Toast.LENGTH_LONG).show();

        }
      else  if (TextUtils.isEmpty(color)) {
            Toast.makeText(postsActivity.this, " Please Enter color", Toast.LENGTH_LONG).show();

        }
      else  if (TextUtils.isEmpty(number)) {
            Toast.makeText(postsActivity.this, " Please Enter number", Toast.LENGTH_LONG).show();

        }

       else if (TextUtils.isEmpty(place)) {
            Toast.makeText(postsActivity.this, " Please Enter Place", Toast.LENGTH_LONG).show();

        }
       else  if (TextUtils.isEmpty(description)) {
            Toast.makeText(postsActivity.this, " Please Enter description", Toast.LENGTH_LONG).show();

        } else {
            Loadingbar.setTitle("Add new post");
            Loadingbar.setMessage("uploading new post please wait...");
            Loadingbar.show();
            Loadingbar.setCanceledOnTouchOutside(true);

            StoringImageToFireBaseStorage();
        }


    }

    private void StoringImageToFireBaseStorage() {
        Calendar calenderForDate = Calendar.getInstance();
        Calendar calendarForTime = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-YYYY");
        SaveCurrentDate = currentDate.format(calenderForDate.getTime());

        Calendar calenderForTime = Calendar.getInstance();

        SimpleDateFormat currentTime = new SimpleDateFormat("H-mm-ss");
        SaveCurrentTime = currentTime.format(calendarForTime.getTime());

        PostRandomName  =  SaveCurrentDate  +  SaveCurrentTime;
        StorageReference filePath = postsref.child("Post Image").child(ImageUri.getLastPathSegment()  + ".jpg");

        filePath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    downloadUrl = task.getResult().getDownloadUrl().toString();


                    Toast.makeText(postsActivity.this, " Image uploaded ", Toast.LENGTH_LONG).show();

                   savingPostInformationToDataBase();
                } else {
                    String message = task.getException().getMessage();
                    Toast.makeText(postsActivity.this, " Error : " + message, Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    private void savingPostInformationToDataBase() {
        userRef.child(Current_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String username = dataSnapshot.child("user_name").getValue().toString();
                    String userprofile = dataSnapshot.child("user_image").getValue().toString();


                   // String postimage = dataSnapshot.child("").getValue().toString();

                    HashMap postsMap = new HashMap();
                    postsMap.put("uid", Current_user_id);
                    postsMap.put("date", SaveCurrentDate);
                    postsMap.put("time", SaveCurrentTime);
                    postsMap.put("description", description);

                    postsMap.put("place", place);
                    postsMap.put("color", color);
                    postsMap.put("number", number);



                    postsMap.put("profileimage", userprofile);
                    postsMap.put("postimage", downloadUrl);

                    postsMap.put("fullname", username);

                    postsrefrence.child(Current_user_id  +  PostRandomName ).updateChildren(postsMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {


                                        Toast.makeText(postsActivity.this, " New post is updated ...", Toast.LENGTH_LONG).show();
                                        Loadingbar.dismiss();
                                        Intent senduserintent = new Intent(postsActivity.this,MainActivity.class);
                                        startActivity(senduserintent);
                                    } else {
                                        //  String message = task.getException().getMessage();

                                        Toast.makeText(postsActivity.this, " Error.......", Toast.LENGTH_LONG).show();
                                        Loadingbar.dismiss();

                                    }

                                }
                            });


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, Gallery_Pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            carbtn.setImageURI(ImageUri);


        }
    }
}