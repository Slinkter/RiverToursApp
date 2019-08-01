package com.cudpast.rivertourapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cudpast.rivertourapp.Model.Vehiculo;
import com.cudpast.rivertourapp.R;

import java.util.List;

public class vehiculoAdapter extends RecyclerView.Adapter<vehiculoAdapter.CustomViewHolderVehiculo> {

    private List<Vehiculo> mListVehiculo;

    public vehiculoAdapter(List<Vehiculo> mListVehiculo) {
        this.mListVehiculo = mListVehiculo;
    }

    @NonNull
    @Override
    public CustomViewHolderVehiculo onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vehiculo_list, viewGroup, false);
        return new CustomViewHolderVehiculo(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolderVehiculo holder, int position) {
        Vehiculo vehiculo = mListVehiculo.get(position);
        holder.nombreVehiculo.setText("Nombre : " + vehiculo.getNombrevehiculo());
        holder.marcaVehiculo.setText("Marca : " + vehiculo.getMarcaVehiculo());
        holder.matriculaVehiculo.setText("Matricula : " + vehiculo.getMatriculoVehiculo());
        holder.placaVehiculo.setText("Placa : " + vehiculo.getPlacaVehiculo());
    }

    @Override
    public int getItemCount() {
        return mListVehiculo.size();
    }


    //mini clase de tipo RecyclerView
    public class CustomViewHolderVehiculo extends RecyclerView.ViewHolder {

        TextView nombreVehiculo, marcaVehiculo, matriculaVehiculo, placaVehiculo;

        public CustomViewHolderVehiculo(@NonNull View itemView) {
            super(itemView);
            nombreVehiculo = itemView.findViewById(R.id.nombreVehiculo);
            marcaVehiculo = itemView.findViewById(R.id.marcaVehiculo);
            matriculaVehiculo = itemView.findViewById(R.id.matriculaVehiculo);
            placaVehiculo = itemView.findViewById(R.id.placaVehiculo);

        }


    }


}
