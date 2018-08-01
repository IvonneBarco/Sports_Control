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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.practicavolley.ennovic.sportscontrol.Adapters.AdaptadorLigas;
import com.practicavolley.ennovic.sportscontrol.Conexiones.Conexion;
import com.practicavolley.ennovic.sportscontrol.Modelos.DeportesVo;
import com.practicavolley.ennovic.sportscontrol.Modelos.LigasVo;
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

public class Ligas extends AppCompatActivity {

    private Usuario user;
    private DeportesVo sport;
    TextView nomdeporte, iddeporte;

    private String IDUSUARIO, ROLEUSUARIO;

    //RECYCLER
    ArrayList<LigasVo> listaLigas;

    //Refencia al reclycler
    RecyclerView recyclerLigas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ligas);

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

        // Recuperando variables usuario y deporte
        Bundle bundle = getIntent().getExtras();
        user = bundle.getParcelable("DATOS_USER");
        /*((TextView) findViewById(R.id.idSesion)).setText("ID USUARIO: " + user.getId());
        ((TextView) findViewById(R.id.roleSesion)).setText("ROL: " + user.getRole());*/

        final String recuperamos_iddeporte = getIntent().getStringExtra("iddeporte");
        //((TextView) findViewById(R.id.id_deporte)).setText("ID DEPORTE: " + recuperamos_iddeporte);

        // Fin * Recuperando variables usuario y deporte

        //Inicio * Recycler
        listaLigas = new ArrayList<>();
        recyclerLigas = (RecyclerView) findViewById(R.id.RecyclerLigasId);
        recyclerLigas.setLayoutManager(new LinearLayoutManager(this));
        recyclerLigas.setHasFixedSize(true);
        //Fin * Recycler

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(Ligas.this);

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
                                //Toast.makeText(Ligas.this, "NO HAY DATOS", Toast.LENGTH_SHORT).show();
                                Drawable icon = getResources().getDrawable(R.drawable.ic_empty);
                                Toasty.normal(Ligas.this, "No se han encontrado datos", icon).show();


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
                                        if (ROLEUSUARIO.equalsIgnoreCase("admin")){
                                            //Envio de variables DATOS_USER
                                            Intent intentd = new Intent(Ligas.this, Entrenadores.class);
                                            intentd.putExtra("DATOS_USER", user);
                                            //Envio variable iddeporte
                                            intentd.putExtra("idliga", listaLigas.get(recyclerLigas.getChildAdapterPosition(view)).getIdliga());
                                            intentd.putExtra("deporte", recuperamos_iddeporte);
                                            //intent.putExtra("role", user.getRole());
                                            startActivity(intentd);
                                        }else{
                                            if (ROLEUSUARIO.equalsIgnoreCase("entrenador")){
                                                //Envio de variables DATOS_USER
                                                Intent intentd = new Intent(Ligas.this, Atletas.class);
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
                            //Toast.makeText(Ligas.this, "NO HAY CONEXIÓN", Toast.LENGTH_SHORT).show();
                            Drawable icon = getResources().getDrawable(R.drawable.ic_sin_conexion);
                            Toasty.normal(Ligas.this, "No se puede establecer una conexión", icon).show();

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
                params.put("deporte",getIntent().getStringExtra("iddeporte"));
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
            Intent i = new Intent(Ligas.this, OpcionesActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}