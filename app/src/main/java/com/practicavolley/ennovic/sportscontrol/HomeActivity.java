package com.practicavolley.ennovic.sportscontrol;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class HomeActivity extends AppCompatActivity {

    private LinearLayout athletes, group, training, sports, plans, settings, time;
    TextView nombreUsuario, rolUsuario, listarUsuario;
    private Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        Bundle bundle = getIntent().getExtras();
        user = bundle.getParcelable("DATOS_USER");
        user.getId();
        ((TextView) findViewById(R.id.nom_usuario_home)).setText("¡HOLA " + user.getNombre().toUpperCase() + "!");
        user.getUsername();
        ((TextView) findViewById(R.id.role_usuario_home)).setText(user.getRole().toUpperCase());


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
}
