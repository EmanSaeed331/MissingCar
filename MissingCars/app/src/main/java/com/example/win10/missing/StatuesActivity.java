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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatuesActivity extends AppCompatActivity {
    private Button saveChangeButton;
    private EditText StatuesInput;
    private Toolbar mToolbar;
    private DatabaseReference changeStatuesRefrens;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_statues );
        mAuth =FirebaseAuth.getInstance();
        String User_id = mAuth.getCurrentUser().getUid();
        changeStatuesRefrens= FirebaseDatabase.getInstance().getReference().child("Users").child(User_id);

        mToolbar =(Toolbar)findViewById(R.id.statues_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Change statues");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        saveChangeButton = (Button)findViewById(R.id.save_statues_changes_button);
        StatuesInput =(EditText)findViewById(R.id.statues_input);
        loadingbar = new ProgressDialog(this);


        saveChangeButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_Statues = StatuesInput.getText().toString();
                ChangeProfileStatues(new_Statues);
            }
        } );




    }

    private void ChangeProfileStatues(String new_statues) {
        if(TextUtils.isEmpty(new_statues)){
            Toast.makeText(StatuesActivity.this , "Please enter your statues" , Toast.LENGTH_LONG).show();

        }
        else{
            loadingbar.setTitle( "Change Statues" );
            loadingbar.setMessage("Please wait ..");
            loadingbar.show();

            changeStatuesRefrens.child("user_statues").setValue(new_statues)
                    .addOnCompleteListener( new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                loadingbar.dismiss();
                                Intent settingInetnt = new Intent(StatuesActivity.this , Setting_AccountActivity.class);
                                startActivity(settingInetnt);
                                Toast.makeText(StatuesActivity.this ,"Status Updeted successfully " ,Toast.LENGTH_LONG).show();



                            }
                            else {
                                Toast.makeText(StatuesActivity.this , " Error ",Toast.LENGTH_LONG).show();

                            }

                        }
                    } );

        }
    }
}
