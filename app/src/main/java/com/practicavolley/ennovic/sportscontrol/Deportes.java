package com.practicavolley.ennovic.sportscontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.practicavolley.ennovic.sportscontrol.Conexiones.Conexion;
import com.practicavolley.ennovic.sportscontrol.Modelos.DeportesVo;
import com.practicavolley.ennovic.sportscontrol.Modelos.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Deportes extends AppCompatActivity {

    private Usuario user;
    TextView username, contrasena;


    //RECYCLER
    //Creamos una lista igual a la de AdaptadorDeportes
    ArrayList<DeportesVo> listaDeportes;

    //Referencia al recycler_id
    RecyclerView recyclerDeportes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deportes);

        /*username = (TextView) findViewById(R.id.txtUsuario);
        contrasena = (TextView) findViewById(R.id.txtContrasena);*/

        //Se recupera los datos del usuario que inicio sesión
        Bundle bundle = getIntent().getExtras();
        user = bundle.getParcelable("DATOS_USER");
        ((TextView) findViewById(R.id.idSesion)).setText(user.getId() + "");
        ((TextView) findViewById(R.id.roleSesion)).setText(user.getRole());

        //INICIO CODIGO RECYCLER

        listaDeportes = new ArrayList<>();
        recyclerDeportes = (RecyclerView) findViewById(R.id.RecyclerId);
        recyclerDeportes.setLayoutManager(new LinearLayoutManager(this));
        recyclerDeportes.setHasFixedSize(true);

        //FIN CODIGO RECYCLER


        RequestQueue requestQueue = Volley.newRequestQueue(Deportes.this);

        //Extraemos la info de un JSONArray
        StringRequest stringRequest = new StringRequest(Method.POST, Conexion.URL_WEB_SERVICES + "listar-deportes.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        DeportesVo sport = null;

                        try {
                            JSONObject objresultado = new JSONObject(response);
                            JSONArray deportes = objresultado.getJSONArray("resultado");

                            if (deportes.length() <= 0) {
                                Toast.makeText(Deportes.this, "NO HAY DATOS", Toast.LENGTH_LONG).show();

                            } else {

                                for (int i = 0; i < deportes.length(); i++) {
                                    sport = new DeportesVo();
                                    JSONObject objdeporte = deportes.getJSONObject(i);

                                    sport.setIddeporte(String.valueOf(objdeporte.optInt("iddeporte")));
                                    sport.setNombredeporte(objdeporte.optString("nombre"));
                                    sport.setFoto(R.drawable.icon_sports);
                                    listaDeportes.add(sport);


                                }
                                AdaptadorDeportes adapter = new AdaptadorDeportes(listaDeportes);

                                //evento click
                                adapter.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(getApplicationContext(), "Selección: " +
                                                listaDeportes.get(recyclerDeportes.getChildAdapterPosition(view)).getNombredeporte(), Toast.LENGTH_LONG).show();

                                        //Envio de variables DATOS_USER
                                        Intent intentd = new Intent(Deportes.this, Ligas.class);
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
                            Toast.makeText(Deportes.this, "NO HAY CONEXIÓN", Toast.LENGTH_LONG).show();
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

    }


}
