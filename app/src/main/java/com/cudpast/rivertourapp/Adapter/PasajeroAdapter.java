package com.cudpast.rivertourapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cudpast.rivertourapp.Model.Pasajero;
import com.cudpast.rivertourapp.R;

import java.util.ArrayList;

public class PasajeroAdapter extends RecyclerView.Adapter<PasajeroAdapter.CustomViewHolderPasajero> {

    private ArrayList<Pasajero> arrayListPasajero = new ArrayList<>();

    public PasajeroAdapter(ArrayList<Pasajero> arrayListPasajero) {
        this.arrayListPasajero = arrayListPasajero;
    }

    @NonNull
    @Override
    public CustomViewHolderPasajero onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pasajero_list, parent, false);
        CustomViewHolderPasajero myVH = new CustomViewHolderPasajero(view);
        return myVH;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolderPasajero item, int position) {
        item.tv_nombrePasajero.setText("Nombre  : " + arrayListPasajero.get(position).getNombre());
        item.tv_edadPasajero.setText("Edad : " + arrayListPasajero.get(position).getEdad() +" " + "años");
        item.tv_ocupacionPasajero.setText("Ocupación  : " + arrayListPasajero.get(position).getOcupacion());
        item.tv_nacionalidadPasajero.setText("Nacionalidad  : " + arrayListPasajero.get(position).getNacionalidad());
        item.tv_numBoletaPasajero.setText("Num Boleto  : " + arrayListPasajero.get(position).getNumBoleta());
        item.tv_dniPasajero.setText("DNI  : " + arrayListPasajero.get(position).getDni());
        item.tv_destinoPasajero.setText("Destino  : " + arrayListPasajero.get(position).getDestino());
    }

    @Override
    public int getItemCount() {
        return arrayListPasajero.size();
    }


    public class CustomViewHolderPasajero extends RecyclerView.ViewHolder {

        TextView tv_nombrePasajero, tv_edadPasajero, tv_ocupacionPasajero, tv_nacionalidadPasajero, tv_numBoletaPasajero, tv_dniPasajero, tv_destinoPasajero;


        public CustomViewHolderPasajero(@NonNull View itemView) {
            super(itemView);
            tv_nombrePasajero = itemView.findViewById(R.id.tv_nombrePasajero);
            tv_edadPasajero = itemView.findViewById(R.id.tv_edadPasajero);
            tv_ocupacionPasajero = itemView.findViewById(R.id.tv_ocupacionPasajero);
            tv_nacionalidadPasajero = itemView.findViewById(R.id.tv_nacionalidadPasajero);
            tv_numBoletaPasajero = itemView.findViewById(R.id.tv_numBoletaPasajero);
            tv_dniPasajero = itemView.findViewById(R.id.tv_dniPasajero);
            tv_destinoPasajero = itemView.findViewById(R.id.tv_destinoPasajero);


        }
    }

}
