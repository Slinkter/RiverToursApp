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

import com.cudpast.rivertourapp.Model.Manifiesto;
import com.cudpast.rivertourapp.R;

import java.util.ArrayList;


public class ManifiestoAdapter extends RecyclerView.Adapter<ManifiestoAdapter.customRVManifiesto> {
    public static final String TAG = ManifiestoAdapter.class.getSimpleName();
    //.1 Variable
    private ArrayList<Manifiesto> mListManifiesto;
    private OnItemClickListener mItemListener;

    //.2 Constructor
    public ManifiestoAdapter(ArrayList<Manifiesto> mListManifiesto) {
        this.mListManifiesto = mListManifiesto;
    }

    //.3 Clase RecyclerView
    public class customRVManifiesto extends RecyclerView.ViewHolder {

        TextView idGuiaMani, fechaMani, destinoMani, vehiculoMani, choferMani;
        ImageView btn_listPasajero, btn_showSyncStatus;

        public customRVManifiesto(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);
            idGuiaMani = itemView.findViewById(R.id.it_idGuia);
            fechaMani = itemView.findViewById(R.id.it_fechaGuia);
            destinoMani = itemView.findViewById(R.id.it_destinoMani);
            vehiculoMani = itemView.findViewById(R.id.it_vehiculoMani);
            choferMani = itemView.findViewById(R.id.it_choferMAni);
            btn_showSyncStatus = itemView.findViewById(R.id.it_btn_showSyncStatus);
            btn_listPasajero = itemView.findViewById(R.id.it_btn_showListPasajero);

            btn_showSyncStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        Log.e(TAG, " " + position);
                        Toast.makeText(itemView.getContext(), "Pasajero Eliminado " + (position + 1), Toast.LENGTH_SHORT).show();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onShowListManfiesto(position);
                        }
                    }
                }
            });

            btn_listPasajero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        Log.e(TAG, " " + position);
                        Toast.makeText(itemView.getContext(), "Sync " + (position + 1), Toast.LENGTH_SHORT).show();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onShowSync(position);
                        }
                    }
                }
            });

        }
    }
    //.

    @NonNull
    @Override
    public customRVManifiesto onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_manifiesto, viewGroup, false);
        return new customRVManifiesto(itemView, mItemListener);
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


    public interface OnItemClickListener {
        void onShowSync(int position);

        void onShowListManfiesto(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemListener = listener;
    }

}
