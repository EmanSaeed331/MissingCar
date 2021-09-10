package com.example.win10.missing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private Button chatbtn;
    private Toolbar mToolbar;
    TextView nameProf;
    TextView stateProf;
    private DatabaseReference GetUserDataReference;
    private StorageReference storeProfileImageStorageRef;
    FirebaseAuth mAuth;
    private CircleImageView profileImg;
    private ImageView profilimg;

    private DatabaseReference UsersRefrence , usref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mToolbar =(Toolbar)findViewById(R.id.all_users_app_par);

        chatbtn = (Button)findViewById(R.id.open_chat_btn) ;


        UsersRefrence = FirebaseDatabase.getInstance().getReference().child("Users");


        UsersRefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        chatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent chatintent = new Intent(ProfileActivity.this , InboxActivity.class);
                String postId = getIntent().getExtras().get("Visit_user_id").toString();
                usref = FirebaseDatabase.getInstance().getReference().child("Posts");


                usref.child(postId).addValueEventListener( new ValueEventListener() {

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String userId = dataSnapshot.child("uid").getValue().toString();

                        chatintent.putExtra("chat_user_id" , userId);
                       // Toast.makeText(ProfileActivity.this , userId , Toast.LENGTH_LONG).show();
                        startActivity(chatintent);

                    }

                    });



            }
        });




    /* try {
         String visit_user_id = getIntent().getExtras().get("Visit_user_id").toString();
         Toast.makeText(this , visit_user_id ,Toast.LENGTH_LONG ).show();

     }catch(Exception x){

     }*/

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        String online_user_id = mAuth.getCurrentUser().getUid();
        // GetUserDataReference = FirebaseDatabase.getInstance().getReference().child("Users").child(online_user_id);
        storeProfileImageStorageRef = FirebaseStorage.getInstance().getReference().child("Profile_Images");


        profileImg =(CircleImageView)findViewById(R.id.profileImg);


        nameProf =(TextView)findViewById(R.id.nameProf);
        stateProf =(TextView)findViewById(R.id.stateProf);

        String visit_user_id = getIntent().getExtras().get("Visit_user_id").toString();

        Toast.makeText(this , visit_user_id ,Toast.LENGTH_LONG ).show();

        UsersRefrence = FirebaseDatabase.getInstance().getReference().child("Posts");


        UsersRefrence.child(visit_user_id).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Name = dataSnapshot.child("fullname").getValue().toString();
                //String name = dataSnapshot.child("user_name").getValue().toString();
                String StatuesUser = dataSnapshot.child("description").getValue().toString();
                String image = dataSnapshot.child("profileimage").getValue().toString();
                // String thumb_image = dataSnapshot.child("user_thump").getValue().toString();

                nameProf.setText(Name);
                stateProf.setText(StatuesUser);
                Picasso.with(ProfileActivity.this).load(image).placeholder(R.drawable.profile).into(profileImg);

                if(image.equals("default_profile")) {
                    Picasso.with(ProfileActivity.this).load(R.drawable.profile).into(profileImg);

                }
                else{
                    Picasso.with(ProfileActivity.this).load(image).into(profileImg);

                }





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );
    }

}
