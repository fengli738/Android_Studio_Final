package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Scheduler extends AppCompatActivity {

    DatabaseReference reference;
    TextView user;
    Button scheduler_view, create_scheduler;
    String _USERNAME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_layout);

        reference = FirebaseDatabase.getInstance().getReference("user");

        user = findViewById(R.id.textView3);
        scheduler_view = findViewById(R.id.button2);
        create_scheduler = findViewById(R.id.button4);

        showUser();

        scheduler_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Scheduler.this, Scheduler_view.class);
                intent.putExtra("username", _USERNAME);
                startActivity(intent);
            }
        });
        create_scheduler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Scheduler.this, Scheduler_create.class);
                intent.putExtra("username", _USERNAME);
                startActivity(intent);
            }
        });


    }
    private void showUser(){
        Intent intent = getIntent();
        _USERNAME = intent.getStringExtra("username");
        user.setText("Hello " + _USERNAME);
    }


}
