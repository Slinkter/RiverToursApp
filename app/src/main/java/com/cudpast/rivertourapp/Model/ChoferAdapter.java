package com.cudpast.rivertourapp.Model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cudpast.rivertourapp.R;

import java.util.List;

public class ChoferAdapter extends RecyclerView.Adapter<ChoferAdapter.CustomViewHolder> {

    private List<Chofer> chofers;

    public ChoferAdapter(List<Chofer> chofers) {
        this.chofers = chofers;
    }

    //
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chofer_list, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Chofer chofer = chofers.get(position);
        holder.nameChofer.setText(chofer.getNameChofer() + " " + chofer.getLastChofer());
        holder.lastChofer.setText("2019");
        holder.dniChofer.setText("DNI : " + chofer.getDniChofer());
        holder.brevete.setText("Brevete : " + chofer.getBrevete());
        holder.numphone.setText("Celular  : " + chofer.getNumphone());
    }

    @Override
    public int getItemCount() {
        return chofers.size();
    }

    //
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView nameChofer, dniChofer, lastChofer, brevete, numphone;

        public CustomViewHolder(@NonNull View view) {
            super(view);
            nameChofer = (TextView) view.findViewById(R.id.nameChofer);
            lastChofer = (TextView) view.findViewById(R.id.lastChofer);
            dniChofer = (TextView) view.findViewById(R.id.dniChofer);
            brevete = (TextView) view.findViewById(R.id.brevete);
            numphone = (TextView) view.findViewById(R.id.numphone);

        }
    }

}
