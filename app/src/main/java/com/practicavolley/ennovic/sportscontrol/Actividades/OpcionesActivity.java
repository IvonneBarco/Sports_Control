package com.practicavolley.ennovic.sportscontrol.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.practicavolley.ennovic.sportscontrol.R;

public class OpcionesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout athletes, group, training, sports, plans, settings, time;
    TextView nombreUsuario, rolUsuario, listarUsuario;
    private Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
                Toast.makeText(OpcionesActivity.this, "Deportes", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OpcionesActivity.this, Deportes.class);
                intent.putExtra("DATOS_USER", user);
                startActivity(intent);
            }
        });

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OpcionesActivity.this, "Grupo de apoyo", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(HomeActivity.this, Deportes.class);
                intent.putExtra("DATOS_USER", user);
                startActivity(intent);*/
            }
        });

        training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OpcionesActivity.this, "Entrenamiento", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OpcionesActivity.this, EntrenoProgramado.class);
                //intent.putExtra("DATOS_USER", user);
                startActivity(intent);
            }
        });

        plans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OpcionesActivity.this, "Cargar Planes", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(HomeActivity.this, Deportes.class);
                intent.putExtra("DATOS_USER", user);
                startActivity(intent);*/
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OpcionesActivity.this, "Configuración", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(HomeActivity.this, Deportes.class);
                intent.putExtra("DATOS_USER", user);
                startActivity(intent);*/
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OpcionesActivity.this, "Iniciar entrenamiento", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OpcionesActivity.this, InicioEntrenamiento.class);
                //intent.putExtra("DATOS_USER", user);
                startActivity(intent);
            }
        });

        Bundle bundle = getIntent().getExtras();
        user = bundle.getParcelable("DATOS_USER");
        user.getId();
        ((TextView) findViewById(R.id.nom_usuario_home)).setText("¡HOLA " + user.getNombre().toUpperCase() + "!");
        user.getUsername();
        ((TextView) findViewById(R.id.role_usuario_home)).setText(user.getRole().toUpperCase());
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
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_sports:
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_settings:
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_exit:
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
