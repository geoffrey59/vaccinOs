package com.vaccinos.administration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.vaccinos.R;
import com.vaccinos.globals.models.Vaccin;
import com.vaccinos.injection.InjectionActivity;

import java.util.ArrayList;
import java.util.List;

public class ReceptionActivity extends AppCompatActivity {

    private static final String TAG = "ReceptionActivity";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private int nbDoseParFlacon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_reception);
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StockActivity = new Intent(ReceptionActivity.this, AdministrationMenuActivity.class);
                startActivity(StockActivity);
                finish();
            }
        });

        db.collection("vaccin").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<String> items_vaccin = new ArrayList<>();
                items_vaccin.add("Choisir ...");
                //On boucle sur les documents présent dans la collection vaccin, chaque document correspond donc a un vaccin
                //On les ajoutes dans la liste
                for(DocumentSnapshot vaccin: value) items_vaccin.add(vaccin.get("name").toString());
                Spinner spinner = findViewById(R.id.spinnerVaccin);
                ArrayAdapter<String> adapter_vaccin = new ArrayAdapter<>(ReceptionActivity.this, android.R.layout.simple_spinner_dropdown_item, items_vaccin);
                spinner.setAdapter(adapter_vaccin);
            }
        });
        findViewById(R.id.buttonAjoutStock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStock();
            }
        });
    }
    private void addStock() {
        Spinner spinner = findViewById(R.id.spinnerVaccin);
        String nomVaccin = spinner.getSelectedItem().toString();

        TextView textViewnbFlacon = findViewById(R.id.editNombreDeFlacons);
        String nombreDeFlacon = textViewnbFlacon.getText().toString();
        int converted = Integer.parseInt(nombreDeFlacon);


        db.collection("vaccin")
                .whereEqualTo("name",nomVaccin)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                task.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String idDoc = queryDocumentSnapshots.getDocuments().get(0).getId();
                        List<Vaccin> listVaccin = queryDocumentSnapshots.toObjects(Vaccin.class);
                        Vaccin vaccinBdd = listVaccin.get(0);
                        vaccinBdd.setNbDeFlacon(vaccinBdd.getNbDeFlacon() + converted);
                        vaccinBdd.setStock(vaccinBdd.getStock() + (converted*vaccinBdd.getNbDoseParFlacon()));
                        db.collection("vaccin").document(idDoc).set(vaccinBdd).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                task.addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ReceptionActivity.this, "Flacon ajouté avec succès !",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                                task.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ReceptionActivity.this, "Une erreur est survenue !",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });






    }}





 //   DocumentReference updateVaccin = db.collection ("vaccin").document();
   //     updateVaccin.update("nbDeFlacon", FieldValue.increment(Long.parseLong(nombreDeFlacon)));