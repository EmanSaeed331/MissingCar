package com.example.win10.missing;

import android.app.FragmentContainer;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class displaychat extends AppCompatActivity {

    private Toolbar mToolbar;

    private TextView nameText;

    private RecyclerView myChatList;
    private DatabaseReference chatref;
    private DatabaseReference userref;
    private FirebaseAuth mAuth;
    private View  myMainView;
    String online_user_id;
    private DatabaseReference GetchatDataReference, GetchatDataReference2;
    private RecyclerView userMsgList;

    private final List<Chaty> messagesList = new ArrayList<>();

    private LinearLayoutManager linearLayoutManager;
    private  chatAdapter chatAdapter;
private CircleImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nameText = (TextView) findViewById(R.id.name);
       // myMainView = (R.layout.activity_displaychat , , false);

      //  Intent mainIntet = new Intent(displaychat.this , InboxActivity.class);
      //  mainIntet.putExtra("online_user_id" , online_user_id);
        //startActivity(mainIntet);

        setContentView(R.layout.activity_displaychat);
        mToolbar = (Toolbar) findViewById(R.id.chat_bar_layout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("inbox");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();


        online_user_id = mAuth.getCurrentUser().getUid();
        //img = (CircleImageView) findViewById(R.id.imchat);




        GetchatDataReference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(online_user_id);
        userref = FirebaseDatabase.getInstance().getReference().child("Users");

        userMsgList = (RecyclerView)findViewById(R.id.chat_list);
        chatAdapter = new chatAdapter(messagesList);

        linearLayoutManager = new LinearLayoutManager(this);


        userMsgList.setHasFixedSize(true);
        userMsgList.setLayoutManager(linearLayoutManager);

         myChatList = (RecyclerView) findViewById(R.id.chat_list);
         myChatList.setHasFixedSize(true);

         LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
         linearLayoutManager.setReverseLayout(true);
         linearLayoutManager.setStackFromEnd(true);
         myChatList.setLayoutManager(linearLayoutManager);
        Displayallusersposts();

    }

    @Override
    protected void onStart() {


        super.onStart();
    }

  public static class ChatsViewHolder extends RecyclerView.ViewHolder {
        View mview;


        public ChatsViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
        }




       /* public void setFullname(String user_name) {
            TextView username = (TextView) mview.findViewById(R.id.all_users_username);
            username.setText(user_name);
        }/*

      /*  public void setProfileimage(Context ctx, String user_image) {
            CircleImageView image = (CircleImageView) mview.findViewById(R.id.chaty_img);
            Picasso.with(ctx).load(user_image).into(image);

        }*/
/*
       /* public void setDescription(String description) {
            TextView userdescription = (TextView) mview.findViewById(R.id.all_users_description);
            userdescription.setText(description);

        }*/

      /*  public void setColor(String color) {
            TextView usercolor = (TextView) mview.findViewById(R.id.all_users_color);
            usercolor.setText(color);
        }*/

    /*    public void setPlace(String place) {
            TextView userplace = (TextView) mview.findViewById(R.id.all_users_place);
            userplace.setText(place);
        }*/

       /* public void setNumber(String number) {
            TextView usernumber = (TextView) mview.findViewById(R.id.all_users_number);
            usernumber.setText(number);

        }*/
       /*

        public void setDate(String date) {
            TextView userdate = (TextView) mview.findViewById(R.id.all_users_date);
            userdate.setText(date);

        }

        public void setTime(String time) {
            TextView usertime = (TextView) mview.findViewById(R.id.all_users_time);
            usertime.setText(time);

        }

        public void setPostimage(Context ctx, String postimage) {
            ImageView Post = (ImageView) mview.findViewById(R.id.post_image);
            Picasso.with(ctx).load(postimage).into(Post);


        }*/

    }

    /*  private void displayHeaders() {
        FirebaseAuth mAuth2 = FirebaseAuth.getInstance();

        String myId  = mAuth2.getCurrentUser().getUid().toString();

            DatabaseReference chatHeadersRef = FirebaseDatabase.getInstance().getReference().child("Message");
        chatHeadersRef.child(myId).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    Toast.makeText(displaychat.this,"hi",Toast.LENGTH_LONG).show();

                    Toast.makeText(displaychat.this,postSnapshot.toString(),Toast.LENGTH_LONG).show();


                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


    private void Displayallusersposts() {

        String myid = mAuth.getCurrentUser().getUid().toString();

        GetchatDataReference = FirebaseDatabase.getInstance().getReference().child("Messages");
        GetchatDataReference.getRef().child(myid).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String HeaderUserId = dataSnapshot.getKey().toString();
                dius(HeaderUserId);
              //  getUserDataHeader(HeaderUserId);


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

        });

    }
public void dius(String uid){
    GetchatDataReference = FirebaseDatabase.getInstance().getReference().child("Users");
  GetchatDataReference.child(uid).addValueEventListener(new ValueEventListener() {
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                Chaty users = dataSnapshot.getValue(Chaty.class);
                                                                messagesList.add(users);


                                                                userMsgList.setAdapter(chatAdapter);
                                                                chatAdapter.notifyDataSetChanged();

                                                                Toast.makeText(displaychat.this, dataSnapshot.child("user_name").getValue().toString(), Toast.LENGTH_LONG).show();


                      }


      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
    }


/*
 public void dispusersChat(String uid) {

     GetchatDataReference2 =FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
;

     Toast.makeText(displaychat.this, uid, Toast.LENGTH_LONG).show();


        FirebaseRecyclerAdapter<Chaty, chatyViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Chaty, chatyViewHolder>(
                        Chaty.class,
                        R.layout.displaychatuser,
                        chatyViewHolder.class,
                        GetchatDataReference2





                ) {
                    @Override
                    protected void populateViewHolder(chatyViewHolder viewHolder, Chaty model, int position) {
                        viewHolder.setUser_name(model.getHeaders());
                        viewHolder.setUser_image(getApplicationContext() , model.getFrom());

                    }
                };
            myChatList.setAdapter(firebaseRecyclerAdapter);



    }

    public static class chatyViewHolder extends RecyclerView.ViewHolder{
        View mview ;

        public chatyViewHolder(View itemView) {

            super(itemView);
            mview = itemView;
        }

        public void setUser_name(String user_name) {
            TextView chatyname = (TextView)mview.findViewById(R.id.displayname);
            chatyname.setText(user_name);
        }
        public void setUser_image(Context ctx , String user_image) {
            CircleImageView chatyimg = (CircleImageView)mview.findViewById(R.id.chaty_img);
            Picasso.with(ctx).load(user_image).into(chatyimg);
        }
    }
*/

    private void getUserDataHeader(String uId) {

        Toast.makeText(displaychat.this, uId, Toast.LENGTH_LONG).show();

        GetchatDataReference2 = FirebaseDatabase.getInstance().getReference().child("Users");


        GetchatDataReference2.child(uId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {




                String Name = dataSnapshot.child("user_name").getValue().toString();

                Toast.makeText(displaychat.this, Name, Toast.LENGTH_LONG).show();

                String image = dataSnapshot.child("user_image").getValue().toString();

                if(image.equals("default_profile")) {
                  //  Picasso.with(displaychat.this).load(R.drawable.profile).into(img);

                }
                else{
                    //Picasso.with(displaychat.this).load(image).into(img);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






       /* FirebaseRecyclerAdapter<Chats, displaychat.ChatsViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Chats, ChatsViewHolder>(
                        Chats.class,
                        R.layout.all_users_layout,
                        displaychat.ChatsViewHolder.class,
                        chatref


                ) {
                    @Override
                    protected void populateViewHolder(final displaychat.ChatsViewHolder viewHolder, Chats model, final int position) {
                        viewHolder.setFullname(model.getFullname());
                        viewHolder.setDate(model.getDate());
                        viewHolder.setDescription(model.getDescription());
                        viewHolder.setTime(model.getTime());
                        viewHolder.setPostimage(getApplicationContext(), model.getPostimage());
                        viewHolder.setProfileimage(getApplicationContext(), model.getProfileimage());

                        viewHolder.setPlace(model.getPlace());
                        viewHolder.setColor(model.getColor());
                        viewHolder.setNumber(model.getNumber());

                        viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String visit_user_id = getRef(position).getKey();

                                Intent profileIntent = new Intent(displaychat.this, ProfileActivity.class);
                                profileIntent.putExtra("Visit_user_id", visit_user_id);
                                startActivity(profileIntent);

                            }
                        });

                    }


                };*/
    }
}