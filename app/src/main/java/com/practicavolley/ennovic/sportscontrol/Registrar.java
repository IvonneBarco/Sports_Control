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
import com.practicavolley.ennovic.sportscontrol.Modelos.Entrenop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registrar extends AppCompatActivity {

    //Entreno programado

    EditText nombre,fecha,hinicio,hfin,lugar,descripcion;
    Button agregar;
    Spinner spinner;
    String[] datos = null, datosid;
    String tmp="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        nombre=(EditText) findViewById(R.id.nombre_id);
        fecha=(EditText) findViewById(R.id.fecha_id);
        hinicio=(EditText) findViewById(R.id.hinicio_id);
        hfin=(EditText) findViewById(R.id.hfin_id);
        lugar=(EditText) findViewById(R.id.lugar_id);

        descripcion=(EditText) findViewById(R.id.descripcion_id);
        agregar=(Button) findViewById(R.id.btnagregar);

        spinner = (Spinner) findViewById(R.id.spinner);

        this.datoscheck();

        //spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos));

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

    }

    public void registrar(){
        RequestQueue queue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "registrar-entrenosprogramados.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Entrenop user=new Entrenop();
                try {
                    JSONObject objresultado=new JSONObject(response);
                    String estadox=objresultado.get("estado").toString();
                    if(!estadox.equalsIgnoreCase("exito")){
                        //Toast.makeText(this,"errot",Toast.LENGTH_LONG).show();
                        Toast.makeText(Registrar.this, "error", Toast.LENGTH_SHORT).show();
                    }else{

                        Intent intent =new Intent(Registrar.this, HomeActivity.class);
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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("nombre", nombre.getText().toString());
                params.put("fecha",fecha.getText().toString());
                params.put("hinicio",hinicio.getText().toString());
                params.put("hfin",hfin.getText().toString());
                params.put("lugar",lugar.getText().toString());
                params.put("semana_id",tmp);
                params.put("descripcion",descripcion.getText().toString());

                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void datoscheck(){


        RequestQueue queue= Volley.newRequestQueue(Registrar.this);


        StringRequest stringRequest=new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES+ "listar-semanas.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //listarusr.setText(response);

                try {
                    JSONObject root = new JSONObject(response);
                    final JSONArray arrsemanas = root.getJSONArray("semanas");
                    datos=new String[arrsemanas.length()];
                    datosid=new String[arrsemanas.length()];
                    if (arrsemanas.length()>0) {

                        for (int i = 0; i < arrsemanas.length(); i++) {
                            JSONObject arrsemana = arrsemanas.getJSONObject(i);
                            datosid[i]=arrsemana.getString("id");
                            datos[i]=arrsemana.getString("nombre");
                            Log.d("datos",arrsemana.getString("nombre"));
                        }


                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Registrar.this, android.R.layout.simple_spinner_item, datos); //selected item will look like a spinner set from XML

                        spinner.setAdapter(spinnerArrayAdapter);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
                            {
                                Toast.makeText(adapterView.getContext(),datosid[pos].toString(), Toast.LENGTH_SHORT).show();
                                tmp=datosid[pos].toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent)
                            {    }
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
        }){


        };
        queue.add(stringRequest);


    }
}
