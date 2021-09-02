package com.vaccinos.injection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.vaccinos.R;
import com.vaccinos.accueil.AccueilActivity;
import com.vaccinos.globals.models.Injection;
import com.vaccinos.globals.models.Soignant;
import com.vaccinos.stock.StockActivity;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InjectionActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView datePicker;
    private DatePickerDialog.OnDateSetListener dateListener;
    private String dateInjection;
    private Soignant soignant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injection);
        getSupportActionBar().hide();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("");
        Spinner dropdown = findViewById(R.id.spinnerHoraire);
        Spinner spinnerMedecin = findViewById(R.id.spinnerMedecin);

        datePicker = (TextView) findViewById(R.id.choixDate);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(InjectionActivity.this, android.R.style.Theme_Holo_Light_Dialog, dateListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateListener = (view, year, month, dayOfMonth) -> {
            dateInjection = String.valueOf(dayOfMonth) + "/" + String.valueOf(month+1) + "/" + String.valueOf(year);
            datePicker.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(month+1) + "/" + String.valueOf(year));
        };
        //On écoute dans la base de données les informations de la collection vaccin afin de les afficher sur l'app
        db.collection("vaccin").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<String> items_vaccin = new ArrayList<>();
                items_vaccin.add("Choisir ...");
                //On boucle sur les documents présent dans la collection vaccin, chaque document correspond donc a un vaccin
                for(DocumentSnapshot vaccin: value){
                    //On les ajoutes dans la liste
                    items_vaccin.add(vaccin.get("name").toString());
                }
                Spinner dropdown_vaccin = findViewById(R.id.spinnerInjection);
                ArrayAdapter<String> adapter_vaccin = new ArrayAdapter<>(InjectionActivity.this, android.R.layout.simple_spinner_dropdown_item, items_vaccin);
                dropdown_vaccin.setAdapter(adapter_vaccin);
            }
        });

        String[] items = new String[]{"Choisir...","08H00-08H05", "08H05-08H10", "08H10-08H15", "08H15-08H20", "08H20-08H25", "08H25-08H30","08H30-08H35", "08H35-08H40", "08H40-08H45", "08H45-08H50", "08H50-08H55", "08H55-09H00",
                "09H00-09H05", "09H05-09H10", "09H10-09H15", "09H15-09H20", "09H20-09H25", "09H25-09H30","09H30-09H35", "09H35-09H40", "09H40-09H45", "09H45-09H50", "09H50-09H55", "09H55-10H00",
                "10H00-10H05", "10H05-10H10", "10H10-10H15", "10H15-10H20", "10H20-10H25", "10H25-10H30","10H30-10H35", "10H35-10H40", "10H40-10H45", "10H45-10H50", "10H50-10H55", "10H55-11H00",
                "11H00-11H05", "11H05-10H10", "11H10-10H15", "11H15-11H20", "11H20-11H25", "11H25-11H30","11H30-11H35", "11H35-11H40", "11H40-11H45", "11H45-11H50", "11H50-11H55", "11H55-12H00",
                "12H00-12H05", "12H05-12H10", "12H10-12H15", "12H15-12H20", "12H20-12H25", "12H25-12H30","12H30-12H35", "12H35-12H40", "12H40-12H45", "12H45-12H50", "12H50-12H55", "12H55-13H00",
                "13H00-13H05", "13H05-10H10", "13H10-13H15", "13H15-13H20", "13H20-13H25", "13H25-13H30","13H30-13H35", "13H35-13H40", "13H40-13H45", "13H45-13H50", "13H50-13H55", "13H55-14H00",
                "14H00-14H05", "14H05-10H10", "14H10-14H15", "14H15-14H20", "14H20-14H25", "14H25-14H30","14H30-14H35", "14H35-14H40", "14H40-14H45", "14H45-14H50", "14H50-14H55", "14H55-15H00",
                "15H00-15H05", "15H05-10H10", "15H10-15H15", "15H15-15H20", "15H20-15H25", "15H25-15H30","15H30-15H35", "15H35-15H40", "15H40-15H45", "15H45-15H50", "15H50-15H55", "15H55-16H00",
                "16H00-16H05", "16H05-10H10", "16H10-16H15", "16H15-16H20", "16H20-16H25", "16H25-16H30","16H30-16H35", "16H35-16H40", "16H40-16H45", "16H45-16H50", "16H50-16H55", "16H55-17H00",
                "17H00-17H05", "17H05-10H10", "17H10-17H15", "17H15-17H20", "17H20-17H25", "17H25-17H30","17H30-17H35", "17H35-17H40", "17H40-17H45", "17H45-17H50", "17H50-17H55", "17H55-18H00",
                "18H00-18H05", "18H05-10H10", "18H10-18H15", "18H15-18H20", "18H20-18H25", "18H25-18H30","18H30-18H35", "18H35-18H40", "18H40-18H45", "18H45-18H50", "18H50-18H55", "18H55-19H00",
                "19H00-19H05", "19H05-10H10", "19H10-19H15", "19H15-19H20", "19H20-19H25", "19H25-19H30","19H30-19H35", "19H35-19H40", "19H40-19H45", "19H45-19H50", "19H50-19H55", "19H55-20H00",

        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);



        db.collection("soignant").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Soignant> itemsoignant = new ArrayList<Soignant>();
                itemsoignant.addAll(value.toObjects(Soignant.class));
                ArrayAdapter<Soignant> adapterMedecin = new ArrayAdapter<Soignant>(InjectionActivity.this, android.R.layout.simple_spinner_dropdown_item,itemsoignant);
                spinnerMedecin.setAdapter(adapterMedecin);
            }
        });





        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StockActivity = new Intent(InjectionActivity.this, AccueilActivity.class);
                startActivity(StockActivity);
                finish();
            }
        });

        spinnerMedecin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                soignant = (Soignant) parent.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        findViewById(R.id.buttonInjectionValid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinnerInjection = (Spinner) findViewById(R.id.spinnerInjection);
                Spinner spinnerCreneau = (Spinner) findViewById(R.id.spinnerHoraire);

                EditText nomPatient = (EditText) findViewById(R.id.editNomPatient);
                EditText prenomPatient = findViewById(R.id.editPrenompatient);
                EditText mailPatient = findViewById(R.id.editMailPatient);
                EditText telPatient = findViewById(R.id.editTelPatient);
                Spinner medecinSpinner = findViewById(R.id.spinnerMedecin);

                String heureDebutH = spinnerCreneau.getSelectedItem().toString().substring(0, 5);
                String heureFinH = spinnerCreneau.getSelectedItem().toString().substring(6, 11);

                String heureDebut = heureDebutH.substring(0, 2) + "-" + heureDebutH.substring(3, 5);
                String heureFin = heureFinH.substring(0, 2) + "-" + heureFinH.substring(3, 5);

                String dateDebutH = dateInjection + " " + heureDebut;
                String dateFinH = dateInjection + " " + heureFin;
                String dateSimpleH = dateInjection;
                SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yy HH-mm");
                SimpleDateFormat formatDateSingle = new SimpleDateFormat("dd/MM/yy");

                Date dateDebut = null;
                Date dateFin = null;
                Date dateSimple = null;
                try {
                    dateDebut = formatDate.parse(dateDebutH);
                    dateFin = formatDate.parse(dateFinH);
                    dateSimple = formatDateSingle.parse(dateSimpleH);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Timestamp dateDebutTimestamp = new Timestamp(dateDebut);
                Timestamp dateFinTimestamp = new Timestamp(dateFin);

                Injection injectionPatient = new Injection(nomPatient.getText().toString(), prenomPatient.getText().toString(), mailPatient.getText().toString(), telPatient.getText().toString(), spinnerCreneau.getSelectedItem().toString(), spinnerInjection.getSelectedItem().toString(), dateDebutTimestamp, dateFinTimestamp, dateSimple, soignant.getNomSoignant() + " " + soignant.getPrenomSoignant());
                db.collection("injection")
                        .whereEqualTo("medecin", soignant.getNomSoignant() + " " + soignant.getPrenomSoignant())
                        .whereEqualTo("dateDebut", dateDebutTimestamp)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().getDocuments().isEmpty()) {
                                db.collection("vaccin")
                                        .whereEqualTo("name", spinnerInjection.getSelectedItem().toString())
                                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        String stock = task.getResult().getDocuments().get(0).get("stock").toString();
                                        int nbDoseUtilise =Integer.parseInt(task.getResult().getDocuments().get(0).get("nbDoseUtilise").toString()) ;
                                        int nbDoseParFlacon =Integer.parseInt(task.getResult().getDocuments().get(0).get("nbDoseParFlacon").toString()) ;
                                        if (Integer.valueOf(stock) != 0) {
                                            db.collection("injection").add(injectionPatient).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    task.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            documentReference.update("ref", documentReference.getId());
                                                        }
                                                    });
                                                }
                                            });
                                            Toast.makeText(InjectionActivity.this, "Injection enregistrée", Toast.LENGTH_SHORT).show();
                                            db.collection("vaccin").document(task.getResult().getDocuments().get(0).getId()).update(
                                                    "stock", Integer.valueOf(stock) - 1,
                                                    "nbDoseUtilise", nbDoseUtilise+1,
                                                    "nbFlacon",(Integer.valueOf(stock)-1)/nbDoseParFlacon
                                            );
                                        } else {
                                            Toast.makeText(InjectionActivity.this, "Plus de dose disponible", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(InjectionActivity.this, "Rendez vous déjà utilisé", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


            }

        });



    }
}