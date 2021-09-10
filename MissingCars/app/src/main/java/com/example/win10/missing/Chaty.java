package com.example.win10.missing;



public class Chaty {


    String user_name;
    String user_image;

    public Chaty(){

    }

    public Chaty(String user_name ,String user_image) {

        this.user_name = user_name;
        this.user_image = user_image;

    }



    public String getUserName()
    {
        return user_name;


    }

    public String getImg() {
        return user_image;
    }




}
