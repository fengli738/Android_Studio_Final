package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ProviderPick extends AppCompatActivity {

    ImageView ig1, ig2;
    String provider1 = "Utility";
    String provider2 = "Houseclean";
    String _USERNAME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_layout);

        ig1 = findViewById(R.id.imageView);
        ig2 = findViewById(R.id.imageView2);
        Intent intent = getIntent();
        _USERNAME = intent.getStringExtra("username");

        ig1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(ProviderPick.this, Scheduler.class);
                i1.putExtra("provider", provider1);
                i1.putExtra("username", _USERNAME);
                startActivity(i1);
            }
        });
        ig2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(ProviderPick.this, Scheduler.class);
                i2.putExtra("provider", provider2);
                i2.putExtra("username", _USERNAME);
                startActivity(i2);
            }
        });
    }
}
