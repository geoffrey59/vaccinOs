package com.vaccinos.connexion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.vaccinos.R;
import com.vaccinos.accueil.AccueilActivity;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText username;
    private EditText motDePasse;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!= null){
            FirebaseUser currentUser = auth.getCurrentUser();
            if(!currentUser.isAnonymous()){
                Intent acceuilActivity = new Intent(MainActivity.this, AccueilActivity.class);
                startActivity(acceuilActivity);
                super.finish();
            }
        }
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        username = findViewById(R.id.editText2);
        motDePasse = findViewById(R.id.editText);
        Button buttonConnexion = findViewById(R.id.buttonConnexion);
        buttonConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connexion();
            }
        });
    }

    private void connexion() {


        String usernameVal = username.getText().toString();
        String motDePasseVal = motDePasse.getText().toString();
        if (!TextUtils.isEmpty(usernameVal) && !TextUtils.isEmpty(motDePasseVal)) {
            Log.d("MainActivity", "connexion de l'utilisateur.");
            auth.signInWithEmailAndPassword(usernameVal, motDePasseVal)
            .addOnCompleteListener( task ->{
                if (task.isSuccessful()) {
                    String userid = task.getResult().getUser().getUid();
                    db.collection("user").document(userid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(Boolean.parseBoolean(documentSnapshot.get("delete").toString()) == true){
                                task.getResult().getUser().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(MainActivity.this, "Echec de l'authentication.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                                db.collection("user").document(userid).delete();
                            }else{
                                Log.d("MainActivity", "Connexion de l'utilisateur avec son email : Succès");
                                Intent acceuilActivity = new Intent(MainActivity.this, AccueilActivity.class);
                                startActivity(acceuilActivity);
                                MainActivity.super.finish();
                            }
                        }
                    });
                    // Si l'authentification a réussi, on met à jour les informations de l'utilisateur
                } else {
                    // Si l'authentification a échouée, on affiche un message à l'utilisateur
                    Log.e("MainActivity", "Connexion de l'utilisateur avec son email : Echec", task.getException());
                    Toast.makeText(MainActivity.this, "Echec de l'authentication.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Entrer les détails", Toast.LENGTH_SHORT).show();
        }
    }
}