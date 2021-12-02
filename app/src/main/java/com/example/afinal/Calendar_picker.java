package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Calendar_picker extends AppCompatActivity {

    private CalendarView mCalendarView;
    String _USERNAME, Provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_pick);
        mCalendarView = (CalendarView) findViewById(R.id.calendar_date_picker);
        Intent intent = getIntent();
        _USERNAME = intent.getStringExtra("username");
        Provider = intent.getStringExtra("provider");

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = (i1 + 1) + "/" + i2 + "/" + i;

                Intent intent = new Intent(Calendar_picker.this, Scheduler_create.class);
                intent.putExtra("date", date);
                intent.putExtra("username", _USERNAME);
                intent.putExtra("provider", Provider);
                startActivity(intent);

            }
        });

    }


}
