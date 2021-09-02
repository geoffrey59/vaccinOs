package com.vaccinos.administration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.vaccinos.R;
import com.vaccinos.globals.models.User;
import com.vaccinos.planing.PlanningAdapter;
import com.vaccinos.planing.PlanningListActivity;

import java.util.ArrayList;

public class SuppMembreActivity extends AppCompatActivity implements SuppMembreAdapter.RecyclerItemClickListener {

    private static String TAG = "SuppMembreActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recycler;
    private SuppMembreAdapter mAdapter;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private ArrayList<User> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supp_membre);
        getSupportActionBar().hide();
        usersList = new ArrayList<>();
        initializeView();
        getMembres();
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new SuppMembreAdapter(getApplicationContext(),usersList, SuppMembreActivity.this);
        recycler.setAdapter(mAdapter);
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StockActivity = new Intent(SuppMembreActivity.this, AdministrationMenuActivity.class);
                startActivity(StockActivity);
                finish();
            }
        });

    }
    public void initializeView(){
        recycler = findViewById(R.id.recyclerMembre);
    }

    public void getMembres(){
        db.collection("user").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                usersList.clear();
                usersList.addAll(value.toObjects(User.class)) ;
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onClickListener(User user, int position) {
        db.collection("user").document(user.getRef()).update(
                "delete", true
        ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                task.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SuppMembreActivity.this, user.getPrenom() + " a été supprimé !", Toast.LENGTH_SHORT).show();
                    }
                });
                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SuppMembreActivity.this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}