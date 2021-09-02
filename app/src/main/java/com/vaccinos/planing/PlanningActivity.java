package com.vaccinos.planing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import com.google.firebase.Timestamp;
import com.vaccinos.R;
import com.vaccinos.accueil.AccueilActivity;
import com.vaccinos.injection.InjectionActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlanningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_planning);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StockActivity = new Intent(PlanningActivity.this, AccueilActivity.class);
                startActivity(StockActivity);
                finish();
            }
        });
        //On récupère la date qui est choisi par l'utilisateur
        CalendarView calendar = findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                String date = String.valueOf(dayOfMonth) + "/"+String.valueOf(month+1) + "/"+String.valueOf(year) ;

                Intent injectionActivity = new Intent(PlanningActivity.this, PlanningListActivity.class );
                injectionActivity.putExtra("dateInjectionClient",date);
                startActivity(injectionActivity);
        }

       });
    }
}