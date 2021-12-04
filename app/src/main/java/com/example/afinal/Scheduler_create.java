package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Scheduler_create extends AppCompatActivity {

    Button Btn, Btn_pick, addReason, back_schedule;
    String _USERNAME, Provider;
    String date1;
    String reason1;
    TextView dateSelect;
    EditText reasonType;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private String blockCharacterSet = "/~#^|$%&*!";

    private InputFilter filter = new InputFilter() {


        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_create_layout);

        Btn = findViewById(R.id.button3);
        Btn_pick = findViewById(R.id.calendar_date_pick);
        addReason = findViewById(R.id.addReason);
        dateSelect = findViewById(R.id.calendar_date);
        reasonType = findViewById(R.id.editTextTextReason);
        back_schedule = findViewById(R.id.button_back_schedule);
        Btn.setEnabled(false);


        Intent intent = getIntent();
        _USERNAME = intent.getStringExtra("username");
        Provider = intent.getStringExtra("provider");
        date1 = intent.getStringExtra("date");
        reasonType.setText(reason1);
        reasonType.setFilters(new InputFilter[] { filter });

        dateSelect.setText(date1);
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("scheduler");

                String reason = reason1;
                String date = date1;
                String username = _USERNAME;
                String provider = Provider;
                String identify = _USERNAME + "_" +Provider;

                SchedulerHelper schedulerHelper = new SchedulerHelper(date, reason, username, provider, identify);
                reference.child(reason).setValue(schedulerHelper);

                Toast.makeText(Scheduler_create.this, "Appointment Added",
                        Toast.LENGTH_LONG).show();

            }
        });

        Btn_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Scheduler_create.this, Calendar_picker.class);
                intent1.putExtra("username", _USERNAME);
                intent1.putExtra("provider", Provider);
                startActivity(intent1);
            }
        });

        addReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reason1 = reasonType.getText().toString();
                if(reason1.matches("")){
                    Toast.makeText(Scheduler_create.this, "Nothing added please add again",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(Scheduler_create.this, "Reason added",
                            Toast.LENGTH_LONG).show();
                    Btn.setEnabled(true);
                }
            }
        });

        back_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Scheduler_create.this, Scheduler.class);
                intent2.putExtra("username", _USERNAME);
                intent2.putExtra("provider", Provider);
                startActivity(intent2);
            }
        });


    }
}
