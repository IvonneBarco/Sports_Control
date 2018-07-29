package com.practicavolley.ennovic.sportscontrol.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.practicavolley.ennovic.sportscontrol.Modelos.EntrenamientoVo;
import com.practicavolley.ennovic.sportscontrol.R;

import java.util.ArrayList;

public class AdaptadorEntrenamiento extends RecyclerView.Adapter<AdaptadorEntrenamiento.ViewHolderEntrenamiento> implements View.OnClickListener {

    ArrayList<EntrenamientoVo> listaEntrenamientos;
    private  View.OnClickListener listener;

    public AdaptadorEntrenamiento (ArrayList<EntrenamientoVo> listaAtletas) {
        this.listaEntrenamientos = listaAtletas;
    }

    @Override
    public ViewHolderEntrenamiento onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_entrenamientos, null, false);

        //Escucha la selección
        view.setOnClickListener(this);
        return new ViewHolderEntrenamiento(view);
    }

    public class ViewHolderEntrenamiento extends RecyclerView.ViewHolder {

        TextView etiqueta_id_entrenamiento, etiqueta_nombre_entrenamiento, etiqueta_fecha_entrenamiento, etiqueta_horainicio_entrenamiento, etiqueta_hfin_entrenamiento, etiqueta_lugar_entrenamiento, etiqueta_semana_entrenamiento, etiqueta_descripcion_entrenamiento;

        public ViewHolderEntrenamiento(View itemView) {
            super(itemView);
            etiqueta_id_entrenamiento = (TextView) itemView.findViewById(R.id.id_entrenamiento);
            etiqueta_nombre_entrenamiento = (TextView) itemView.findViewById(R.id.id_nombre_entrenamiento);
            etiqueta_fecha_entrenamiento = (TextView) itemView.findViewById(R.id.id_fecha_entrenamiento);
            etiqueta_horainicio_entrenamiento = (TextView) itemView.findViewById(R.id.id_hora_inicio_entrenamiento);
            etiqueta_hfin_entrenamiento = (TextView) itemView.findViewById(R.id.id_hora_fin_entrenamiento);
            etiqueta_lugar_entrenamiento = (TextView) itemView.findViewById(R.id.id_lugar_entrenamiento);
            etiqueta_semana_entrenamiento = (TextView) itemView.findViewById(R.id.id_semana_entrenamiento);
            etiqueta_descripcion_entrenamiento = (TextView) itemView.findViewById(R.id.id_descripcion_entrenamiento);
        }
    }

    @Override
    public void onBindViewHolder(AdaptadorEntrenamiento.ViewHolderEntrenamiento holder, int position) {

        holder.etiqueta_id_entrenamiento.setText(listaEntrenamientos.get(position).getId_entrenamiento().toUpperCase());
        holder.etiqueta_nombre_entrenamiento.setText(listaEntrenamientos.get(position).getNombre_entrenamiento());
        holder.etiqueta_fecha_entrenamiento.setText(listaEntrenamientos.get(position).getFecha_entrenamiento());
        holder.etiqueta_horainicio_entrenamiento.setText(listaEntrenamientos.get(position).getHorainicio_entrenameinto());
        holder.etiqueta_hfin_entrenamiento.setText(listaEntrenamientos.get(position).getHorafin_entrenamiento());
        holder.etiqueta_lugar_entrenamiento.setText(listaEntrenamientos.get(position).getLugar_entrenamiento());
        holder.etiqueta_semana_entrenamiento.setText(listaEntrenamientos.get(position).getSemana_entrenamiento());
        holder.etiqueta_descripcion_entrenamiento.setText(listaEntrenamientos.get(position).getDescripcion_entrenamiento());
    }

    @Override
    public int getItemCount() {
        return listaEntrenamientos.size();
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
