package com.practicavolley.ennovic.sportscontrol.Vistas;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.practicavolley.ennovic.sportscontrol.Conexiones.Conexion;
import com.practicavolley.ennovic.sportscontrol.Deportes;
import com.practicavolley.ennovic.sportscontrol.EntrenoProgramado;
import com.practicavolley.ennovic.sportscontrol.HomeActivity;
import com.practicavolley.ennovic.sportscontrol.InicioEntrenamiento;
import com.practicavolley.ennovic.sportscontrol.Modelos.Usuario;
import com.practicavolley.ennovic.sportscontrol.R;


public class HomeFragment extends Fragment {

    private LinearLayout athletes, group, training, sports, plans, settings, time;
    TextView nombreUsuario, rolUsuario, listarUsuario;
    private Usuario user;
    View view;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        //athletes = view.findViewById(R.id.btn_athletes);
        group = view.findViewById(R.id.btn_group);
        training = view.findViewById(R.id.btn_training);
        sports = view.findViewById(R.id.btn_sports);
        plans = view.findViewById(R.id.btn_plans);
        settings = view.findViewById(R.id.btn_settings);
        time = view.findViewById(R.id.btn_timer);

        /*athletes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Atletas", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(getActivity(), Atletas.class);
                //startActivity(intent);
            }
        });*/

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Deportes", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Deportes.class);
                intent.putExtra("DATOS_USER", user);
                startActivity(intent);
            }
        });

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Grupo de apoyo", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(getActivity(), Deportes.class);
                intent.putExtra("DATOS_USER", user);
                startActivity(intent);*/
            }
        });

        training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Entrenamiento", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), EntrenoProgramado.class);
                //intent.putExtra("DATOS_USER", user);
                startActivity(intent);
            }
        });

        plans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Cargar Planes", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(getActivity(), Deportes.class);
                intent.putExtra("DATOS_USER", user);
                startActivity(intent);*/
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Configuración", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(getActivity(), Deportes.class);
                intent.putExtra("DATOS_USER", user);
                startActivity(intent);*/
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Iniciar entrenamiento", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), InicioEntrenamiento.class);
                //intent.putExtra("DATOS_USER", user);
                startActivity(intent);
            }
        });

        //Bundle bundle = getIntent().getExtras();
        Bundle bundle = getActivity().getIntent().getExtras();
        user = bundle.getParcelable("DATOS_USER");
        user.getId();
        ((TextView) view.findViewById(R.id.nom_usuario_home)).setText("¡HOLA " + user.getNombre().toUpperCase() + "!");
        user.getUsername();
        ((TextView) view.findViewById(R.id.role_usuario_home)).setText(user.getRole().toUpperCase());


        return view;
    }

    public void listar() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "listar-usuario.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        listarUsuario.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

        };
        queue.add(stringRequest);
    }
}
