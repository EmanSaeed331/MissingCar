package com.example.win10.missing;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by emans on 15/03/2018.
 */
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private FirebaseAuth mAuth;

     private List<Messages> userMessagesList;
    private DatabaseReference GetchatDataReference;
    private  DatabaseReference UserDataBaseRefrence;


    public MessageAdapter(List<Messages>userMessagesList){
         this.userMessagesList = userMessagesList;

     }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         mAuth = FirebaseAuth.getInstance();


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_layout_of_users , parent ,false);

        return  new MessageViewHolder(v);
    }
    public String img;
    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override

    public void onBindViewHolder(final MessageViewHolder holder, int position) {

       //  String message_sender_id = mAuth.getCurrentUser().getUid();


         Messages messages = userMessagesList.get(position);
         String fromUserId = messages.getFrom();
         String fromMessageType = messages.getType();
         UserDataBaseRefrence = FirebaseDatabase.getInstance().getReference().child("Users").child(fromUserId);
         UserDataBaseRefrence.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 String  userName = dataSnapshot.child("user_name").getValue().toString();
                 String userImage = dataSnapshot.child("user_image").getValue().toString();

                 Picasso.with(holder.userProfileImage.getContext()).load(userImage)
                         .placeholder(R.drawable.profile).into(holder.userProfileImage);

             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });
         if(fromMessageType.equals("text")) {
             holder.messagePicture.setVisibility(View.INVISIBLE);
         }
         else{
             holder.messageText.setVisibility(View.INVISIBLE);
             holder.messageText.setPadding(0, 0 , 0 , 0);
             holder.messagePicture.setForegroundGravity(22);
             Picasso.with(holder.userProfileImage.getContext()).load(messages.getMessages())
                     .placeholder(R.drawable.profile).into(holder.messagePicture);


         }

        // String fromUserId =messages.getFrom();
       //  if(fromUserId.equals(message_sender_id)){

           //  holder.messageText.setBackgroundResource(R.drawable.msg_text_background_two);

            // holder.messageText.setTextColor(Color.BLACK);
             //holder.messageText.setGravity(Gravity.RIGHT);
        // }
        // else{
            //    holder.messageText.setBackgroundResource(R.drawable.shape2);

             //   holder.messageText.setTextColor(Color.WHITE);

       //  }


      //   holder.messageText.setText(messages.getMessages());

       /* Picasso.with(holder.userProfileImage.getContext()).load(messages.getImg()).into(holder.userProfileImage);
*/

        GetchatDataReference = FirebaseDatabase.getInstance().getReference().child("Users");
        GetchatDataReference.child(messages.getFrom()).addValueEventListener(new ValueEventListener() {




            public void onDataChange(DataSnapshot dataSnapshot ){

                 img=dataSnapshot.child("user_image").getValue().toString();

               Picasso.with(holder.userProfileImage.getContext()).load(img).into(holder.userProfileImage);


            }




            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        holder.messageText.setText(messages.getMessages());

    }

    @Override
    public int getItemCount() {
        return userMessagesList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        public ImageView messagePicture;


         public MessageViewHolder(View view) {
             super(view);

             messageText = (TextView )view.findViewById(R.id.msg_text);
             messagePicture = (ImageView)view.findViewById(R.id.msg_image_view);
             userProfileImage = (CircleImageView) view.findViewById(R.id.user_img_chat);


         }

         public TextView messageText;
       public CircleImageView userProfileImage;
     }




}
