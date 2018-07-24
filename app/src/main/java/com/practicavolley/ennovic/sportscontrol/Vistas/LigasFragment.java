package com.practicavolley.ennovic.sportscontrol.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.practicavolley.ennovic.sportscontrol.AdaptadorLigas;
import com.practicavolley.ennovic.sportscontrol.Atletas;
import com.practicavolley.ennovic.sportscontrol.Conexiones.Conexion;
import com.practicavolley.ennovic.sportscontrol.Entrenadores;
import com.practicavolley.ennovic.sportscontrol.Ligas;
import com.practicavolley.ennovic.sportscontrol.Modelos.DeportesVo;
import com.practicavolley.ennovic.sportscontrol.Modelos.LigasVo;
import com.practicavolley.ennovic.sportscontrol.Modelos.Usuario;
import com.practicavolley.ennovic.sportscontrol.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class LigasFragment extends Fragment {

    private Usuario user;
    private DeportesVo sport;
    private String nomdeporte, iddeporte;

    //RECYCLER
    ArrayList<LigasVo> listaLigas;

    //Refencia al reclycler
    RecyclerView recyclerLigas;
    View view;

    public LigasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            iddeporte = getArguments().getString("iddeporte","1");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ligas, container, false);

        // Recuperando variables usuario y deporte
        Bundle bundle = getActivity().getIntent().getExtras();
        user = bundle.getParcelable("DATOS_USER");
        /*((TextView) findViewById(R.id.idSesion)).setText("ID USUARIO: " + user.getId());
        ((TextView) findViewById(R.id.roleSesion)).setText("ROL: " + user.getRole());*/

        //final String recuperamos_iddeporte = getActivity().getIntent().getStringExtra("iddeporte");
        final String recuperamos_iddeporte = getArguments() != null ? getArguments().getString("iddeporte") : "1";
        Log.i("IDDEPORTE", "recuperamos_iddeporte" + recuperamos_iddeporte);

        //((TextView) findViewById(R.id.id_deporte)).setText("ID DEPORTE: " + recuperamos_iddeporte);

        user.getId();
        user.getRole();
        // Fin * Recuperando variables usuario y deporte

        //Inicio * Recycler
        listaLigas = new ArrayList<>();
        recyclerLigas = (RecyclerView) view.findViewById(R.id.RecyclerLigasId);
        recyclerLigas.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerLigas.setHasFixedSize(true);
        //Fin * Recycler

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        // Initialize a new JsonArrayRequest instance
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "listar-ligas.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        LigasVo liga = null;

                        try {
                            JSONObject objresultado = new JSONObject(response);
                            JSONArray ligas = objresultado.getJSONArray("ligas");

                            if (ligas.length()<=0){
                                Toast.makeText(getActivity(), "NO HAY DATOS", Toast.LENGTH_SHORT).show();

                            }else{

                                for (int i = 0; i < ligas.length(); i++){
                                    liga = new LigasVo();
                                    JSONObject objligas = ligas.getJSONObject(i);

                                    liga.setIdliga(String.valueOf(objligas.optInt("idliga")));
                                    liga.setNombreliga(objligas.optString("nombre"));
                                    liga.setFoto(R.drawable.team);
                                    listaLigas.add(liga);


                                }
                                AdaptadorLigas adapter = new AdaptadorLigas(listaLigas);
                                //evento click
                                adapter.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //Toast.makeText(getApplicationContext(), "" + listaLigas.get(recyclerLigas.getChildAdapterPosition(view)).getNombreliga(), Toast.LENGTH_SHORT).show();
                                        if (user.getRole().equalsIgnoreCase("admin")){
                                            //Envio de variables DATOS_USER
                                            Intent intentd = new Intent(getActivity(), Entrenadores.class);
                                            intentd.putExtra("DATOS_USER", user);
                                            //Envio variable iddeporte
                                            intentd.putExtra("idliga", listaLigas.get(recyclerLigas.getChildAdapterPosition(view)).getIdliga());
                                            intentd.putExtra("deporte", recuperamos_iddeporte);
                                            //intent.putExtra("role", user.getRole());
                                            startActivity(intentd);
                                        }else{
                                            if (user.getRole().equalsIgnoreCase("entrenador")){
                                                //Envio de variables DATOS_USER
                                                Intent intentd = new Intent(getActivity(), Atletas.class);
                                                intentd.putExtra("DATOS_USER", user);

                                                //Envio variable iddeporte
                                                intentd.putExtra("idliga", listaLigas.get(recyclerLigas.getChildAdapterPosition(view)).getIdliga());
                                                intentd.putExtra("deporte", recuperamos_iddeporte);
                                                startActivity(intentd);
                                            }
                                        }
                                    }
                                });
                                //fin evento click
                                recyclerLigas.setAdapter(adapter);
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
                //params.put("deporte",getActivity().getIntent().getStringExtra("iddeporte"));
                params.put("deporte",recuperamos_iddeporte);
                return params;
            }
        };

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(stringRequest);

        return view;
    }

}
