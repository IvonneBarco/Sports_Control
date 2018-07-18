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

public class Entrenadores extends AppCompatActivity {

    private Usuario user;
    private LigasVo liga;
    private DeportesVo sport;
    private EntrenadoresVo coach;
    TextView idliga, iddeporte;


    //RECYCLER
    ArrayList<EntrenadoresVo> listaEntrenadores;

    //Refencia al reclycler
    RecyclerView recyclerEntrenadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenadores);

        // Recuperando variables usuario, deporte y liga
        Bundle bundle = getIntent().getExtras();
        user = bundle.getParcelable("DATOS_USER");
        /*((TextView) findViewById(R.id.idSesionl)).setText("ID USUARIO: " + user.getId());
        ((TextView) findViewById(R.id.rolSesionl)).setText("ROL: " + user.getRole());
        String recuperamos_iddeporte = getIntent().getStringExtra("deporte");
        ((TextView) findViewById(R.id.id_iddeporte_liga)).setText("ID DEPORTE: " + recuperamos_iddeporte);
        String recuperamos_idliga = getIntent().getStringExtra("idliga");
        ((TextView) findViewById(R.id.id_idliga)).setText("ID LIGA: " + recuperamos_idliga);*/

        user.getId();
        user.getRole();
        String recuperamos_iddeporte = getIntent().getStringExtra("deporte");
        String recuperamos_idliga = getIntent().getStringExtra("idliga");
        // Fin * Recuperando variables usuario y deporte

        //Inicio * Recycler
        listaEntrenadores = new ArrayList<>();
        recyclerEntrenadores = (RecyclerView) findViewById(R.id.RecyclerEntrenadoresId);
        recyclerEntrenadores.setLayoutManager(new LinearLayoutManager(this));
        recyclerEntrenadores.setHasFixedSize(true);
        //Fin * Recycler

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(Entrenadores.this);

        // Initialize a new JsonArrayRequest instance
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "listar-entrenadores.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        EntrenadoresVo coach = null;

                        try {
                            JSONObject objresultado = new JSONObject(response);
                            JSONArray coaches = objresultado.getJSONArray("entrenadores");

                            if (coaches.length() <= 0) {
                                Toast.makeText(Entrenadores.this, "NO HAY DATOS", Toast.LENGTH_SHORT).show();

                            } else {

                                for (int i = 0; i < coaches.length(); i++) {
                                    coach = new EntrenadoresVo();
                                    JSONObject objentrenadores = coaches.getJSONObject(i);

                                    coach.setIdentrenador(String.valueOf(objentrenadores.optInt("entrenadorid")));
                                    coach.setNombreentrenador(objentrenadores.optString("nombre"));
                                    coach.setApellidoentrenador(objentrenadores.optString("apellido"));
                                    coach.setFoto(R.drawable.coach);
                                    listaEntrenadores.add(coach);


                                }
                                AdaptadorEntrenador adapter = new AdaptadorEntrenador(listaEntrenadores);
                                recyclerEntrenadores.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Entrenadores.this, "NO HAY CONEXIÓN", Toast.LENGTH_SHORT).show();
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
                params.put("deporte", "1");
                params.put("liga", "1");
                /*params.put("deporte", String.valueOf(getIntent().getStringExtra("iddeporte")));
                params.put("liga", String.valueOf(getIntent().getStringExtra("idliga")));*/
                return params;
            }
        };

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(stringRequest);

    }

}
