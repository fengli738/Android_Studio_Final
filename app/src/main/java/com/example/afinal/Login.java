package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {


    EditText username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        username = findViewById(R.id.text1);
        password = findViewById(R.id.text2);
    }

    private boolean validateUsername(){
        String val =  username.getText().toString();

        if (val.isEmpty()){
            username.setError("Field cannot be empty");
            return false;
        }else{
            username.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        String val = password.getText().toString();
        if (val.isEmpty()){
            password.setError("Field cannot be empty");
            return false;
        }else{
            password.setError(null);
            return true;
        }
    }

    public void loginUser(View view){
        if (!validatePassword() | !validateUsername()){
            return;
        }
        else{
            isUser();
        }
    }

    private void isUser(){
        String userEntered = username.getText().toString();
        String passEntered = password.getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");

        Query checkUser = reference.orderByChild("username").equalTo(userEntered);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    username.setError(null);
                    String passFromDB = snapshot.child(userEntered).child("password")
                            .getValue(String.class);
                    if (passFromDB.equals(passEntered)){

                        username.setError(null);
                        String UsernameDB = snapshot.child(userEntered).child("username").getValue(String.class);
                        Intent intent = new Intent(Login.this, Scheduler.class);
                        intent.putExtra("username", UsernameDB);
                        startActivity(intent);
                    }else{
                        password.setError("Wrong Password!");
                        password.requestFocus();
                    }
                }else{
                    username.setError("No such User exist.");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void jumptoReg(View view){
        Intent intent = new Intent(Login.this, SignUp.class);
        startActivity(intent);
    }




}
