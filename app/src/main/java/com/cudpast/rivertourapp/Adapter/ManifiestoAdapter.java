package com.cudpast.rivertourapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cudpast.rivertourapp.Model.Manifiesto;
import com.cudpast.rivertourapp.R;

import java.util.List;

public class ManifiestoAdapter extends RecyclerView.Adapter<ManifiestoAdapter.customRVManifiesto> {
    //.Variable
    private List<Manifiesto> mListManifiesto;

    //.Constructor
    public ManifiestoAdapter(List<Manifiesto> mListManifiesto) {
        this.mListManifiesto = mListManifiesto;
    }


    //.Clase RecyclerView
    public class customRVManifiesto extends RecyclerView.ViewHolder {

        TextView idGuiaMani, fechaMani, destinoMani, vehiculoMani, choferMani;
        Button btn_listPasajero;

        public customRVManifiesto(@NonNull View itemView) {
            super(itemView);
            idGuiaMani = itemView.findViewById(R.id.it_idGuia);
            fechaMani = itemView.findViewById(R.id.it_fechaGuia);
            destinoMani = itemView.findViewById(R.id.it_destinoMani);
            vehiculoMani = itemView.findViewById(R.id.it_vehiculoMani);
            choferMani = itemView.findViewById(R.id.it_choferMAni);
            //      btn_listPasajero = itemView.findViewById(R.id.it_buttonListPasajero);

        }
    }
    //.Clase RecyclerView

    @NonNull
    @Override
    public customRVManifiesto onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_manifiesto, viewGroup, false);
        return new customRVManifiesto(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull customRVManifiesto holder, int position) {
        Manifiesto manifiesto = mListManifiesto.get(position);
        holder.idGuiaMani.setText("Guía :" + manifiesto.getIdGuiaMani());
        holder.fechaMani.setText("Fecha :" + manifiesto.getFechaMani());
        holder.destinoMani.setText("Destino : " + manifiesto.getDestinoMani());
        holder.vehiculoMani.setText("Vehiculo : " + manifiesto.getVehiculoMani());
        holder.choferMani.setText("Chofer : " + manifiesto.getChoferMani());

    }

    @Override
    public int getItemCount() {
        return mListManifiesto.size();
    }


}
