package com.example.win10.missing;

/**
 * Created by emans on 06/03/2018.
 */

public class AllUsers {
    String user_name;
    String user_image;
    String user_statues;
    public AllUsers(){



    }

    public AllUsers(String user_name, String user_image, String user_statues) {
        this.user_name = user_name;
        this.user_image = user_image;
        this.user_statues = user_statues;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_statues() {
        return user_statues;
    }

    public void setUser_statues(String user_statues) {
        this.user_statues = user_statues;
    }


}
