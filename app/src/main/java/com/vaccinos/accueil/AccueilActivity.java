package com.vaccinos.accueil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.vaccinos.R;
import com.vaccinos.administration.AdministrationMenuActivity;
import com.vaccinos.connexion.MainActivity;
import com.vaccinos.injection.InjectionActivity;
import com.vaccinos.planing.PlanningActivity;
import com.vaccinos.stock.StockActivity;


public class AccueilActivity extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private WebView webView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_accueil);
         getSupportActionBar().hide();
        this.getSupportActionBar().setTitle("");

        db.collection("user").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                TextView textName = findViewById(R.id.textView3);
                String name;
                if(documentSnapshot == null){
                    name = getResources().getString(R.string.namee);
                }else{
                    name = getResources().getString(R.string.namee,documentSnapshot.get("prenom").toString());

                }

                textName.setText(name);
            }
        });


        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://www.nathanlemaitre.fr/patientos/");


        /* On récupère le bouton avec son id et on écoute si l'utilisateur clique dessus, quand il clique on l'envoie vers l'activité StockActivity */
        findViewById(R.id.buttonStock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StockActivity = new Intent(AccueilActivity.this, StockActivity.class);
                startActivity(StockActivity);
            }
        });
        /* On récupère le bouton avec son id et on écoute si l'utilisateur clique dessus, quand il clique on l'envoie vers l'activité InjectionActivity*/
        findViewById(R.id.buttonInjection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent InjectionActivity = new Intent(AccueilActivity.this, InjectionActivity   .class );
                startActivity(InjectionActivity);
            }
        });
        /* On récupère le bouton avec son id et on écoute si l'utilisateur clique dessus, quand il clique on l'envoie vers l'activité InjectionActivity*/
        findViewById(R.id.buttonVoirInjection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent InjectionVoirActivity = new Intent(AccueilActivity.this, PlanningActivity.class );
                startActivity(InjectionVoirActivity);
            }
        });
        /* On récupère le bouton avec son id et on écoute si l'utilisateur clique dessus si il clique on l'envoie vers l'activité AdministrationMenuActivity */
        findViewById(R.id.btnAdminMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("groooooooooooooossssssssssssss nibard");
                Intent AdministrationMenuActivity = new Intent(AccueilActivity.this, AdministrationMenuActivity.class );
                startActivity(AdministrationMenuActivity);
            }
        });
        findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent ConnexionActivity = new Intent(AccueilActivity.this, MainActivity.class );
                startActivity(ConnexionActivity);
                finish();
            }
        });




    }




}