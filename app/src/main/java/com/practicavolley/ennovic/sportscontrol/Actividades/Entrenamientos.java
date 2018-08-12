package com.practicavolley.ennovic.sportscontrol.Actividades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.practicavolley.ennovic.sportscontrol.Adapters.AdaptadorEntrenamiento;
import com.practicavolley.ennovic.sportscontrol.Conexiones.Conexion;
import com.practicavolley.ennovic.sportscontrol.Modelos.EntrenamientoVo;
import com.practicavolley.ennovic.sportscontrol.Clases.Preferences;
import com.practicavolley.ennovic.sportscontrol.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class Entrenamientos extends AppCompatActivity {

    private String IDUSUARIO, ROLEUSUARIO;

    //RECYCLER
    ArrayList<EntrenamientoVo> listaEntrenamiento;
    //Referencia al recycler
    RecyclerView recyclerEntrenamientos;

    //barra de progreso
    ProgressDialog progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenos);

        progreso = new ProgressDialog(this);
        progreso.setMessage("Cargando entramientos...");
        progreso.show();

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

        //fab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                Intent newEntrenamiento = new Intent(Entrenamientos.this, EntrenoProgramado.class);
                startActivity(newEntrenamiento);
            }
        });

        //Inicio * Recycler
        listaEntrenamiento = new ArrayList<>();
        recyclerEntrenamientos = (RecyclerView)findViewById(R.id.RecyclerEntrenamientosId);
        recyclerEntrenamientos.setLayoutManager(new LinearLayoutManager(this));
        recyclerEntrenamientos.setHasFixedSize(true);
        //FIn / Recycler

        RequestQueue requestQueue = Volley.newRequestQueue(Entrenamientos.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "listar-entrenop.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        EntrenamientoVo entrenamiento = null;

                        try {
                            JSONObject objresultado = new JSONObject(response);
                            JSONArray entrenamientos = objresultado.getJSONArray("entrenop");

                            if (entrenamientos.length() <= 0) {
                                //Toast.makeText(Entrenamientos.this, "NO HAY DATOS", Toast.LENGTH_SHORT).show();
                                Drawable icon = getResources().getDrawable(R.drawable.ic_empty);
                                Toasty.normal(Entrenamientos.this, "No se ha encontrado datos", icon).show();
                            } else {

                                progreso.hide();

                                for (int i = 0; i < entrenamientos.length(); i++){
                                    entrenamiento = new EntrenamientoVo();
                                    JSONObject objEntrenamientos = entrenamientos.getJSONObject(i);

                                    entrenamiento.setId_entrenamiento(String.valueOf(objEntrenamientos.optInt("id")));
                                    entrenamiento.setNombre_entrenamiento(objEntrenamientos.optString("nombre"));
                                    entrenamiento.setFecha_entrenamiento(objEntrenamientos.optString("fecha"));
                                    entrenamiento.setHorainicio_entrenameinto(objEntrenamientos.optString("horainicio"));
                                    entrenamiento.setHorafin_entrenamiento(objEntrenamientos.optString("horafin"));
                                    entrenamiento.setLugar_entrenamiento(objEntrenamientos.optString("lugar"));
                                    entrenamiento.setSemana_entrenamiento(objEntrenamientos.optString("semana_id"));
                                    entrenamiento.setDescripcion_entrenamiento(objEntrenamientos.optString("descripcion"));

                                    listaEntrenamiento.add(entrenamiento);

                                }

                                AdaptadorEntrenamiento adapter = new AdaptadorEntrenamiento(listaEntrenamiento);

                                //evento click
                                adapter.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(getApplicationContext(), "" + listaEntrenamiento.get(recyclerEntrenamientos.getChildAdapterPosition(view)).getId_entrenamiento(), Toast.LENGTH_SHORT).show();

                                        //Envio de variables DATOS_USER
                                        Intent intent = new Intent(Entrenamientos.this, CargarImagen.class);
                                        //Envio variable identrenamiento
                                        intent.putExtra("id_entrenamiento", listaEntrenamiento.get(recyclerEntrenamientos.getChildAdapterPosition(view)).getId_entrenamiento());
                                        intent.putExtra("nom_entrenamiento", listaEntrenamiento.get(recyclerEntrenamientos.getChildAdapterPosition(view)).getNombre_entrenamiento());
                                        startActivity(intent);
                                        finish();

                                    }
                                });
                                //fin evento click

                                recyclerEntrenamientos.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Toast.makeText(Entrenamientos.this, "NO HAY CONEXIÓN", Toast.LENGTH_SHORT).show();
                            Drawable icon = getResources().getDrawable(R.drawable.ic_sin_conexion);
                            Toasty.normal(Entrenamientos.this, "No se puede establecer una conexión", icon).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            //LOS CAMPOS EN VERDE DEBEN SER IGUAL AL DEL ARCHIVO PHP
            /*protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", IDUSUARIO);
                params.put("liga", String.valueOf(getIntent().getStringExtra("idliga")));
                return params;
            }*/
        };

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(stringRequest);
    }

    public void fechaActual(){
        long hoy = System.currentTimeMillis();
        String mifecha = new SimpleDateFormat("yyyy/MM/dd").format(hoy);
        //Toast.makeText(this, "HOY ES: "+ mifecha, Toast.LENGTH_LONG).show();
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
            Intent i = new Intent(Entrenamientos.this, OpcionesActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
