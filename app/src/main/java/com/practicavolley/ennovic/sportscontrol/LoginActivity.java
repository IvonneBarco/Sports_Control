package com.practicavolley.ennovic.sportscontrol;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText contrasena;
    Button login;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username =(EditText) findViewById(R.id.txtUsuario);
        contrasena =(EditText) findViewById(R.id.txtContrasena);
        login=(Button)findViewById(R.id.btnIniciarSesion);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });
    }

    public void iniciarSesion(){
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "iniciar-sesion.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Usuario user=new Usuario();
                        try {
                            JSONObject objResultado = new JSONObject(response);
                            String estado=objResultado.get("estado").toString();
                            if(!estado.equalsIgnoreCase("exito")){
                                Toast.makeText(LoginActivity.this,"Usuario o contraseña incorrecta",Toast.LENGTH_LONG).show();

                            }else{
                                user.setId(objResultado.getJSONObject("datos").optInt("id"));
                                user.setUsername(objResultado.getJSONObject("datos").optString("username"));
                                user.setNombre(objResultado.getJSONObject("datos").optString("nombre"));
                                user.setRole(objResultado.getJSONObject("datos").optString("role"));
                                Intent intent= new Intent(LoginActivity.this, HomeActivity.class);
                                intent.putExtra("id", user.getId());
                                intent.putExtra("nombre", user.getNombre());
                                intent.putExtra("role", user.getRole());
                                intent.putExtra("DATOS_USER",user);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(LoginActivity.this,"NO REGISTRA CONEXIÓN A DATOS O WIFI",Toast.LENGTH_LONG).show();

            }
        }){
            //LOS CAMPOS EN VERDE DEBEN SER IGUAL AL DEL ARCHIVO PHP
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();
                params.put("username", username.getText().toString());
                params.put("password",contrasena.getText().toString());
                return params;

            }
        };
        queue.add(stringRequest);
    }
}
