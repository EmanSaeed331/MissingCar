package com.example.win10.missing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
    private Button  Alreadyhaveaccountbutton;
    private Button NeedNewAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_start);
        NeedNewAccountButton = (Button) findViewById(R.id.need_new_account_button);
        Alreadyhaveaccountbutton = (Button) findViewById(R.id.Already_have_account_button);
        NeedNewAccountButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegisterIntent = new Intent(StartActivity.this , RegisterActivity.class);
                startActivity(RegisterIntent);

            }
        } );
        Alreadyhaveaccountbutton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(StartActivity.this , LoginActivity.class);
                startActivity(LoginIntent);
            }
        } );
    }
}
