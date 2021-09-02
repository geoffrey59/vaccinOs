package com.vaccinos.stock;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.vaccinos.R;
import com.vaccinos.accueil.AccueilActivity;
import com.vaccinos.globals.FirebaseVaccin;
import com.vaccinos.globals.models.Vaccin;
import com.vaccinos.planing.PlanningActivity;
import com.vaccinos.planing.PlanningAdapter;
import com.vaccinos.planing.PlanningListActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class StockActivity extends AppCompatActivity implements StockAdapter.RecyclerItemClickListener {
    private WebView webView;
    private FirebaseVaccin firebaseVaccin;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<Vaccin> vaccinList;
    private RecyclerView recycler;
    private StockAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        getSupportActionBar().hide();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("");
        getVaccin();
        initializeViews();
        vaccinList = new ArrayList<>();
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new StockAdapter(getApplicationContext(),vaccinList, StockActivity.this);
        recycler.setAdapter(mAdapter);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StockActivity = new Intent(StockActivity.this, AccueilActivity.class);
                startActivity(StockActivity);
                finish();
            }
        });

    }
    private void initializeViews(){
        recycler = findViewById(R.id.recyclerStock);
    }

    public void getVaccin(){
        db.collection("vaccin").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    vaccinList.clear();
                    vaccinList.addAll(value.toObjects(Vaccin.class));
                    mAdapter.notifyDataSetChanged();


            }
        });
    }

    @Override
    public void onClickListener(Vaccin vaccin, int position) {

    }
}