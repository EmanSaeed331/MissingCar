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
//import android.widget.Toolbar;

public class LoginActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private Button LoginButton;
    private EditText LoginEmail;
    private EditText LoginPasswod;
    private FirebaseAuth mAuth;
    private ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        mAuth=FirebaseAuth.getInstance();
        mToolbar =(Toolbar)findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Sign in");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LoginButton =(Button) findViewById(R.id.Login_Button);
        LoginEmail = (EditText) findViewById(R.id.login_Email);
        LoginPasswod = (EditText)findViewById(R.id.login_Password);
        LoadingBar = new ProgressDialog(this);

        LoginButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = LoginEmail.getText().toString();
                String password = LoginPasswod.getText().toString();

                LoginUserAccount(email,password);
            }
        } );

    }

    private void LoginUserAccount(String email, String password) {
        if (TextUtils.isEmpty( email )) {
            Toast.makeText( LoginActivity.this, "Please enter your email..", Toast.LENGTH_LONG ).show();

        }
        if (TextUtils.isEmpty( password )) {
            Toast.makeText( LoginActivity.this, "Please enter your password..", Toast.LENGTH_LONG ).show();

        }
        else{
            LoadingBar.setTitle("Loading Account");
            LoadingBar.setMessage("Please Wait");
            LoadingBar.show();

            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent mainIntent = new Intent(LoginActivity.this , MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();


                            }
                            else{
                                Toast.makeText(LoginActivity.this , " Error" ,Toast.LENGTH_LONG).show();
                            }
                            LoadingBar.dismiss();

                        }
                    } );

        }
    }
}
