package com.example.win10.missing;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TimelineActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView timelineuser;
    private DatabaseReference allDatabaseusersRefrence;
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        mToolbar =(Toolbar)findViewById(R.id.all_users_app_par);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Timeline");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        timelineuser =(RecyclerView)findViewById(R.id.all_users_list2);
        timelineuser.setHasFixedSize(true);
        timelineuser.setLayoutManager(new LinearLayoutManager(this));
        allDatabaseusersRefrence = FirebaseDatabase.getInstance().getReference().child("Users");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<AllUsers, AllUsersViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<AllUsers, AllUsersViewHolder>(
                        AllUsers.class,
                        R.layout.all_users_layout,
                        AllUsersViewHolder.class,
                        allDatabaseusersRefrence
        ) {
            @Override
            protected void populateViewHolder(AllUsersViewHolder viewHolder, AllUsers model, int position) {
                viewHolder.setUser_name(model.getUser_name());
                viewHolder.setUser_statues(model.getUser_statues());

            }
        };

    }
    public static class AllUsersViewHolder  extends RecyclerView.ViewHolder
    {
        View mview;
        public AllUsersViewHolder(View itemView) {
            super(itemView);
            mview=itemView;
        }
        public void setUser_name(String user_name){
            TextView name = (TextView) mview.findViewById(R.id.all_users_username);
            name.setText(user_name);

        }
        public void setUser_statues(String user_statues) {
            TextView statues = (TextView) mview.findViewById(R.id.all_users_statues);
            statues.setText(user_statues);
        }
    }*/
}
