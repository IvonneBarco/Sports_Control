package com.practicavolley.ennovic.sportscontrol.Actividades;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.practicavolley.ennovic.sportscontrol.Modelos.Usuario;
import com.practicavolley.ennovic.sportscontrol.Preferences;
import com.practicavolley.ennovic.sportscontrol.R;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout athletes, group, training, sports, plans, settings, time;
    TextView nombreUsuario, rolUsuario, listarUsuario;
    private Usuario user;

    private String NOMBREUSUARIO;

    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NOMBREUSUARIO = Preferences.obtenerPreferencesString(this, Preferences.PREFERENCE_USUARIO_LOGIN);

        //athletes = findViewById(R.id.btn_athletes);
        group = findViewById(R.id.btn_group);
        training = findViewById(R.id.btn_training);
        sports = findViewById(R.id.btn_sports);
        plans = findViewById(R.id.btn_plans);
        settings = findViewById(R.id.btn_settings);
        time = findViewById(R.id.btn_timer);

        /*Bundle bundle = getIntent().getExtras();
        user = bundle.getParcelable("DATOS_USER");
        user.getId();*/
        //((TextView) findViewById(R.id.nom_usuario_home)).setText("¡HOLA " + user.getNombre().toUpperCase() + "!");
        ((TextView) findViewById(R.id.nom_usuario_home)).setText("¡HOLA " + NOMBREUSUARIO.toUpperCase() + "!");
        //user.getUsername();
        //((TextView) findViewById(R.id.role_usuario_home)).setText(user.getRole().toUpperCase());


        /*athletes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Atletas", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(HomeActivity.this, Atletas.class);
                //startActivity(intent);
            }
        });*/

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Deportes", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, Deportes.class);
                intent.putExtra("DATOS_USER", user);
                startActivity(intent);
            }
        });

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Grupo de apoyo", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(HomeActivity.this, Deportes.class);
                intent.putExtra("DATOS_USER", user);
                startActivity(intent);*/
            }
        });

        training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Entrenamiento", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, EntrenoProgramado.class);
                //intent.putExtra("DATOS_USER", user);
                startActivity(intent);
            }
        });

        plans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Cargar Planes", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(HomeActivity.this, Deportes.class);
                intent.putExtra("DATOS_USER", user);
                startActivity(intent);*/
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Configuración", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(HomeActivity.this, Deportes.class);
                intent.putExtra("DATOS_USER", user);
                startActivity(intent);*/
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Iniciar entrenamiento", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, InicioEntrenamiento.class);
                //intent.putExtra("DATOS_USER", user);
                startActivity(intent);
            }
        });


    }

    public void listar() {
        RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {


            Preferences.savePreferencesBoolean(HomeActivity.this, false, Preferences.PREFERENCES_ESTADO_SWITCH);
            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
