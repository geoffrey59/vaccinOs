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
import com.vaccinos.globals.models.Soignant;

public class AjoutSoignantActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button ajoutSoignant;
    private EditText et_nomSoignant;
    private EditText et_prenomSoignant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_ajout_soignant);
        initialiseView();
        ajoutSoignant = findViewById(R.id.buttonAjoutSoignant);
        ajoutSoignant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSoignant();
            }
        });
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StockActivity = new Intent(AjoutSoignantActivity.this, AdministrationMenuActivity.class);
                startActivity(StockActivity);
                finish();
            }
        });
    }
    private void initialiseView(){
        ajoutSoignant = findViewById(R.id.buttonAjoutSoignant);
        et_nomSoignant = findViewById(R.id.editNomSoignant);
        et_prenomSoignant = findViewById(R.id.editPrenomSoignant);
    }
    private void addSoignant(){
        String nomSoignant = et_nomSoignant.getText().toString();
        String prenomSoignant = et_prenomSoignant.getText().toString();
        Soignant soignant = new Soignant(nomSoignant,prenomSoignant);

        db.collection("soignant").document().set(soignant).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                task.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AjoutSoignantActivity.this, "Nouveau soignant ajouté",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AjoutSoignantActivity.this, "Un problème est survenue",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }
}