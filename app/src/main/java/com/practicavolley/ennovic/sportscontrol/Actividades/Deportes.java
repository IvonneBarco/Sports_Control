package com.practicavolley.ennovic.sportscontrol.Actividades;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import com.practicavolley.ennovic.sportscontrol.Adapters.AdaptadorDeportes;
import com.practicavolley.ennovic.sportscontrol.Conexiones.Conexion;
import com.practicavolley.ennovic.sportscontrol.Modelos.DeportesVo;
import com.practicavolley.ennovic.sportscontrol.Modelos.Usuario;
import com.practicavolley.ennovic.sportscontrol.Clases.Preferences;
import com.practicavolley.ennovic.sportscontrol.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class Deportes extends AppCompatActivity {

    private Usuario user;
    TextView username, contrasena;

    //RECYCLER
    //Creamos una lista igual a la de AdaptadorDeportes
    ArrayList<DeportesVo> listaDeportes;

    //Referencia al recycler_id
    RecyclerView recyclerDeportes;

    private String IDUSUARIO, ROLEUSUARIO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deportes);

        IDUSUARIO = Preferences.obtenerPreferencesString(this, Preferences.PREFERENCE_ID_USUARIO_LOGIN);
        ROLEUSUARIO = Preferences.obtenerPreferencesString(this, Preferences.PREFERENCE_ROLE_USUARIO_LOGIN);

        // Codigo flecha atras...
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //regresar...

                finish();
            }
        });

        // * Codigo flecha atras...

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
                                //Toast.makeText(Deportes.this, "NO HAY DATOS", Toast.LENGTH_SHORT).show();
                                Drawable icon = getResources().getDrawable(R.drawable.ic_empty);
                                Toasty.normal(Deportes.this, "No se ha encontrado datos", icon).show();

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
                            //Toast.makeText(Deportes.this, "NO HAY CONEXIÓN", Toast.LENGTH_SHORT).show();
                            Drawable icon = getResources().getDrawable(R.drawable.ic_sin_conexion);
                            Toasty.normal(Deportes.this, "No se puede establecer una conexión", icon).show();
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
                params.put("id", IDUSUARIO);
                params.put("role", ROLEUSUARIO);
                return params;
            }
        };

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            //Preferences.savePreferencesBoolean(this, false, Preferences.PREFERENCES_ESTADO_SWITCH);
            Intent i = new Intent(Deportes.this, OpcionesActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
