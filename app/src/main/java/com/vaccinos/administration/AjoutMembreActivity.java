package com.vaccinos.administration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vaccinos.R;
import com.vaccinos.accueil.AccueilActivity;
import com.vaccinos.globals.models.User;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

public class AjoutMembreActivity extends AppCompatActivity {

    private static final String TAG = "AjoutMembreActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_ajout_membre);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StockActivity = new Intent(AjoutMembreActivity.this, AdministrationMenuActivity.class);
                startActivity(StockActivity);
                finish();
            }
        });
        findViewById(R.id.buttonAjoutMembre2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

    }

    private void addUser(){
        //reccupération del'entrée
        TextView textViewNom = findViewById(R.id.editNomMembre);

        //réccupération du contenu
        String nomMembre = textViewNom.getText().toString();

        TextView textViewPrenom = findViewById(R.id.editPrenomMembre);
        String prenomMembre = textViewPrenom.getText().toString();

        TextView textViewEmail = findViewById(R.id.editEmailMembre);
        String email = textViewEmail.getText().toString();

        TextView textViewMdp = findViewById(R.id.editMotDePasseMembre);
        String mdpMembre = textViewMdp.getText().toString();

        mAuth.createUserWithEmailAndPassword(email,mdpMembre)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        User user = new User(authResult.getUser().getUid(),nomMembre, prenomMembre, email,false);
                        db.collection("user").document(authResult.getUser().getUid()).set(user);
                        Log.d(TAG, "User ajouté");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Un problème est survenu !");
                    }
                });


    }

}