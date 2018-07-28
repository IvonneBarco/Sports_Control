package com.practicavolley.ennovic.sportscontrol.Actividades;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
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
import com.practicavolley.ennovic.sportscontrol.Modelos.Usuario;
import com.practicavolley.ennovic.sportscontrol.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText contrasena;
    Button login;
    Switch switchRecordar;
    //RadioButton switchRecordar;
    private boolean isActivateRadioButton;

    private static final String STRING_PREFERENCES = "info.sportcontrol";
    private static final String PREFERENCES_ESTADO_SWITCH = "estado.switch";


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (obtenerEstadoSwitch()){
            Intent intent = new Intent(LoginActivity.this, OpcionesActivity.class);
            startActivity(intent);
            finish();
        }

        username = (EditText) findViewById(R.id.txtUsuario);
        contrasena = (EditText) findViewById(R.id.txtContrasena);
        login = (Button) findViewById(R.id.btnIniciarSesion);
        switchRecordar = (Switch) findViewById(R.id.switchRecordar);

        //switchRecordar = (RadioButton) findViewById(R.id.switchRecordar);
        isActivateRadioButton = switchRecordar.isChecked();//Desactivado

        switchRecordar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isActivateRadioButton){
                    switchRecordar.setChecked(false);
                }
                isActivateRadioButton = switchRecordar.isChecked();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });
    }

    public void iniciarSesion() {
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "iniciar-sesion.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Usuario user = new Usuario();
                        try {
                            JSONObject objResultado = new JSONObject(response);
                            String estado = objResultado.get("estado").toString();
                            if (!estado.equalsIgnoreCase("exito")) {
                                Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();

                            } else {
                                user.setId(objResultado.getJSONObject("datos").optInt("id"));
                                user.setUsername(objResultado.getJSONObject("datos").optString("username"));
                                user.setNombre(objResultado.getJSONObject("datos").optString("nombre"));
                                user.setRole(objResultado.getJSONObject("datos").optString("role"));
                                Intent intent = new Intent(LoginActivity.this, OpcionesActivity.class);
                                intent.putExtra("id", user.getId());
                                intent.putExtra("nombre", user.getNombre());
                                intent.putExtra("role", user.getRole());
                                intent.putExtra("DATOS_USER", user);
                                //guarda el estado del botón
                                guardarEstadoSwitch();
                                startActivity(intent);
                                finish();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(LoginActivity.this, "NO HAY CONEXIÓN", Toast.LENGTH_LONG).show();

            }
        }) {
            //LOS CAMPOS EN VERDE DEBEN SER IGUAL AL DEL ARCHIVO PHP
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username.getText().toString());
                params.put("password", contrasena.getText().toString());
                return params;

            }
        };
        queue.add(stringRequest);
    }

    public void guardarEstadoSwitch(){
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        preferences.edit().putBoolean(PREFERENCES_ESTADO_SWITCH, switchRecordar.isChecked()).apply();
    }

    public boolean obtenerEstadoSwitch(){
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        return preferences.getBoolean(PREFERENCES_ESTADO_SWITCH,(false));
    }

    public static void cambiarEstadoSwitch(Context c, boolean b){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        preferences.edit().putBoolean(PREFERENCES_ESTADO_SWITCH, b).apply();
    }
}
