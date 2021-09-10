package com.example.win10.missing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;


import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;

import com.darwindeveloper.horizontalscrollmenulibrary.custom_views.HorizontalScrollMenuView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import android.support.v4.app.FragmentActivity;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {


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

   /* FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;
*/
   //HorizontalScrollView menu;
   HorizontalScrollMenuView menu2;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Postbutton*/

        //addbutton = (FloatingActionButton) findViewById(R.id.addbtn);
       // SearchInputText = (EditText)findViewById(R.id.search_input_text);
        //SearchButton = (ImageButton)findViewById(R.id.search_btn);


        carimage = (ImageButton) findViewById(R.id.carimg);
        colorText = (EditText) findViewById(R.id.coloredittext);
        numbertext = (EditText) findViewById(R.id.numberedittext);
        placeText = (EditText) findViewById(R.id.placeedittext);
        adbtn = (FloatingActionButton) findViewById(R.id.addbtn);
        postlist = (RecyclerView)findViewById(R.id.all_users_post_list) ;



        postRef = FirebaseDatabase.getInstance().getReference().child("Posts");


        SearchForNumber();
      /*  SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String SearchCarNumber = SearchInputText.getText().toString();
                if(TextUtils.isEmpty(SearchCarNumber)){
                    Toast.makeText(MainActivity.this , "please enter car number " , Toast.LENGTH_LONG).show();
                }

                SearchForNumber();

            }
        });*/


        adbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserspostActivity();


                //   AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                // View MMview = getLayoutInflater().inflate(R.layout.post_dialof,null);


                /*posts designs
                */


                //  mBuilder.setView(MMview);
                //AlertDialog dialog = mBuilder.create();
                //dialog.show();
            }





        });
       // Displayallusersposts();









        postlist = (RecyclerView)findViewById(R.id.all_users_post_list) ;
        postlist.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        postlist.setLayoutManager(linearLayoutManager);






        /*Fragments*/
        TabLayout tabLayout = (TabLayout) findViewById( R.id.tablayout );
       // tabLayout.addTab( tabLayout.newTab().setText( "Timeline" ) );
        //tabLayout.addTab( tabLayout.newTab().setText( " Inbox " ) );
       // tabLayout.addTab( tabLayout.newTab().setText( " Profile " ) );
        //menu//
        mToolbar = (Toolbar) findViewById( R.id.main_page_toolbar );
        setSupportActionBar( mToolbar );
        menu2 = (HorizontalScrollMenuView) findViewById(R.id.menu);
        textView=(TextView)findViewById(R.id.txtText);
         initMenu();


        tabLayout.setTabGravity( TabLayout.GRAVITY_FILL );

        final ViewPager viewpager = (ViewPager) findViewById( R.id.pager );
       // final PagerAdapter adapter = new PagerAdabter( getSupportFragmentManager(), tabLayout.getTabCount() );
        //viewpager.setAdapter( adapter );
        //viewpager.setOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( tabLayout ) );
        tabLayout.setOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem( tab.getPosition() );
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewpager.setCurrentItem( tab.getPosition() );

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewpager.setCurrentItem( tab.getPosition() );

            }
        } );


        mAuth = FirebaseAuth.getInstance();
       /* myViewPager =(ViewPager) findViewById (R.id.main_tabs_pager);*/

        //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.replace(R.id.linear_layout, tab);
        //transaction.commit();

        //TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tabs);
        //tabLayout.setupWithViewPager(myViewPager);

        // myTabLayout.setupWithViewPager(myViewPager);
      // mToolbar = (Toolbar) findViewById( R.id.main_page_toolbar );
        //setSupportActionBar( mToolbar );
       getSupportActionBar().setTitle( "Home" );
       /* FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Log.d(TAG ,"OnCreate : starting");*/

    }

    private void sendUserspostActivity() {
        Intent posetIntent = new Intent(MainActivity.this , postsActivity.class);
        startActivity(posetIntent);
    }

  //  private void Displayallusersposts() {
     private void SearchForNumber( ) {
        Toast.makeText(MainActivity.this , "Searching.....",Toast.LENGTH_LONG).show();

        // Query searchNumberCar = postRef.orderByChild("number").startAt(SearchCarNumber).endAt(SearchCarNumber + "\uf8ff");

        FirebaseRecyclerAdapter<posts ,PostViewHolder > firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<posts, PostViewHolder>(
                        posts.class,
                        R.layout.all_users_layout,
                        PostViewHolder.class,
                        postRef



                ) {
                    @Override
                    protected void populateViewHolder(PostViewHolder viewHolder, posts model, final int position) {
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

                                Intent profileIntent = new Intent(MainActivity.this , ProfileActivity.class);
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
        public void setProfileimage( Context ctx ,String profileimage) {
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

    /*making a car post*/
    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent ,Gallery_Pick );
    }



    private void initMenu() {
        menu2.addItem("Timeline",R.drawable.ic_action_timeline);
        menu2.addItem("inbox",R.drawable.ic_action_msg);
        menu2.addItem("profile",R.drawable.ic_action_profile);
        menu2.addItem("Search",R.drawable.ic_action_serch15);

 menu2.showItems();
        menu2.setOnHSMenuClickListener(new HorizontalScrollMenuView.OnHSMenuClickListener() {
            @Override
            public void onHSMClick(com.darwindeveloper.horizontalscrollmenulibrary.extras.MenuItem menuItem, int position) {
             /*  if (position == 0) {
                    Intent settingIntent = new Intent(MainActivity.this, TimelineActivity.class);
                    startActivity(settingIntent);
                }*/
               if (position == 1) {
                    Intent chatIntent = new Intent(MainActivity.this, displaychat.class);
                    startActivity(chatIntent);
                }
                if (position == 2) {
                    Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(profileIntent);
                }
                if(position == 3)
                {
                    Intent searchIntent = new Intent(MainActivity.this , SearchActivity.class);
                    startActivity(searchIntent);

                }

                Toast.makeText(MainActivity.this , ""+menuItem.getText(), Toast.LENGTH_LONG).show();
                textView.setText(menuItem.getText());

            }


        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = mAuth.getCurrentUser();
        if (currentuser == null) {
            LogOutUser();
           /* Intent startPageIntent = new Intent(MainActivity.this , StartActivity.class);
            startPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(startPageIntent);
            finish();*/

        }
    }


    private void LogOutUser() {
        Intent startPageIntent = new Intent( MainActivity.this, StartActivity.class );
        startPageIntent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity( startPageIntent );
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu( menu );
        getMenuInflater().inflate( R.menu.main_menu, menu );
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected( item );
        if (item.getItemId() == R.id.main_logout_button) {
            mAuth.signOut();
            LogOutUser();
        }
        if (item.getItemId() == R.id.main_account_settings) {
            Intent settingIntent = new Intent( MainActivity.this, Setting_AccountActivity.class );
            startActivity( settingIntent );

        }
        if (item.getItemId() == R.drawable.ic_action_name) {
            Intent nameIntnent = new Intent( MainActivity.this,ProfileActivity.class );
            startActivity( nameIntnent );
        }
        return true;

    }

    /*@Override
    public void onFragmentInteraction(Uri uri) {

    }*/




}

/*
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
     GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id ))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , this )
                .addApi( Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mAuth=FirebaseAuth.getInstance();
        mAuthStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser User = firebaseAuth.getCurrentUser();
                if(User != null){

                    Log.d("Auth",User.getEmail());
                    Toast.makeText(getApplicationContext(),User.getUid(),Toast.LENGTH_LONG).show();
                }

            }
        };



   /public void buSignIn(View view) {
        mAuth.signInAnonymously().addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Log.w("Auth",task.getException());
                }

            }
        } );
     Intent signInInten = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
      startActivityForResult(signInInten ,RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode , int resultCode , Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );

        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account =result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }


        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
        Log.d(TAG,"firebaseAuthWithGoogle:" + acct.getId());


        AuthCredential Credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null );
        mAuth.signInWithCredential(Credential)
                .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG , "SignInWithCredential:" ,task.getException());
                        Toast.makeText(getApplicationContext() ,"Authentication failed" ,Toast.LENGTH_LONG).show();

                    }

                } );
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
*/