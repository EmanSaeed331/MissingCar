package com.example.win10.missing;

/**
 * Created by emans on 16/03/2018.
 */

public class Chats {
    String uid;
    String profileimage;
    String fullname;
    String description;
    String date;
    String color;
    String place;
    String number;
    String time;

    String postimage;

    public Chats() {


    }

    public Chats(String uid, String profileimage, String fullname, String description, String date, String time, String postimage, String color, String place, String number) {
        this.uid = uid;
        this.profileimage = profileimage;
        this.fullname = fullname;
        this.description = description;
        this.date = date;
        this.time = time;
        this.postimage = postimage;
        this.color = color;
        this.place = place;
        this.number = number;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }


    public void setTime(String time) {
        this.time = time;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}