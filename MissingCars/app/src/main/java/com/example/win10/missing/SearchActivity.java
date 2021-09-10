package com.example.win10.missing;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchActivity extends AppCompatActivity {



    FirebaseAuth mAuth;
    private FloatingActionButton addbutton;
    private Toolbar mToolbar;
    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private ImageButton carimage ;
    private EditText colorText;
    private  EditText numbertext;
    private EditText placeText;
    private  FloatingActionButton adbtn;
    private RecyclerView postlist;
    private DatabaseReference postRef;
    private  static final int Gallery_Pick = 1;
    private EditText SearchInputText;
    private ImageButton SearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        carimage = (ImageButton) findViewById(R.id.carimg);
        colorText = (EditText) findViewById(R.id.coloredittext);
        numbertext = (EditText) findViewById(R.id.numberedittext);
        placeText = (EditText) findViewById(R.id.placeedittext);
        adbtn = (FloatingActionButton) findViewById(R.id.addbtn);
        postlist = (RecyclerView)findViewById(R.id.all_users_post_list) ;

        SearchInputText = (EditText)findViewById(R.id.search_input_text);
        SearchButton = (ImageButton)findViewById(R.id.search_btn2);



        mToolbar = (Toolbar)findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Search");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        postlist = (RecyclerView)findViewById(R.id.all_users_post_list) ;
        postlist.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        postlist.setLayoutManager(linearLayoutManager);
        postRef = FirebaseDatabase.getInstance().getReference().child("Posts");


        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String SearchCarNumber = SearchInputText.getText().toString();
                if(TextUtils.isEmpty(SearchCarNumber)){
                    Toast.makeText(SearchActivity.this , "please enter car number " , Toast.LENGTH_LONG).show();
                }

                SearchForNumber(SearchCarNumber);

            }
        });


    }




    private void SearchForNumber(String SearchCarNumber ) {
        Toast.makeText(SearchActivity.this , "Searching.....",Toast.LENGTH_LONG).show();

        Query searchNumberCar = postRef.orderByChild("number").startAt(SearchCarNumber).endAt(SearchCarNumber + "\uf8ff");

        FirebaseRecyclerAdapter<posts ,MainActivity.PostViewHolder > firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<posts, MainActivity.PostViewHolder>(
                        posts.class,
                        R.layout.all_users_layout,
                        MainActivity.PostViewHolder.class,
                        searchNumberCar



                ) {
                    @Override
                    protected void populateViewHolder(MainActivity.PostViewHolder viewHolder, posts model, final int position) {
                        viewHolder.setFullname(model.getFullname());
                        viewHolder.setDate(model.getDate());
                        viewHolder.setDescription(model.getDescription());
                        viewHolder.setTime(model.getTime());
                        viewHolder.setPostimage(getApplicationContext() , model.getPostimage());
                        viewHolder.setProfileimage(getApplicationContext() , model.getProfileimage());

                        viewHolder.setPlace(model.getPlace());
                        viewHolder.setColor(model.getColor());
                        viewHolder.setNumber(model.getNumber());


                        viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String visit_user_id = getRef(position).getKey();

                                Intent profileIntent = new Intent(SearchActivity.this , ProfileActivity.class);
                                profileIntent.putExtra("Visit_user_id" , visit_user_id);
                                startActivity(profileIntent);

                            }
                        });



                    }
                };
        postlist.setAdapter(firebaseRecyclerAdapter);




    }


    public static class PostViewHolder extends  RecyclerView.ViewHolder{
        View mview;


        public PostViewHolder(View itemView) {
            super(itemView);
            mview=itemView;
        }


        public void setFullname(String fullname) {
            TextView username = (TextView) mview.findViewById(R.id.all_users_username);
            username.setText(fullname);
        }
        public void setProfileimage(Context ctx , String profileimage) {
            CircleImageView image = (CircleImageView) mview.findViewById(R.id.all_users_profile_image);
            Picasso.with(ctx).load(profileimage).into(image);

        }
        public void setDescription(String description) {
            TextView userdescription = (TextView) mview.findViewById(R.id.all_users_description);
            userdescription.setText(description);

        }
        public void setColor(String color) {
            TextView usercolor = (TextView)mview.findViewById(R.id.all_users_color);
            usercolor.setText(color);
        }
        public void setPlace(String place) {
            TextView userplace = (TextView)mview.findViewById(R.id.all_users_place);
            userplace.setText(place);
        }
        public void setNumber(String number) {
            TextView usernumber = (TextView)mview.findViewById(R.id.all_users_number);
            usernumber.setText(number);

        }

        public void setDate(String date) {
            TextView userdate = (TextView) mview.findViewById(R.id.all_users_date);
            userdate.setText(date);

        }
        public void setTime(String time) {
            TextView usertime = (TextView) mview.findViewById(R.id.all_users_time);
            usertime.setText(time);

        }

        public void setPostimage( Context ctx,String postimage) {
            ImageView Post = (ImageView) mview.findViewById(R.id.post_image);
            Picasso.with(ctx).load(postimage).into(Post);


        }

    }


}

