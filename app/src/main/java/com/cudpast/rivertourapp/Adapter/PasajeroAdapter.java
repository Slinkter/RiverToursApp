package com.cudpast.rivertourapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cudpast.rivertourapp.Model.Pasajero;

import java.util.ArrayList;

public class PasajeroAdapter {

    private ArrayList<Pasajero> arrayList = new ArrayList<>();

    public PasajeroAdapter(ArrayList<Pasajero> arrayList) {
        this.arrayList = arrayList;
    }

    //String nombre, String edad, String ocupacion, String nacionalidad, String numBoleta, String dni, String destino


    public class CustomViewHolderPasajero  extends RecyclerView.ViewHolder{

        TextView tv_nombrePasajero,tv_edadPasajero ,tv_ocupacionPasajero,tv_nacionalidadPasajero,tv_numBoletaPasajero,tv_dniPasajero,tv_destinoPasajero;


        public CustomViewHolderPasajero(@NonNull View itemView) {
            super(itemView);

        }
    }

}
