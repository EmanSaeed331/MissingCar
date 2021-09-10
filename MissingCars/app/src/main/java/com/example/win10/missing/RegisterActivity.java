package com.example.win10.missing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import android.widget.Toolbar;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText RegisterUserName;
    private EditText RegisterUserEmail;
    private EditText RegisterUserPassword;
    private Button CreateAccountButton;
    private FirebaseAuth mAuth;
    private DatabaseReference StoreUserDefaultDataReference;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        mAuth =FirebaseAuth.getInstance();
        mToolbar = (Toolbar)findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RegisterUserName =(EditText)findViewById(R.id.register_name);
        RegisterUserEmail=(EditText)findViewById(R.id.register_email);
        RegisterUserPassword=(EditText)findViewById(R.id.register_password);
        CreateAccountButton=(Button)findViewById(R.id.create_account_button);
        loadingbar = new ProgressDialog(this);

        CreateAccountButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final   String name = RegisterUserName.getText().toString();
                String email = RegisterUserEmail.getText().toString();
                String password = RegisterUserPassword.getText().toString();
                
                RegisterAccount(name,email,password);
            }
        } );

    }

    private void RegisterAccount(final String name, String email, String password) {
        if(TextUtils.isEmpty(name)){
            Toast.makeText(RegisterActivity.this , "Please enter your name ." ,Toast.LENGTH_LONG).show();

        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(RegisterActivity.this , "Please enter your email ." ,Toast.LENGTH_LONG).show();

        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText( RegisterActivity.this, "Please enter your password .", Toast.LENGTH_LONG ).show();

        }
        else{


            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String current_user_id = mAuth.getCurrentUser().getUid();

                                StoreUserDefaultDataReference= FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_id);
                                StoreUserDefaultDataReference.child("user_name").setValue(name);
                                StoreUserDefaultDataReference.child("user_statues").setValue("using missing app");
                                StoreUserDefaultDataReference.child("user_image").setValue("default_profile");
                                StoreUserDefaultDataReference.child("user_thump").setValue("default_image")
                                        .addOnCompleteListener( new OnCompleteListener<Void>()
                                        {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Intent mainIntent = new Intent(RegisterActivity.this , MainActivity.class);
                                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(mainIntent);
                                                    finish();


                                                }

                                            }
                                        } );




                                /*Intent mainIntent = new Intent(RegisterActivity.this , MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();*/
                            }
                            else{
                                Toast.makeText(RegisterActivity.this,"Error please try again",Toast.LENGTH_LONG).show();
                            }
                            loadingbar.dismiss();

                        }

                    } );
        }
        }
}
