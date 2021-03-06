package com.practicavolley.ennovic.sportscontrol.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.practicavolley.ennovic.sportscontrol.Modelos.AtletaEntrenoVo;
import com.practicavolley.ennovic.sportscontrol.R;

import java.util.ArrayList;

public class AdaptadorAtletasEntrenos extends RecyclerView.Adapter<AdaptadorAtletasEntrenos.ViewHolderAtletas> implements View.OnClickListener{

    ArrayList<AtletaEntrenoVo> listaAtletasEntrenos;
    private  View.OnClickListener listener;

    public AdaptadorAtletasEntrenos(ArrayList<AtletaEntrenoVo> listaAtletasEntrenos) {
        this.listaAtletasEntrenos = listaAtletasEntrenos;
    }

    @Override
    public ViewHolderAtletas onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_atletas_entreno, null, false);
        //Escucha la selección
        view.setOnClickListener(this);
        return new ViewHolderAtletas(view);
    }

    public class ViewHolderAtletas extends RecyclerView.ViewHolder {

        TextView etiqueta_nombre_atleta;
        ImageView etiqueta_foto;

        public ViewHolderAtletas(View itemView) {
            super(itemView);
            //etiqueta_id_atleta = (TextView)itemView.findViewById(R.id.id_atleta_list);
            etiqueta_nombre_atleta = (TextView)itemView.findViewById(R.id.id_nombre_atleta_list);
            etiqueta_foto = (ImageView)itemView.findViewById(R.id.id_imagen_atleta_list);
        }
    }

    @Override
    public void onBindViewHolder(AdaptadorAtletasEntrenos.ViewHolderAtletas holder, int position) {
        //holder.etiqueta_id_atleta.setText(listaAtletas.get(position).getIdatleta());
        holder.etiqueta_nombre_atleta.setText(listaAtletasEntrenos.get(position).getNombreatleta().toUpperCase());
        //holder.etiqueta_apellido_atleta.setText(listaAtletas.get(position).getApellidoatleta());
        holder.etiqueta_foto.setImageResource(listaAtletasEntrenos.get(position).getFoto());
    }

    @Override
    public int getItemCount() {
        return listaAtletasEntrenos.size();
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