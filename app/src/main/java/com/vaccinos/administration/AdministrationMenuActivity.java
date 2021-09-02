package com.vaccinos.administration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.vaccinos.R;
import com.vaccinos.accueil.AccueilActivity;
import com.vaccinos.planing.PlanningActivity;
import com.vaccinos.planing.PlanningListActivity;

public class AdministrationMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_administration_menu);

        findViewById(R.id.buttonAjouterSoignant).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AjoutSoignantActivity = new Intent(AdministrationMenuActivity.this, AjoutSoignantActivity.class);
                startActivity(AjoutSoignantActivity);
            }
        });

        findViewById(R.id.buttonAjouterVaccin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AjoutVaccinActivity = new Intent(AdministrationMenuActivity.this, AjoutVaccinActivity.class);
                startActivity(AjoutVaccinActivity);
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StockActivity = new Intent(AdministrationMenuActivity.this, AccueilActivity.class);
                startActivity(StockActivity);
                finish();
            }
        });

        findViewById(R.id.buttonAjoutMembre).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AjoutMembreActivity = new Intent (AdministrationMenuActivity.this, AjoutMembreActivity.class);
                startActivity(AjoutMembreActivity);
            }
        });

        findViewById(R.id.buttonSuppMembre).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SuppMembreActivity = new Intent (AdministrationMenuActivity.this, SuppMembreActivity.class);
                startActivity(SuppMembreActivity);
            }
        });

        findViewById(R.id.buttonReception).setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent receptionActivity = new Intent( AdministrationMenuActivity.this, ReceptionActivity.class);
                startActivity(receptionActivity);
            }
        }));










    }

}