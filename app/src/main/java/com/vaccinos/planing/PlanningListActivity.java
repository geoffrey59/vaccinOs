package com.vaccinos.planing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.Lists;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.vaccinos.R;
import com.vaccinos.accueil.AccueilActivity;
import com.vaccinos.globals.models.Injection;

import java.sql.Array;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlanningListActivity extends AppCompatActivity implements PlanningAdapter.RecyclerItemClickListener {

    private String TAG = "PlanningListActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recycler;
    private ArrayList<Injection> injectionlist;
    private PlanningAdapter mAdapter;
    private TextView textDateHaut,textDateBas;
    private ImageView btnDateMoins,btnDatePlus;
    private String dateAfficher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_planning_list);
        Intent intent = getIntent();
        dateAfficher = intent.getStringExtra("dateInjectionClient");

        initializeViews();
        getInjectionByDate();
        injectionlist = new ArrayList<>();
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new PlanningAdapter(getApplicationContext(),injectionlist, PlanningListActivity.this);
        recycler.setAdapter(mAdapter);
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StockActivity = new Intent(PlanningListActivity.this, PlanningActivity.class);
                startActivity(StockActivity);
                finish();
            }
        });
        btnDateMoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDate(false);
            }
        });
        btnDatePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDate(true);
            }
        });
    }

    //fonction permettant d'initialiser la vue
    private void initializeViews(){
        recycler = findViewById(R.id.recyclerInjection);

        textDateHaut = findViewById(R.id.text_date_planing_haut);
        btnDateMoins = findViewById(R.id.btnMoinsDate);
        btnDatePlus = findViewById(R.id.btnPlusDate);
    }

    //fonction permettant de récupérer une liste d'injection par rapport à la date
    private void getInjectionByDate(){
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yy");
        //date transmise depuis le planning
        //textDateBas.setText(dateAfficher);
        textDateHaut.setText(dateAfficher);
        Date dateDebutClique = null;
        Date dateFinClique = null;
        try {
            dateDebutClique = formatDate.parse(dateAfficher);
            dateFinClique = formatDate.parse(dateAfficher);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dateDebutClique.setTime(dateDebutClique.getTime() + 3600000);
        dateFinClique.setTime(dateFinClique.getTime() + 3600000 + 86390000);

        Query query = db.collection("injection")
                .whereLessThan("dateDebut",new Timestamp(dateFinClique))
                .whereGreaterThan("dateDebut",new Timestamp(dateDebutClique));
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                injectionlist.clear();
                injectionlist.addAll(value.toObjects(Injection.class));
                mAdapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    public void onClickListener(Injection injection, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlanningListActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Annulation de rendez-vous");
        builder.setMessage("Voulez vous annulez le rendez vous pour l'injection de :" + injection.getNomPatient() + " " + injection.getPrenomPatient() + ", prévu le : " + injection.getDateInjection() + " lors du créneau : " + injection.getCreneau()  );
        builder.setPositiveButton("Oui",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.collection("injection").document(injection.getRef()).delete();
                        dialog.cancel();

                    }
                });
        builder.setNegativeButton("Non",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                 });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void changeDate(boolean plus){
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yy");
        //date transmise depuis le planning

        Date dateCliqueDate = null;
        try {
            dateCliqueDate = formatDate.parse(dateAfficher);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        String strDateSuivant;
        dateCliqueDate.setTime(dateCliqueDate.getTime() + 3600000);
        if(plus){
            dateCliqueDate.setTime(dateCliqueDate.getTime() + 86400000 );
            strDateSuivant = dateFormat.format(dateCliqueDate);
        }else{
            dateCliqueDate.setTime(dateCliqueDate.getTime() - 86400000 );
            strDateSuivant = dateFormat.format(dateCliqueDate);
        }
        dateAfficher = strDateSuivant;
        getInjectionByDate();


    }
}