package com.vaccinos.stock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vaccinos.R;
import com.vaccinos.globals.models.Injection;
import com.vaccinos.globals.models.Vaccin;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {

    private Context context;
    private ArrayList<Vaccin> vaccinList;
    private RecyclerItemClickListener listener;


    public StockAdapter(Context context, ArrayList<Vaccin> vaccinList, RecyclerItemClickListener listener){
        this.context = context;
        this.vaccinList = vaccinList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_inflater,parent,false);
        return new StockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {
        Vaccin vaccin = vaccinList.get(position);
        if(vaccin != null){
            holder.nomVaccin.setText(context.getResources().getString(R.string.text_nom_stock)+ " : " + vaccin.getName());
            holder.nbDoseVaccin.setText(String.valueOf(vaccin.getStock()));


            holder.bind(vaccin,listener);
        }
    }

    @Override
    public int getItemCount() {
        return vaccinList.size();
    }

    public static class StockViewHolder extends RecyclerView.ViewHolder{

        private TextView nomVaccin;
        private TextView nbDoseVaccin;

        public StockViewHolder(@NonNull View itemView) {
            super(itemView);
            nomVaccin = (TextView) itemView.findViewById(R.id.nom_vaccin_stock);
            nbDoseVaccin = (TextView) itemView.findViewById(R.id.nb_dose);

        }

        public void bind(final Vaccin vaccin, final RecyclerItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(vaccin, getLayoutPosition());

                }
            });
        }

    }

    public interface RecyclerItemClickListener{
        void onClickListener(Vaccin vaccin, int position);
    }
}
