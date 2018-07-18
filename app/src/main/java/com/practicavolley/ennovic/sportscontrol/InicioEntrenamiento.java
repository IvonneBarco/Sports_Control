package com.practicavolley.ennovic.sportscontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.practicavolley.ennovic.sportscontrol.Conexiones.Conexion;
import com.practicavolley.ennovic.sportscontrol.Modelos.Entreno;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InicioEntrenamiento extends AppCompatActivity {

    //Entreno iniciar

    EditText descripcion;
    Button iniciar, actualizar, parar;
    Spinner spinner;
    String[] datos = null, datosid;
    String tmp = "", identificador = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_entrenamiento);

        descripcion = (EditText) findViewById(R.id.descripcion_id);
        iniciar = (Button) findViewById(R.id.btnagregar);
        actualizar = (Button) findViewById(R.id.btnagregar2);
        parar = (Button) findViewById(R.id.btnagregar3);

        actualizar.setEnabled(false);
        parar.setEnabled(false);
        spinner = (Spinner) findViewById(R.id.spinner);

        this.datoscheck();

        //spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos));

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

        parar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pararentreno();
            }
        });

    }

    public void registrar() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "registrar-entrenos.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Entreno user = new Entreno();
                try {
                    JSONObject objresultado = new JSONObject(response);
                    String estadox = objresultado.get("estado").toString();
                    identificador = objresultado.get("id").toString();
                    if (!estadox.equalsIgnoreCase("exito")) {
                        //Toast.makeText(this,"errot",Toast.LENGTH_LONG).show();
                        Toast.makeText(InicioEntrenamiento.this, "error", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(InicioEntrenamiento.this, identificador, Toast.LENGTH_LONG).show();
                        iniciar.setEnabled(false);
                        descripcion.setEnabled(false);
                        actualizar.setEnabled(true);
                        parar.setEnabled(true);
                        spinner.setEnabled(false);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("gps", "1010");
                params.put("entrenop", tmp);
                params.put("descripcion", descripcion.getText().toString());

                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void pararentreno() {

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "actualizar-entrenos.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Entreno user = new Entreno();
                try {
                    JSONObject objresultado = new JSONObject(response);
                    String estadox = objresultado.get("estado").toString();
                    if (!estadox.equalsIgnoreCase("exito")) {
                        //Toast.makeText(this,"errot",Toast.LENGTH_LONG).show();
                        Toast.makeText(InicioEntrenamiento.this, "error", Toast.LENGTH_LONG).show();
                    } else {
                        //Toast.makeText(InicioEntrenamiento.this, "error",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(InicioEntrenamiento.this, HomeActivity.class);
                        startActivity(intent);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", identificador);

                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void datoscheck() {


        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "listar-entrenop.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //listarusr.setText(response);

                try {
                    JSONObject root = new JSONObject(response);
                    final JSONArray arrsemanas = root.getJSONArray("entrenop");
                    datos = new String[arrsemanas.length()];
                    datosid = new String[arrsemanas.length()];
                    if (arrsemanas.length() > 0) {

                        for (int i = 0; i < arrsemanas.length(); i++) {
                            JSONObject arrsemana = arrsemanas.getJSONObject(i);
                            datosid[i] = arrsemana.getString("id");
                            datos[i] = arrsemana.getString("nombre");
                            Log.d("datos", arrsemana.getString("nombre"));
                        }


                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(InicioEntrenamiento.this, android.R.layout.simple_spinner_item, datos); //selected item will look like a spinner set from XML

                        spinner.setAdapter(spinnerArrayAdapter);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                                Toast.makeText(adapterView.getContext(), datosid[pos].toString(), Toast.LENGTH_SHORT).show();
                                tmp = datosid[pos].toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


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
