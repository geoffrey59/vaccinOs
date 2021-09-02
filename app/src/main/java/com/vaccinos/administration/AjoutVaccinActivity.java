package com.vaccinos.administration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vaccinos.R;
import com.vaccinos.accueil.AccueilActivity;
import com.vaccinos.connexion.MainActivity;
import com.vaccinos.globals.models.Vaccin;

public class AjoutVaccinActivity extends AppCompatActivity {

    private String TAG = "AjoutVaccinActivity";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText et_nomVaccin;
    EditText et_nbDoseParFlacon;
    Button btnAjoutVaccin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_ajout_vaccin);
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StockActivity = new Intent(AjoutVaccinActivity.this, AdministrationMenuActivity.class);
                startActivity(StockActivity);
                finish();
            }
        });
        btnAjoutVaccin = findViewById(R.id.buttonAjoutVaccin);
        btnAjoutVaccin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajoutVaccin();
            }
        });

    }

    private void ajoutVaccin(){
        et_nbDoseParFlacon = findViewById(R.id.editNombreDeDoseParFlacon);
        et_nomVaccin = findViewById(R.id.editNomVaccin);

        String nomVaccin =et_nomVaccin.getText().toString();
        int nbDoseParFlacon = Integer.parseInt(et_nbDoseParFlacon.getText().toString());

       Vaccin vaccin = new Vaccin(nomVaccin,0,nbDoseParFlacon,0,0);

        db.collection("vaccin").document().set(vaccin).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                task.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AjoutVaccinActivity.this, "Nouveau vaccin ajouté",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AjoutVaccinActivity.this, "Une erreur est survenue.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}