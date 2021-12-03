package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class Login extends AppCompatActivity {


    EditText username, password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TwitterLoginButton mTwitterBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        username = findViewById(R.id.text1);
        password = findViewById(R.id.text2);
        TwitterAuthConfig mTwitterAuthConfig = new TwitterAuthConfig(getString(R.string.twitter_consumer_key),
                getString(R.string.twitter_consumer_secret));
        TwitterConfig twitterConfig = new TwitterConfig.Builder(this)
                .twitterAuthConfig(mTwitterAuthConfig)
                .build();
        Twitter.initialize(twitterConfig);
        mAuth = FirebaseAuth.getInstance();

        mTwitterBtn = findViewById(R.id.twitter_login_button);

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                if (firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(Login.this, Scheduler.class));
                }
            }
        };

        UpdateTwitterButton();

        mTwitterBtn.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Toast.makeText(Login.this, "Signed in to twitter successful", Toast.LENGTH_LONG).show();
                signInToFirebaseWithTwitterSession(result.data);
                mTwitterBtn.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(Login.this, "Login failed. No internet or No Twitter app found on your phone", Toast.LENGTH_LONG).show();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                UpdateTwitterButton();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTwitterBtn.onActivityResult(requestCode, resultCode, data);

    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI();
        }
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void updateUI() {
        Toast.makeText(Login.this, "You're logged in", Toast.LENGTH_LONG);
        //Sending user to new screen after successful login
        Intent mainActivity = new Intent(Login.this, Scheduler.class);
        startActivity(mainActivity);
        finish();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mAuth.removeAuthStateListener(mAuthListener);
    }
    private void UpdateTwitterButton(){
        if (TwitterCore.getInstance().getSessionManager().getActiveSession() == null){
            mTwitterBtn.setVisibility(View.VISIBLE);
        }
        else{
            mTwitterBtn.setVisibility(View.GONE);
        }
    }

    private void signInToFirebaseWithTwitterSession(TwitterSession session){
        AuthCredential credential = TwitterAuthProvider.getCredential(session.getAuthToken().token,
                session.getAuthToken().secret);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(Login.this, "Signed in firebase twitter successful", Toast.LENGTH_LONG).show();
                        if (!task.isSuccessful()){
                            Toast.makeText(Login.this, "Auth firebase twitter failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
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
                        Intent intent = new Intent(Login.this, ProviderPick.class);
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
