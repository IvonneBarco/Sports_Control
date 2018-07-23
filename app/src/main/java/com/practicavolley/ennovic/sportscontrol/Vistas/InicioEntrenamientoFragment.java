package com.practicavolley.ennovic.sportscontrol.Vistas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.practicavolley.ennovic.sportscontrol.Modelos.Usuario;
import com.practicavolley.ennovic.sportscontrol.R;


public class InicioEntrenamientoFragment extends Fragment {


    View view;

    public InicioEntrenamientoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_inicio_entrenamiento, container, false);

        return view;
    }
}
