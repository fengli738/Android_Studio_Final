package com.example.afinal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Scheduler_view extends AppCompatActivity {
    DatabaseReference reference;
    String _USERNAME, Provider;
    private RecyclerView recyclerView;
    private ScheduleReader reader;
    private List<Date> dateList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        Intent intent = getIntent();
        _USERNAME = intent.getStringExtra("username");
        Provider = intent.getStringExtra("provider");
        String identify = _USERNAME + "_" + Provider;

        recyclerView = findViewById(R.id.date_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dateList = new ArrayList<>();
        reader = new ScheduleReader(this, dateList);
        recyclerView.setAdapter(reader);

        // SELECT FROM Scheduler
        reference = FirebaseDatabase.getInstance().getReference("scheduler");
        // SELECT FROM USER
        Query query = FirebaseDatabase.getInstance().getReference("scheduler")
                .orderByChild("identify").equalTo(identify);
        query.addListenerForSingleValueEvent(valueEventListener);

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            dateList.clear();
            if (dataSnapshot.exists()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Date date = snapshot.getValue(Date.class);
                    dateList.add(date);
                }
                reader.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };






}
