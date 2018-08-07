package com.practicavolley.ennovic.sportscontrol.Actividades;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.practicavolley.ennovic.sportscontrol.Modelos.Usuario;
import com.practicavolley.ennovic.sportscontrol.Clases.Preferences;
import com.practicavolley.ennovic.sportscontrol.R;

import es.dmoral.toasty.Toasty;

public class OpcionesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout athletes, group, training, sports, plans, settings, time;
    TextView nombreUsuario, rolUsuario, listarUsuario;
    public Usuario user;
    private String NOMBREUSUARIO, APELLIDOUSUARIO, IDUSUARIO, ROLEUSUARIO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NOMBREUSUARIO = Preferences.obtenerPreferencesString(this, Preferences.PREFERENCE_NOMBRE_USUARIO_LOGIN);
        IDUSUARIO = Preferences.obtenerPreferencesString(this, Preferences.PREFERENCE_ID_USUARIO_LOGIN);
        ROLEUSUARIO = Preferences.obtenerPreferencesString(this, Preferences.PREFERENCE_ROLE_USUARIO_LOGIN);

        ((TextView) findViewById(R.id.nom_usuario_home)).setText("¡HOLA " + NOMBREUSUARIO.toUpperCase() + "!");
        ((TextView) findViewById(R.id.role_usuario_home)).setText(ROLEUSUARIO.toUpperCase());



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //////////////////////// HOME ///////////////////////////////////////
        //athletes = findViewById(R.id.btn_athletes);
        group = findViewById(R.id.btn_group);
        training = findViewById(R.id.btn_training);
        sports = findViewById(R.id.btn_sports);
        plans = findViewById(R.id.btn_plans);
        settings = findViewById(R.id.btn_settings);
        time = findViewById(R.id.btn_timer);

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(OpcionesActivity.this, "Deportes", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OpcionesActivity.this, Deportes.class);
                startActivity(intent);
            }
        });

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OpcionesActivity.this, "Grupo de apoyo", Toast.LENGTH_SHORT).show();
            }
        });

        training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(OpcionesActivity.this, "Entrenamiento", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OpcionesActivity.this, Entrenamientos.class);
                startActivity(intent);
            }
        });

        /*plans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OpcionesActivity.this, "Cargar Planes", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, Deportes.class);
                intent.putExtra("DATOS_USER", user);
                startActivity(intent);
            }
        });*/

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OpcionesActivity.this, "Configuración", Toast.LENGTH_SHORT).show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(OpcionesActivity.this, "Iniciar entrenamiento", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OpcionesActivity.this, InicioEntrenamiento.class);
                startActivity(intent);
            }
        });

        //////////////////////// HOME ///////////////////////////////////////
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            Preferences.savePreferencesBoolean(this, false, Preferences.PREFERENCES_ESTADO_SWITCH);
            DialogoSalir();
            /*Intent i = new Intent(OpcionesActivity.this, LoginActivity.class);
            startActivity(i);
            finish();*/
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_training_start:
                Intent intentStart = new Intent(OpcionesActivity.this.getBaseContext(), InicioEntrenamiento.class);
                startActivity(intentStart);
                break;
            case R.id.nav_group:

                break;
            case R.id.nav_training:
                Intent intentTraining = new Intent(OpcionesActivity.this.getBaseContext(), Entrenamientos.class);
                startActivity(intentTraining);
                break;
            case R.id.nav_sports:
                Intent intentSports = new Intent(OpcionesActivity.this.getBaseContext(), Deportes.class);
                startActivity(intentSports);
                break;
            case R.id.nav_settings:
                //Toast.makeText(this, "Configuraciones", Toast.LENGTH_SHORT).show();
                Drawable icon = getResources().getDrawable(R.drawable.ic_settings);
                Toasty.normal(this, "Configuraciones", icon).show();
                //Intent intentE= new Intent(OpcionesActivity.this.getBaseContext(), Entrenamientos.class);
                //startActivity(intentE);
                break;
            case R.id.nav_exit:
                Preferences.savePreferencesBoolean(this, false, Preferences.PREFERENCES_ESTADO_SWITCH);
                DialogoSalir();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void DialogoSalir(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(OpcionesActivity.this);
        alerta.setMessage("¿Desea salir de la aplicación?")
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Codigo de cerrar sesión
                        Intent intent = new Intent(OpcionesActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Codigo de continuar en la app
                        dialogInterface.cancel();
                    }
                });
        AlertDialog titulo = alerta.create();
        titulo.setTitle("Cerrar Sesión");
        titulo.show();
    }

}
