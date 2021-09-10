package com.example.win10.missing;

import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

public class InboxActivity extends AppCompatActivity {
    private Toolbar chatbar;
    private TextView usernameTiTle;
    private TextView userLastSeen;
    private CircleImageView userchatprofileimage;


    private String messageRecieverId;
    private String messageRecievername;
    private DatabaseReference rootRef;


    private CircleImageView chatprofile;
    private TextView chatusername;
    private TextView SettingDisplayStatuesTextView;
    private Button SettingChangeProfileImageButton;
    private Button SettingChangeStatues;
    private DatabaseReference GetchatDataReference;
    private FirebaseAuth mAuth, mAuth2;
    private String messagasenderId;
    private StorageReference storeProfileImageStorageRef;

    private ImageButton Selectimgaebtn;
    private ImageButton sendlocationbtn;
    private ImageButton sendmsgbtn;
    private EditText inputmsg;

    private RecyclerView userMsgList;
    private DatabaseReference stateref;

    private FirebaseUser currentUser;

    private final List<Messages> messagesList = new ArrayList<>();

    private LinearLayoutManager linearLayoutManager;
    private  MessageAdapter messageAdapter;
    static int Gallery_Pick = 1;
    private StorageReference MessageImageStorageRef;
    private ProgressDialog LoadingBar;

    private static final int MY_PERMISSION_REQUEST_CODE = 7171;
    private static final int PLAY_SERVICES_RES_REQUEST = 7172;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        MessageImageStorageRef = FirebaseStorage.getInstance().getReference().child("Messages_Pictures");

        mAuth = FirebaseAuth.getInstance();
        mAuth2 = FirebaseAuth.getInstance();
        messagasenderId = mAuth2.getCurrentUser().getUid().toString();

        messageRecieverId = getIntent().getExtras().get("chat_user_id").toString();

        userMsgList = (RecyclerView)findViewById(R.id.msgs_lis_of_users);
        messageAdapter = new MessageAdapter(messagesList);

        linearLayoutManager = new LinearLayoutManager(this);


        userMsgList.setHasFixedSize(true);
        userMsgList.setLayoutManager(linearLayoutManager);


        FetchMessages();







        Selectimgaebtn = (ImageButton) findViewById(R.id.selectImage);
        sendlocationbtn = (ImageButton) findViewById(R.id.sendlocationbtn);
        sendmsgbtn = (ImageButton) findViewById(R.id.sendmsgbtn);
        inputmsg = (EditText) findViewById(R.id.inputmsgEdittext);

        rootRef = FirebaseDatabase.getInstance().getReference();
        LoadingBar = new ProgressDialog(this);


        GetchatDataReference = FirebaseDatabase.getInstance().getReference().child("Users");
        //  storeProfileImageStorageRef = FirebaseStorage.getInstance().getReference().child("Profile_Images");


        sendlocationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapintent = new Intent (InboxActivity.this , MapsActivity.class);
                startActivity(mapintent);
            }
        });


        chatprofile = (CircleImageView) findViewById(R.id.image_chat);
        chatusername = (TextView) findViewById(R.id.username_chat);
        Toast.makeText(InboxActivity.this, messageRecieverId, Toast.LENGTH_LONG).show();

        GetchatDataReference.child(messageRecieverId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Namee = dataSnapshot.child("user_name").getValue().toString();



                //String name = dataSnapshot.child("user_name").getValue().toString();
                //String StatuesUser = dataSnapshot.child("user_statues").getValue().toString();
                String image = dataSnapshot.child("user_image").getValue().toString();

                chatusername.setText(Namee);

                if (image.equals("default_profile")) {
                    Picasso.with(InboxActivity.this).load(R.drawable.profile).into(chatprofile);

                } else {
                    Picasso.with(InboxActivity.this).load(image).into(chatprofile);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        Selectimgaebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent , Gallery_Pick);
            }
        });







        sendmsgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sendmsg();
            }
        });


        //  usernameTiTle.setText(messageRecievername);


    }

    private void FetchMessages() {
        GetchatDataReference = FirebaseDatabase.getInstance().getReference().child("Messages");
      try{  GetchatDataReference.child(messagasenderId).child(messageRecieverId).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                       Messages messages = dataSnapshot.getValue(Messages.class);
                        messagesList.add(messages);
                        try{
                        String msgOwnerId = dataSnapshot.child("msgOwnerId").getValue().toString();
                        Toast.makeText(InboxActivity.this,msgOwnerId, Toast.LENGTH_LONG).show();}
                        catch(Exception e) {

                        }

    userMsgList.setAdapter(messageAdapter);
                         messageAdapter.notifyDataSetChanged();







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
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });}
                catch(Exception x){

                }
    }


    private void sendmsg() {
        String messageText = inputmsg.getText().toString();

        if (TextUtils.isEmpty(messageText)) {
            Toast.makeText(InboxActivity.this, "enter a masega ", Toast.LENGTH_LONG).show();



        }
        else{

            String  msg_sender_ref = "Messages/" + messagasenderId + "/" + messageRecieverId;
            String  msg_reciver_ref = "Messages/" + messageRecieverId + "/" + messagasenderId;

            DatabaseReference user_msg_key = rootRef.child("Messages").child(messagasenderId)
                    .child(messageRecieverId).push();

            String  messgae_push_id = user_msg_key.getKey();

            Map messgaTextBody  = new HashMap();
            messgaTextBody.put("messages" , messageText);
            messgaTextBody.put("type" , "text");
           // messgaTextBody.put("msgOwnerId" , messagasenderId);
            messgaTextBody.put("from" , messagasenderId);



            Map messageBodyDetailds = new HashMap();
            messageBodyDetailds.put(msg_sender_ref + "/ " + messgae_push_id , messgaTextBody);
            messageBodyDetailds.put(msg_reciver_ref + "/ " + messgae_push_id , messgaTextBody);

            rootRef.updateChildren(messageBodyDetailds, new DatabaseReference.CompletionListener() {
                @Override

                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {



                    if(databaseError != null){

                        Log.d("Chat_Log" , databaseError.getMessage().toString());
                    }

                    inputmsg.setText(" ") ;

                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null){
            LoadingBar.setTitle("Sending chat Image");
            LoadingBar.setMessage("Please Wait...");
            LoadingBar.show();

            Uri ImageURi = data.getData();
            final String  msg_sender_ref = "Messages/" + messagasenderId + "/" + messageRecieverId;
            final String  msg_reciver_ref = "Messages/" + messageRecieverId + "/" + messagasenderId;


            DatabaseReference user_msg_key = rootRef.child("Messages").child(messagasenderId)
                    .child(messageRecieverId).push();

            final String  messgae_push_id = user_msg_key.getKey();

            StorageReference filePath = MessageImageStorageRef.child(messgae_push_id + ".jpg");

            filePath.putFile(ImageURi).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                   // Toast.makeText(InboxActivity.this , "Image Send... ", Toast.LENGTH_LONG ).show();
                    if(task.isSuccessful()){
                         final String downlaodURl = task.getResult().getDownloadUrl().toString();


                        Map messgaTextBody  = new HashMap();
                        messgaTextBody.put("messages" , downlaodURl);
                        messgaTextBody.put("type" , "image");
                        // messgaTextBody.put("msgOwnerId" , messagasenderId);
                        messgaTextBody.put("from" , messagasenderId);

                        Map messageBodyDetailds = new HashMap();
                        messageBodyDetailds.put(msg_sender_ref + "/ " + messgae_push_id , messgaTextBody);
                        messageBodyDetailds.put(msg_reciver_ref + "/ " + messgae_push_id , messgaTextBody);



                        rootRef.updateChildren(messageBodyDetailds, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if(databaseError != null){
                                    Log.d("Chat_Log" , databaseError.getMessage().toString());

                                }
                                inputmsg.setText(" ");
                                LoadingBar.dismiss();

                            }
                        });
                        Toast.makeText(InboxActivity.this , "Image Send... ", Toast.LENGTH_LONG ).show();
                        LoadingBar.dismiss();
                    }
                    else{
                        Toast.makeText(InboxActivity.this , "Error .... ", Toast.LENGTH_LONG ).show();
                        LoadingBar.dismiss();


                    }

                }
            });

        }
    }
}