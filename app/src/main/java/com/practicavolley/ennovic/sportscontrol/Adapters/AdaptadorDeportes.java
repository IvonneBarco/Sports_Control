package com.practicavolley.ennovic.sportscontrol.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.practicavolley.ennovic.sportscontrol.Modelos.DeportesVo;
import com.practicavolley.ennovic.sportscontrol.R;

import java.util.ArrayList;

public class AdaptadorDeportes extends RecyclerView.Adapter<AdaptadorDeportes.ViewHolderDeportes> implements View.OnClickListener{

    ArrayList<DeportesVo> listaDeportes;
    private  View.OnClickListener listener;

    public AdaptadorDeportes(ArrayList<DeportesVo> listaDeportes) {
        this.listaDeportes = listaDeportes;
    }

    @Override
    public ViewHolderDeportes onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_deportes, null, false);
        //Escucha la selección
        view.setOnClickListener(this);
        return new ViewHolderDeportes(view);
    }

    public class ViewHolderDeportes extends RecyclerView.ViewHolder {

        TextView etiqueta_deporte, etiqueta_iddeporte;
        ImageView etiqueta_foto;

        public ViewHolderDeportes(View itemView) {
            super(itemView);
            //etiqueta_iddeporte = (TextView)itemView.findViewById(R.id.id_deporte_list);
            etiqueta_deporte = (TextView)itemView.findViewById(R.id.id_nombre_deporte_list);
            etiqueta_foto = (ImageView)itemView.findViewById(R.id.id_imagen_deporte_list);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolderDeportes holder, int position) {
       //holder.etiqueta_iddeporte.setText(listaDeportes.get(position).getIddeporte());
        holder.etiqueta_deporte.setText(listaDeportes.get(position).getNombredeporte().toUpperCase());
        holder.etiqueta_foto.setImageResource(listaDeportes.get(position).getFoto());
    }

    @Override
    public int getItemCount() {
        return listaDeportes.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);
        }

    }
}
