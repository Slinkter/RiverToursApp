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

public class PasajeroAdapter extends RecyclerView.Adapter<PasajeroAdapter.CustomViewHolderPasajero> {

    public static final String TAG = PasajeroAdapter.class.getSimpleName();

    private ArrayList<Pasajero> mListPasajero;
    private OnItemClickListener mItemListener;

    public PasajeroAdapter(ArrayList<Pasajero> mListPasajero) {
        this.mListPasajero = mListPasajero;
    }

    @NonNull
    @Override
    public CustomViewHolderPasajero onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pasajero_list, parent, false);
        CustomViewHolderPasajero myVH = new CustomViewHolderPasajero(view, mItemListener);
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

        ImageView mEdit, mDelete;

        public CustomViewHolderPasajero(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);
            tv_nombrePasajero = itemView.findViewById(R.id.tv_nombrePasajero);
            tv_edadPasajero = itemView.findViewById(R.id.tv_edadPasajero);
            tv_ocupacionPasajero = itemView.findViewById(R.id.tv_ocupacionPasajero);
            tv_nacionalidadPasajero = itemView.findViewById(R.id.tv_nacionalidadPasajero);
            tv_numBoletaPasajero = itemView.findViewById(R.id.tv_numBoletaPasajero);
            tv_dniPasajero = itemView.findViewById(R.id.tv_dniPasajero);
            tv_destinoPasajero = itemView.findViewById(R.id.tv_destinoPasajero);


            mDelete = itemView.findViewById(R.id.btn_delete);

            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        Log.e(TAG, " " + position);
                        Toast.makeText(itemView.getContext(), "delete " + (position + 1), Toast.LENGTH_SHORT).show();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });


        }
    }


    public interface OnItemClickListener {



        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemListener = listener;
    }

}
