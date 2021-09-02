package com.vaccinos.globals;

import android.provider.Settings;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Document;

public class FirebaseVaccin {

    class Snapshot{
        DocumentSnapshot snapshot;

        public DocumentSnapshot getSnapshot() {
            return snapshot;
        }

        public void setSnapshot(DocumentSnapshot snapshot) {
            this.snapshot = snapshot;
        }
    }

    private final FirebaseFirestore db;
    final Snapshot snapshot = new Snapshot();

    public FirebaseVaccin(){
        this.db = FirebaseFirestore.getInstance();
    }

    public void getVaccin(String nomVaccin){
        db.collection("vaccin").document(nomVaccin).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){

                }else{
                    snapshot.setSnapshot(value);
                }
            }

        });
        System.out.println("-------------------------------------");
        System.out.println(snapshot.getSnapshot());
    }

}

