package com.practicavolley.ennovic.sportscontrol;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.practicavolley.ennovic.sportscontrol.Modelos.AtletaVo;

import java.util.ArrayList;

public class AdaptadorAtletas extends RecyclerView.Adapter<AdaptadorAtletas.ViewHolderAtletas> implements View.OnClickListener{

    ArrayList<AtletaVo> listaAtletas;
    private  View.OnClickListener listener;

    public AdaptadorAtletas (ArrayList<AtletaVo> listaAtletas) {
        this.listaAtletas = listaAtletas;
    }

    @Override
    public ViewHolderAtletas onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_atletas, null, false);
        //Escucha la selección
        view.setOnClickListener(this);
        return new ViewHolderAtletas(view);
    }

    public class ViewHolderAtletas extends RecyclerView.ViewHolder {

        TextView etiqueta_id_atleta, etiqueta_nombre_atleta, etiqueta_apellido_atleta, etiqueta_rendimiento_atleta ;
        ImageView etiqueta_foto;

        public ViewHolderAtletas(View itemView) {
            super(itemView);
            etiqueta_id_atleta = (TextView)itemView.findViewById(R.id.id_atleta_list);
            etiqueta_nombre_atleta = (TextView)itemView.findViewById(R.id.id_nombre_atleta_list);
            etiqueta_apellido_atleta = (TextView)itemView.findViewById(R.id.id_apellido_atleta_list);
            etiqueta_rendimiento_atleta = (TextView)itemView.findViewById(R.id.id_rendimiento_atleta_list);
            etiqueta_foto = (ImageView)itemView.findViewById(R.id.id_imagen_atleta_list);
        }
    }

    @Override
    public void onBindViewHolder(AdaptadorAtletas.ViewHolderAtletas holder, int position) {
        holder.etiqueta_id_atleta.setText(listaAtletas.get(position).getIdatleta());
        holder.etiqueta_nombre_atleta.setText(listaAtletas.get(position).getNombreatleta());
        holder.etiqueta_apellido_atleta.setText(listaAtletas.get(position).getApellidoatleta());
        holder.etiqueta_rendimiento_atleta.setText(listaAtletas.get(position).getNivelrendimientoatleta());
        //holder.etiqueta_foto.setImageResource(listaAtletas.get(position).getFoto());
    }

    @Override
    public int getItemCount() {
        return listaAtletas.size();
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