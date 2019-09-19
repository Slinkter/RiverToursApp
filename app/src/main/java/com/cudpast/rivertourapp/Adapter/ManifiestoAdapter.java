package com.cudpast.rivertourapp.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cudpast.rivertourapp.Business.Support.ListPasajeroActivity;
import com.cudpast.rivertourapp.Model.Manifiesto;
import com.cudpast.rivertourapp.R;
import com.cudpast.rivertourapp.SQLite.Utils;

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


    @NonNull
    @Override
    public customRVManifiesto onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_manifiesto, viewGroup, false);
        return new customRVManifiesto(itemView, mItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final customRVManifiesto holder, int position) {
        final Manifiesto manifiesto = mListManifiesto.get(position);
        //
        holder.idGuiaMani.setText(manifiesto.getIdGuiaMani());
        holder.fechaMani.setText(manifiesto.getFechaMani());
        holder.destinoMani.setText(manifiesto.getDestinoMani());
        holder.vehiculoMani.setText(manifiesto.getVehiculoMani());
        holder.choferMani.setText(manifiesto.getChoferMani());

        int sync_status = mListManifiesto.get(position).getSync_status();
        if (sync_status == Utils.SYNC_STATUS_OK_MANIFIESTO) {
            holder.btn_showSyncStatus.setImageResource(R.drawable.ic_checked);
            holder.btn_showSyncStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(holder.itemView.getContext(), " check ok ", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            holder.btn_showSyncStatus.setImageResource(R.drawable.ic_sync);
            holder.btn_showSyncStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(holder.itemView.getContext(), " falta sync ", Toast.LENGTH_SHORT).show();
                }
            });
        }
        holder.btn_listPasajero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.itemView.getContext(), ListPasajeroActivity.class);
                i.putExtra("idguiaManifiesto",manifiesto.getIdGuiaMani());
                holder.itemView.getContext().startActivity(i);
            }
        });

        holder.btn_listPasajero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.itemView.getContext(), ListPasajeroActivity.class);
                i.putExtra("idguiaManifiesto",manifiesto.getIdGuiaMani());
                holder.itemView.getContext().startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mListManifiesto.size();
    }


    public interface OnItemClickListener {
        void onShowSync(int position);

        void onShowListManfiesto(int position);

        void onShowPdfManfiesto(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemListener = listener;
    }


    //.Clase Externa - RecyclerView
    public class customRVManifiesto extends RecyclerView.ViewHolder {

        TextView idGuiaMani, fechaMani, destinoMani, vehiculoMani, choferMani;
        ImageView btn_listPasajero, btn_showSyncStatus , btn_showPdfManifiesto;
        View mView;

        public customRVManifiesto(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);
            //
            mView = itemView;
            idGuiaMani = mView.findViewById(R.id.it_idGuia);
            fechaMani = mView.findViewById(R.id.it_fechaGuia);
            destinoMani = mView.findViewById(R.id.it_destinoMani);
            vehiculoMani = mView.findViewById(R.id.it_vehiculoMani);
            choferMani = mView.findViewById(R.id.it_choferMAni);
            btn_showSyncStatus = mView.findViewById(R.id.it_btn_showSyncStatus);
            btn_listPasajero = mView.findViewById(R.id.it_btn_showListPasajero);

            btn_showSyncStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onShowSync(position);
                        }
                    }
                }
            });

            btn_listPasajero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onShowListManfiesto(position);
                        }
                    }
                }
            });

            btn_showPdfManifiesto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onShowListManfiesto(position);
                        }
                    }
                }
            });

        }
    }


}
