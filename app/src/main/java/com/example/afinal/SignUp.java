package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    EditText regUsername, regPassword;
    Button regBtn, signedBack;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        //Take all element from signup layout
        regUsername = findViewById(R.id.text4);
        regPassword = findViewById(R.id.text5);
        regBtn = findViewById(R.id.b2);
        signedBack = findViewById(R.id.signed_back);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("user");

                // get all value;
                String username = regUsername.getText().toString();
                String password = regPassword.getText().toString();

                UserHelper userHelper = new UserHelper(username, password);

                reference.child(username).setValue(userHelper);

                Toast.makeText(SignUp.this, "Registered",
                        Toast.LENGTH_LONG).show();
            }
        });

        signedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });
    }

}
