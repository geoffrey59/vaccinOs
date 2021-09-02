package com.vaccinos.administration;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vaccinos.R;
import com.vaccinos.globals.models.User;
import com.vaccinos.globals.models.Vaccin;

import java.util.ArrayList;

public class SuppMembreAdapter extends RecyclerView.Adapter<SuppMembreAdapter.SuppMembreHolder> {

    private Context context;
    private ArrayList<User> userList;
    private SuppMembreAdapter.RecyclerItemClickListener listener;


    public SuppMembreAdapter(Context context, ArrayList<User> userList,SuppMembreAdapter.RecyclerItemClickListener listener){
        this.context = context;
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SuppMembreAdapter.SuppMembreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.membre_inflater,parent,false);
        return new SuppMembreAdapter.SuppMembreHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuppMembreAdapter.SuppMembreHolder holder, int position) {
        User user = userList.get(position);
        if(user != null){
            holder.nomPrenomMembre.setText(user.getNom() + " " + user.getPrenom());
            holder.mailMembre.setText(String.valueOf(user.getEmail()));

            holder.bind(user,listener);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class SuppMembreHolder extends RecyclerView.ViewHolder{

        private TextView nomPrenomMembre;
        private TextView mailMembre;

        public SuppMembreHolder(@NonNull View itemView) {
            super(itemView);
            nomPrenomMembre = (TextView) itemView.findViewById(R.id.infoMembre);
            mailMembre = (TextView) itemView.findViewById(R.id.mailMembre);

        }

        public void bind(final User user, final SuppMembreAdapter.RecyclerItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(user, getLayoutPosition());
                }
            });
        }

    }

    public interface RecyclerItemClickListener{
        void onClickListener(User user, int position);
    }
}
