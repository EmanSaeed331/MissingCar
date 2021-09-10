package com.example.win10.missing;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Messages {


    String messages;
   public String user_img;
    String from;
    String type;
    private DatabaseReference GetchatDataReference;

    public Messages(){

    }

    public Messages(String messages ,String from , String type) {

        this.messages = messages;
        this.type = type;


    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessages() {
        return messages;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public void settUser_img(){

    }
    public String getUser_img(){
        settUser_img();
        return user_img;
    }

}
