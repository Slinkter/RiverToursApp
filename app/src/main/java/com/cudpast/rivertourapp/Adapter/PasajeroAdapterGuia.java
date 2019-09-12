package com.cudpast.rivertourapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cudpast.rivertourapp.Model.Pasajero;
import com.cudpast.rivertourapp.R;

import java.util.ArrayList;

public class PasajeroAdapterGuia extends RecyclerView.Adapter<PasajeroAdapterGuia.CustomViewHolderPasajero> {

    public static final String TAG = PasajeroAdapterGuia.class.getSimpleName();

    private ArrayList<Pasajero> mListPasajero;

    public PasajeroAdapterGuia(ArrayList<Pasajero> mListPasajero) {
        this.mListPasajero = mListPasajero;
    }

    @NonNull
    @Override
    public CustomViewHolderPasajero onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pasajero_guia, parent, false);
        CustomViewHolderPasajero myVH = new CustomViewHolderPasajero(view);
        return myVH;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolderPasajero item, int position) {
        item.tv_nombrePasajero.setText("Nombre  : " + mListPasajero.get(position).getNombrePasajero());
        item.tv_edadPasajero.setText("Edad : " + mListPasajero.get(position).getEdadPasajero() + " " + "años");
        item.tv_ocupacionPasajero.setText("Ocupación  : " + mListPasajero.get(position).getOcupacionPasajero());
        item.tv_nacionalidadPasajero.setText("Nacionalidad  : " + mListPasajero.get(position).getNacionalidadPasajero());
        item.tv_numBoletaPasajero.setText("Num Boleto  : " + mListPasajero.get(position).getNumBoleta());
        item.tv_dniPasajero.setText("DNI  : " + mListPasajero.get(position).getDniPasajero());
        item.tv_destinoPasajero.setText("Destino  : " + mListPasajero.get(position).getDestinoPasajero());
    }

    @Override
    public int getItemCount() {
        return mListPasajero.size();
    }

    public class CustomViewHolderPasajero extends RecyclerView.ViewHolder {

        TextView tv_nombrePasajero,
                tv_edadPasajero,
                tv_ocupacionPasajero,
                tv_nacionalidadPasajero,
                tv_numBoletaPasajero,
                tv_dniPasajero,
                tv_destinoPasajero;

        public CustomViewHolderPasajero(@NonNull final View itemView) {
            super(itemView);
            tv_nombrePasajero = itemView.findViewById(R.id.tv_nombrePasajero_guia);
            tv_edadPasajero = itemView.findViewById(R.id.tv_edadPasajero_guia);
            tv_ocupacionPasajero = itemView.findViewById(R.id.tv_ocupacionPasajero_guia);
            tv_nacionalidadPasajero = itemView.findViewById(R.id.tv_nacionalidadPasajero_guia);
            tv_numBoletaPasajero = itemView.findViewById(R.id.tv_numBoletaPasajero_guia);
            tv_dniPasajero = itemView.findViewById(R.id.tv_dniPasajero_guia);
            tv_destinoPasajero = itemView.findViewById(R.id.tv_destinoPasajero_guia);
        }
    }
}