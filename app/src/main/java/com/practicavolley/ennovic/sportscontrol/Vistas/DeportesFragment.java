package com.practicavolley.ennovic.sportscontrol.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.practicavolley.ennovic.sportscontrol.AdaptadorDeportes;
import com.practicavolley.ennovic.sportscontrol.Conexiones.Conexion;
import com.practicavolley.ennovic.sportscontrol.Deportes;
import com.practicavolley.ennovic.sportscontrol.EntrenoProgramado;
import com.practicavolley.ennovic.sportscontrol.InicioEntrenamiento;
import com.practicavolley.ennovic.sportscontrol.Ligas;
import com.practicavolley.ennovic.sportscontrol.Modelos.DeportesVo;
import com.practicavolley.ennovic.sportscontrol.Modelos.Usuario;
import com.practicavolley.ennovic.sportscontrol.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DeportesFragment extends Fragment {

    private Usuario user;
    TextView username, contrasena;

    //RECYCLER
    //Creamos una lista igual a la de AdaptadorDeportes
    ArrayList<DeportesVo> listaDeportes;

    //Referencia al recycler_id
    RecyclerView recyclerDeportes;
    View view;

    public DeportesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_deportes, container, false);

        //Se recupera los datos del usuario que inicio sesión
        //Bundle bundle = getIntent().getExtras();
        Bundle bundle = getActivity().getIntent().getExtras();
        user = bundle.getParcelable("DATOS_USER");
        user.getId();
        user.getRole();

        //INICIO CODIGO RECYCLER
        listaDeportes = new ArrayList<>();
        recyclerDeportes = (RecyclerView) view.findViewById(R.id.Recycler_Deportes_Id);
        recyclerDeportes.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerDeportes.setHasFixedSize(true);
        //FIN CODIGO RECYCLER


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Extraemos la info de un JSONArray
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "listar-deportes.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        DeportesVo sport = null;

                        try {
                            JSONObject objresultado = new JSONObject(response);
                            JSONArray deportes = objresultado.getJSONArray("resultado");

                            if (deportes.length() <= 0) {
                                Toast.makeText(getActivity(), "NO HAY DATOS", Toast.LENGTH_SHORT).show();

                            } else {

                                for (int i = 0; i < deportes.length(); i++) {
                                    sport = new DeportesVo();
                                    JSONObject objdeporte = deportes.getJSONObject(i);

                                    sport.setIddeporte(String.valueOf(objdeporte.optInt("iddeporte")));
                                    sport.setNombredeporte(objdeporte.optString("nombre"));
                                    sport.setFoto(R.drawable.basketball_jersey);
                                    listaDeportes.add(sport);


                                }
                                AdaptadorDeportes adapter = new AdaptadorDeportes(listaDeportes);

                                //evento click
                                adapter.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //Toast.makeText(getApplicationContext(), "" + listaDeportes.get(recyclerDeportes.getChildAdapterPosition(view)).getNombredeporte(), Toast.LENGTH_SHORT).show();

                                        //Envio de variables DATOS_USER
                                        Intent intentd = new Intent(getActivity(), Ligas.class);
                                        intentd.putExtra("DATOS_USER", user);
                                        //Envio variable iddeporte
                                        intentd.putExtra("iddeporte", listaDeportes.get(recyclerDeportes.getChildAdapterPosition(view)).getIddeporte());
                                        startActivity(intentd);

                                    }
                                });
                                //fin evento click
                                recyclerDeportes.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "NO HAY CONEXIÓN", Toast.LENGTH_SHORT).show();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Do something when error occurred
            }
        }) {

            //LOS CAMPOS EN VERDE DEBEN SER IGUAL AL DEL ARCHIVO PHP
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(user.getId()));
                params.put("role", user.getRole());
                return params;
            }
        };

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(stringRequest);

        return view;
    }

}
