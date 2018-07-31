package com.practicavolley.ennovic.sportscontrol.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.practicavolley.ennovic.sportscontrol.Modelos.EntrenadoresVo;
import com.practicavolley.ennovic.sportscontrol.R;

import java.util.ArrayList;

public class AdaptadorEntrenador extends RecyclerView.Adapter<AdaptadorEntrenador.ViewHolderEntrenadores> implements View.OnClickListener{

    ArrayList<EntrenadoresVo> listaEntrenadores;
    private  View.OnClickListener listener;

    public AdaptadorEntrenador(ArrayList<EntrenadoresVo> listaEntrenadores) {
        this.listaEntrenadores = listaEntrenadores;
    }

    @Override
    public ViewHolderEntrenadores onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_entrenadores, null, false);
        //Escucha la selección
        view.setOnClickListener(this);
        return new ViewHolderEntrenadores(view);
    }

    public class ViewHolderEntrenadores extends RecyclerView.ViewHolder {

        TextView etiqueta_identrenador, etiqueta_nombre_entrenador, etiqueta_apellido_entrenador, etiqueta_idliga ;
        ImageView etiqueta_foto;

        public ViewHolderEntrenadores(View itemView) {
            super(itemView);
            etiqueta_identrenador = (TextView)itemView.findViewById(R.id.id_deporte_entrenadores_list);
            etiqueta_nombre_entrenador = (TextView)itemView.findViewById(R.id.id_nombre_entrenadores_list);
            etiqueta_apellido_entrenador = (TextView)itemView.findViewById(R.id.id_apellido_entrenadores_list);
            etiqueta_idliga = (TextView)itemView.findViewById(R.id.id_liga_entrenadores_list);
            etiqueta_foto = (ImageView)itemView.findViewById(R.id.id_imagen_entrenadores_list);
        }
    }

    @Override
    public void onBindViewHolder(AdaptadorEntrenador.ViewHolderEntrenadores holder, int position) {
        holder.etiqueta_identrenador.setText(listaEntrenadores.get(position).getIdentrenador());
        //holder.etiqueta_nombre_entrenador.setText(listaEntrenadores.get(position).getNombreentrenador().toUpperCase() + " " + listaEntrenadores.get(position).getApellidoentrenador().toUpperCase());
        holder.etiqueta_nombre_entrenador.setText(listaEntrenadores.get(position).getNombreentrenador());
        holder.etiqueta_apellido_entrenador.setText(listaEntrenadores.get(position).getApellidoentrenador());
        holder.etiqueta_idliga.setText("Liga deportiva" + listaEntrenadores.get(position).getIdliga());
        holder.etiqueta_foto.setImageResource(listaEntrenadores.get(position).getFoto());
    }

    @Override
    public int getItemCount() {
        return listaEntrenadores.size();
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