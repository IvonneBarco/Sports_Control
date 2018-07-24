package com.practicavolley.ennovic.sportscontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.practicavolley.ennovic.sportscontrol.Conexiones.Conexion;
import com.practicavolley.ennovic.sportscontrol.Modelos.AtletaVo;
import com.practicavolley.ennovic.sportscontrol.Modelos.DeportesVo;
import com.practicavolley.ennovic.sportscontrol.Modelos.EntrenadoresVo;
import com.practicavolley.ennovic.sportscontrol.Modelos.LigasVo;
import com.practicavolley.ennovic.sportscontrol.Modelos.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AtletasEntrenador extends AppCompatActivity {

    private Usuario user;
    private LigasVo liga;
    private DeportesVo sport;
    private EntrenadoresVo entrenador;
    TextView nomdeporte, iddeporte;

    //RECYCLER
    ArrayList<AtletaVo> listaAtletas;

    //Refencia al reclycler
    RecyclerView recyclerAtletas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atletas);

        // Recuperando variables usuario, deporte y liga
        Bundle bundle = getIntent().getExtras();
        user = bundle.getParcelable("DATOS_USER");
        /*((TextView) findViewById(R.id.idSesiona)).setText("ID USUARIO: " + user.getId());
        ((TextView) findViewById(R.id.rolSesiona)).setText("ROL: " + user.getRole());
        String recuperamos_iddeporte = getIntent().getStringExtra("deporte");
        ((TextView) findViewById(R.id.id_iddeporte_liga)).setText("ID DEPORTE: " + recuperamos_iddeporte);
        String recuperamos_idliga = getIntent().getStringExtra("idliga");
        ((TextView) findViewById(R.id.id_idliga)).setText("ID LIGA: " + recuperamos_idliga);*/

        user.getId();
        user.getRole();
        final String recuperamos_identrenador = getIntent().getStringExtra("identrenador");
        final String recuperamos_idliga = getIntent().getStringExtra("liga");
        // Fin * Recuperando variables usuario y deporte

        //Inicio * Recycler
        listaAtletas = new ArrayList<>();
        recyclerAtletas = (RecyclerView) findViewById(R.id.RecyclerAtletasId);
        recyclerAtletas.setLayoutManager(new LinearLayoutManager(this));
        recyclerAtletas.setHasFixedSize(true);
        //Fin * Recycler

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(AtletasEntrenador.this);

        // Initialize a new JsonArrayRequest instance
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "listar-athletas.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        AtletaVo athlete = null;

                        try {
                            JSONObject objresultado = new JSONObject(response);
                            JSONArray athletes = objresultado.getJSONArray("athleta");

                            if (athletes.length() <= 0) {
                                Toast.makeText(AtletasEntrenador.this, "NO HAY DATOS", Toast.LENGTH_SHORT).show();

                            } else {

                                for (int i = 0; i < athletes.length(); i++) {
                                    athlete = new AtletaVo();
                                    JSONObject objAtletas = athletes.getJSONObject(i);

                                    athlete.setIdatleta(String.valueOf(objAtletas.optInt("athlete_id")));
                                    athlete.setNombreatleta(objAtletas.optString("nombre"));
                                    athlete.setApellidoatleta(objAtletas.optString("apellido"));
                                    athlete.setNivelrendimientoatleta(objAtletas.optString("nivelrendimiento"));
                                    athlete.setFoto(R.drawable.weightlifting);
                                    listaAtletas.add(athlete);


                                }
                                AdaptadorAtletas adapter = new AdaptadorAtletas(listaAtletas);
                                recyclerAtletas.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AtletasEntrenador.this, "NO HAY CONEXIÓN", Toast.LENGTH_SHORT).show();
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
                params.put("id", String.valueOf(getIntent().getStringExtra("identrenador")));
                params.put("liga", String.valueOf(getIntent().getStringExtra("liga")));
                return params;
            }
        };

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(stringRequest);


    }

}
