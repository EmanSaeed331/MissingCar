package com.example.win10.missing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


public class UsersActivity extends AppCompatActivity {
private Toolbar mToolbar ;
private RecyclerView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_users );
        mToolbar = (Toolbar)findViewById(R.id.all_users_appbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listview = (RecyclerView) findViewById(R.id.all_userslist);


    }
}
