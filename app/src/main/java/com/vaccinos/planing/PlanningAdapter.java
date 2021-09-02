package com.vaccinos.planing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatCallback;
import androidx.recyclerview.widget.RecyclerView;

import com.vaccinos.R;
import com.vaccinos.globals.models.Injection;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PlanningAdapter extends RecyclerView.Adapter<PlanningAdapter.PlanningViewHolder>  {

    private Context context;
    private ArrayList<Injection> injectionList;
    private RecyclerItemClickListener listener;


    public PlanningAdapter(Context context, ArrayList<Injection> injectionList, RecyclerItemClickListener listener){
        this.context = context;
        this.injectionList = injectionList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlanningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rendezvous_inflater,parent,false);
        return new PlanningViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanningViewHolder holder, int position) {
        Injection injection = injectionList.get(position);
        if(injection != null){

            holder.nomPatient.setText( injection.getNomPatient() + " " + injection.getPrenomPatient());
            holder.creneauPatient.setText(context.getResources().getString(R.string.creneau_patient_paning) + " " + injection.getCreneau());
            holder.infoSoignantPatient.setText(context.getResources().getString(R.string.soignant_planning) + " " + injection.getMedecin());
            holder.vaccinPatient.setText(context.getResources().getString(R.string.vaccin_planning) + " " + injection.getVaccin());

            holder.bind(injection,listener);
        }
    }

    @Override
    public int getItemCount() {
        return injectionList.size();
    }

    public static class PlanningViewHolder extends RecyclerView.ViewHolder{

        private TextView nomPatient;
        private TextView creneauPatient;
        private TextView infoSoignantPatient;
        private TextView vaccinPatient;
        public PlanningViewHolder(@NonNull View itemView) {
            super(itemView);
            nomPatient = (TextView) itemView.findViewById(R.id.infoPatient);
            creneauPatient = (TextView) itemView.findViewById(R.id.infoCreneauPatient);
            infoSoignantPatient = (TextView) itemView.findViewById(R.id.infoSoignantPatient);
            vaccinPatient = (TextView) itemView.findViewById(R.id.infoVaccinPatient);


        }

        public void bind(final Injection injection, final RecyclerItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(injection, getLayoutPosition());

                }
            });
        }

    }

    public interface RecyclerItemClickListener{
        void onClickListener(Injection injection, int position);
    }
}
