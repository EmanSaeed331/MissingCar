package com.example.win10.missing;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by emans on 15/03/2018.
 */

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.MessageViewHolder> {
    private FirebaseAuth mAuth;
     private List<Chaty> chatHeadrsList;
    View parent;


     public chatAdapter(List<Chaty>chatHeadrsList){
         this.chatHeadrsList = chatHeadrsList;

     }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         mAuth = FirebaseAuth.getInstance();


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.displaychatuser , parent ,false);

        return  new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {

       //  String message_sender_id = mAuth.getCurrentUser().getUid();

         Chaty headers = chatHeadrsList.get(position);


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


         holder.messageText.setText(headers.getUserName());



        Picasso.with(holder.userProfileImage.getContext()).load(headers.getImg()).into(holder.userProfileImage);



    }

    @Override
    public int getItemCount() {
        return chatHeadrsList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{


         public MessageViewHolder(View view) {
             super(view);

             messageText = (TextView )view.findViewById(R.id.displayname);

            userProfileImage = (CircleImageView)view.findViewById(R.id.chaty_img);
         }

         public TextView messageText;
        private  CircleImageView userProfileImage;

        //   public CircleImageView userProfileImage;
     }
}
