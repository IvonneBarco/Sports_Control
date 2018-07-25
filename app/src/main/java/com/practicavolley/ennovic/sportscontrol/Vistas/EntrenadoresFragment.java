package com.practicavolley.ennovic.sportscontrol.Vistas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.practicavolley.ennovic.sportscontrol.AdaptadorEntrenador;
import com.practicavolley.ennovic.sportscontrol.Conexiones.Conexion;
import com.practicavolley.ennovic.sportscontrol.Modelos.DeportesVo;
import com.practicavolley.ennovic.sportscontrol.Modelos.EntrenadoresVo;
import com.practicavolley.ennovic.sportscontrol.Modelos.LigasVo;
import com.practicavolley.ennovic.sportscontrol.Modelos.Usuario;
import com.practicavolley.ennovic.sportscontrol.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class EntrenadoresFragment extends Fragment {

    private Usuario user;
    private LigasVo liga;
    private DeportesVo sport;
    private EntrenadoresVo coach;
    TextView idliga, iddeporte;


    //RECYCLER
    ArrayList<EntrenadoresVo> listaEntrenadores;

    //Refencia al reclycler
    RecyclerView recyclerEntrenadores;
    View view;

    public EntrenadoresFragment() {
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
        view = inflater.inflate(R.layout.fragment_entrenadores, container, false);

        //Se recupera los datos del usuario que inicio sesión
        //Bundle bundle = getIntent().getExtras();
        Bundle bundle = getActivity().getIntent().getExtras();
        user = bundle.getParcelable("DATOS_USER");
        user.getId();
        user.getRole();

        final String recuperamos_idliga = getArguments() != null ? getArguments().getString("idliga") : "1";
        final String recuperamos_iddeporte = getArguments() != null ? getArguments().getString("iddeporte") : "1";
        Log.i("IDDEPORTE", "recuperamos_idliga" + recuperamos_idliga);
        Log.i("IDLIGA", "recuperamos_iddeporte" + recuperamos_iddeporte);

        //INICIO CODIGO RECYCLER
        listaEntrenadores = new ArrayList<>();
        recyclerEntrenadores = (RecyclerView) view.findViewById(R.id.Recycler_Entrenadores_Id);
        recyclerEntrenadores.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerEntrenadores.setHasFixedSize(true);
        //FIN CODIGO RECYCLER


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Extraemos la info de un JSONArray
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "listar-entrenadores.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        EntrenadoresVo coach = null;

                        try {
                            JSONObject objresultado = new JSONObject(response);
                            JSONArray coaches = objresultado.getJSONArray("entrenadores");

                            if (coaches.length() <= 0) {
                                Toast.makeText(getActivity(), "NO HAY DATOS", Toast.LENGTH_SHORT).show();

                            } else {

                                for (int i = 0; i < coaches.length(); i++) {
                                    coach = new EntrenadoresVo();
                                    JSONObject objentrenadores = coaches.getJSONObject(i);

                                    coach.setIdentrenador(String.valueOf(objentrenadores.optInt("entrenadorid")));
                                    coach.setNombreentrenador(objentrenadores.optString("nombre"));
                                    coach.setApellidoentrenador(objentrenadores.optString("apellido"));
                                    coach.setIdliga(objentrenadores.optString("idliga"));
                                    coach.setFoto(R.drawable.coach);
                                    listaEntrenadores.add(coach);


                                }
                                AdaptadorEntrenador adapter = new AdaptadorEntrenador(listaEntrenadores);

                                //evento click
                                adapter.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //Toast.makeText(getApplicationContext(), "" + listaDeportes.get(recyclerDeportes.getChildAdapterPosition(view)).getNombredeporte(), Toast.LENGTH_SHORT).show();

                                        //Envio de variables DATOS_USER
                                        /*Intent intentd = new Intent(getActivity(), LigasFragment.class);
                                        intentd.putExtra("DATOS_USER", user);
                                        //Envio variable iddeporte
                                        intentd.putExtra("iddeporte", listaDeportes.get(recyclerDeportes.getChildAdapterPosition(view)).getIddeporte());
                                        startActivity(intentd);*/
                                        Bundle args = new Bundle();
                                        args.putString("identrenador", listaEntrenadores.get(recyclerEntrenadores.getChildAdapterPosition(view)).getIdentrenador());
                                        args.putString("liga", recuperamos_idliga);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        fragmentManager.beginTransaction().replace(R.id.fragment_container, new AtletasEntrenadorFragment()).commit();

                                    }
                                });
                                //fin evento click
                                recyclerEntrenadores.setAdapter(adapter);
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
                //params.put("deporte", "1");
                //params.put("liga", "1");
                params.put("deporte", recuperamos_iddeporte);
                params.put("liga", recuperamos_idliga);
                return params;
            }
        };

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(stringRequest);

        return view;
    }

}
