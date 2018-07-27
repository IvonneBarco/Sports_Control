package com.practicavolley.ennovic.sportscontrol.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.practicavolley.ennovic.sportscontrol.Modelos.LigasVo;
import com.practicavolley.ennovic.sportscontrol.R;

import java.util.ArrayList;

public class AdaptadorLigas extends RecyclerView.Adapter<AdaptadorLigas.ViewHolderLigas> implements View.OnClickListener{

    ArrayList<LigasVo> listaLigas;
    private  View.OnClickListener listener;

    public AdaptadorLigas(ArrayList<LigasVo> listaLigas) {
        this.listaLigas = listaLigas;
    }

    @Override
    public ViewHolderLigas onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_ligas, null, false);
        //Escucha la selección
        view.setOnClickListener(this);
        return new ViewHolderLigas(view);
    }

    public class ViewHolderLigas extends RecyclerView.ViewHolder {

        TextView etiqueta_liga, etiqueta_idliga;
        ImageView etiqueta_foto;

        public ViewHolderLigas(View itemView) {
            super(itemView);
            //etiqueta_idliga = (TextView)itemView.findViewById(R.id.id_liga_list);
            etiqueta_liga = (TextView)itemView.findViewById(R.id.id_nombre_liga_list);
            etiqueta_foto = (ImageView)itemView.findViewById(R.id.id_imagen_liga_list);
        }
    }

    @Override
    public void onBindViewHolder(AdaptadorLigas.ViewHolderLigas holder, int position) {
        //holder.etiqueta_idliga.setText(listaLigas.get(position).getIdliga());
        holder.etiqueta_liga.setText(listaLigas.get(position).getNombreliga());
        holder.etiqueta_foto.setImageResource(listaLigas.get(position).getFoto());
    }

    @Override
    public int getItemCount() {
        return listaLigas.size();
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