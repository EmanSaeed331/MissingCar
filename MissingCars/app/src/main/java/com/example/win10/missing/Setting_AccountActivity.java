package com.example.win10.missing;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Setting_AccountActivity extends AppCompatActivity {
    private CircleImageView settingDisplayprofileImage;
    private TextView SettingDisplayNameTextView;
    private TextView SettingDisplayStatuesTextView;
    private Button SettingChangeProfileImageButton;
    private Button SettingChangeStatues;
    private DatabaseReference GetUserDataReference;
    private FirebaseAuth mAuth;
    private StorageReference storeProfileImageStorageRef;
    private final static int Gallery_Pick =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_setting__account );
        mAuth = FirebaseAuth.getInstance();
        String online_user_id = mAuth.getCurrentUser().getUid();
        GetUserDataReference = FirebaseDatabase.getInstance().getReference().child("Users").child(online_user_id);
        storeProfileImageStorageRef = FirebaseStorage.getInstance().getReference().child("Profile_Images");


        settingDisplayprofileImage =(CircleImageView)findViewById(R.id.setting_profile_image);
        SettingDisplayNameTextView =(TextView)findViewById(R.id.setting_user_name);
        SettingDisplayStatuesTextView =(TextView)findViewById(R.id.setting_user_statues);
        SettingChangeProfileImageButton=(Button)findViewById(R.id.change_user_profile);
        SettingChangeStatues = (Button)findViewById(R.id.Change_user_statues);
        GetUserDataReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Name = dataSnapshot.child("user_name").getValue().toString();
                //String name = dataSnapshot.child("user_name").getValue().toString();
                String StatuesUser = dataSnapshot.child("user_statues").getValue().toString();
                String image = dataSnapshot.child("user_image").getValue().toString();
                String thumb_image = dataSnapshot.child("user_thump").getValue().toString();
               SettingDisplayNameTextView.setText(Name);
                SettingDisplayStatuesTextView.setText(StatuesUser);
                if(image.equals("default_profile")) {
                    Picasso.with(Setting_AccountActivity.this).load(R.drawable.profile).into(settingDisplayprofileImage);

                }
                else{
                    Picasso.with(Setting_AccountActivity.this).load(image).into(settingDisplayprofileImage);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );
        SettingChangeProfileImageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent ,Gallery_Pick );
            }
        } );
        SettingChangeStatues.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StatuesIntent = new Intent (Setting_AccountActivity.this , StatuesActivity.class);
                startActivity(StatuesIntent);
            }
        } );




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if(requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null){

            Uri ImageURi = data.getData();
            CropImage.activity()
                    .setGuidelines( CropImageView.Guidelines.ON)
                    .setAspectRatio( 1 ,1 )
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                String user_id = mAuth.getCurrentUser().getUid();
                StorageReference filepath = storeProfileImageStorageRef.child(user_id + ".jpg");
                filepath.putFile(resultUri).addOnCompleteListener( new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Setting_AccountActivity.this ,"Saving your profile image in firebase storage..." ,Toast.LENGTH_LONG).show();
                            String downlaodURl = task.getResult().getDownloadUrl().toString();
                            GetUserDataReference.child("user_image").setValue(downlaodURl)
                                    .addOnCompleteListener( new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Setting_AccountActivity.this ,"Image Uploaded Successfully...  " ,Toast.LENGTH_LONG).show();

                                        }
                                    } );
                        }
                        else{
                            Toast.makeText(Setting_AccountActivity.this ,"Error occoured during uploading the image " ,Toast.LENGTH_LONG).show();

                        }

                    }
                } );
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }




    }
}
