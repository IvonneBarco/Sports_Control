package com.practicavolley.ennovic.sportscontrol.Actividades;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.preference.SwitchPreference;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.practicavolley.ennovic.sportscontrol.Conexiones.Conexion;
import com.practicavolley.ennovic.sportscontrol.Modelos.Usuario;
import com.practicavolley.ennovic.sportscontrol.Preferences;
import com.practicavolley.ennovic.sportscontrol.R;
import com.practicavolley.ennovic.sportscontrol.VolleyRP;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.time.chrono.JapaneseDate;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText contrasena;
    Button login;
    Switch switchRecordar;

    private boolean isActivateRadioButton;
    private VolleyRP volley;
    private RequestQueue mrequestQueue;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inicializamos las variables
        volley = VolleyRP.getInstance(this);
        mrequestQueue = volley.getRequestQueue();

        if (Preferences.obtenerPreferencesBoolean(LoginActivity.this, Preferences.PREFERENCES_ESTADO_SWITCH)) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        username = (EditText) findViewById(R.id.txtUsuario);
        contrasena = (EditText) findViewById(R.id.txtContrasena);
        login = (Button) findViewById(R.id.btnIniciarSesion);
        switchRecordar = (Switch) findViewById(R.id.switchRecordar);

        isActivateRadioButton = switchRecordar.isChecked();//Desactivado

        switchRecordar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isActivateRadioButton) {
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

    //Aquí va el metoodo IniciarSesión();
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

                                //guarda el estado del switch
                                Preferences.savePreferencesBoolean(LoginActivity.this, switchRecordar.isChecked(), Preferences.PREFERENCES_ESTADO_SWITCH);
                                Preferences.savePreferenesString(LoginActivity.this, user.getUsername(), Preferences.PREFERENCE_USUARIO_LOGIN);
                                Preferences.savePreferenesString(LoginActivity.this, String.valueOf(user.getId()), Preferences.PREFERENCE_ID_USUARIO_LOGIN);
                                Preferences.savePreferenesString(LoginActivity.this, user.getNombre(), Preferences.PREFERENCE_NOMBRE_USUARIO_LOGIN);
                                Preferences.savePreferenesString(LoginActivity.this, user.getRole(), Preferences.PREFERENCE_ROLE_USUARIO_LOGIN);
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

}
