package com.practicavolley.ennovic.sportscontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.practicavolley.ennovic.sportscontrol.Modelos.Usuario;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout athletes, group, training, sports, plans, setting, time;
    TextView nombreUsuario, rolUsuario;
    private Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        athletes = findViewById(R.id.btn_athletes);
        group = findViewById(R.id.btn_group);
        training = findViewById(R.id.btn_training);
        sports = findViewById(R.id.btn_sports);
        plans = findViewById(R.id.btn_plans);
        setting = findViewById(R.id.btn_settings);

        athletes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Atletas", Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(HomeActivity.this, Home.class);
                //startActivity(intent);
            }
        });

        Bundle bundle = getIntent().getExtras();
        user = bundle.getParcelable("DATOS_USER");
        user.getId();
        ((TextView) findViewById(R.id.nom_usuario_home)).setText("¡HOLA "+user.getNombre().toUpperCase()+"!");
        user.getUsername();
        ((TextView) findViewById(R.id.role_usuario_home)).setText(user.getRole().toUpperCase());
    }
}
