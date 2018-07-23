//FRAGMENT

package com.practicavolley.ennovic.sportscontrol.Vistas;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.practicavolley.ennovic.sportscontrol.Conexiones.Conexion;
import com.practicavolley.ennovic.sportscontrol.EntrenoProgramado;
import com.practicavolley.ennovic.sportscontrol.HomeActivity;
import com.practicavolley.ennovic.sportscontrol.Modelos.Entrenop;
import com.practicavolley.ennovic.sportscontrol.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class EntrenoProgramadoFragment extends Fragment {

    //Entreno programado

    Button btnfecha, btnhorainicio, btnhorafin;
    EditText efecha, ehora, fhora;
    private int dia, mes, ano, hora, minutos;

    EditText nombre, fecha, hinicio, hfin, lugar, descripcion;
    Button agregar;
    Spinner spinner;
    String[] datos = null, datosid;
    String tmp = "";
    View view;

    public EntrenoProgramadoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_entreno_programado, container, false);

        nombre = (EditText) view.findViewById(R.id.nombre_id);
        lugar = (EditText) view.findViewById(R.id.lugar_id);

        descripcion = (EditText) view.findViewById(R.id.descripcion_id);
        agregar = (Button) view.findViewById(R.id.btnagregar);

        spinner = (Spinner) view.findViewById(R.id.spinner);

        //HORA Y FECHA
        btnfecha = (Button) view.findViewById(R.id.btn_fecha);
        btnhorainicio = (Button) view.findViewById(R.id.btn_hora_inicio);
        btnhorafin = (Button) view.findViewById(R.id.btn_hora_fin);
        fecha = (EditText) view.findViewById(R.id.fecha_id);
        hinicio = (EditText) view.findViewById(R.id.hinicio_id);
        hfin = (EditText) view.findViewById(R.id.hfin_id);

        btnfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fecha();
            }
        });
        btnhorainicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                horaInicio();
            }
        });

        btnhorafin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                horaFin();
            }
        });

        this.datoscheck();

        //spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos));

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Registro exitoso...", Toast.LENGTH_LONG).show();
                registrar();

            }
        });


        return view;
    }

    public void fecha(){
        final Calendar c= Calendar.getInstance();

        dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH);
        ano=c.get(Calendar.WEEK_OF_YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //fecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                fecha.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
            }
        }
                ,dia,mes,ano);
        datePickerDialog.show();
    }

    public void horaInicio(){
        final Calendar c= Calendar.getInstance();
        hora=c.get(Calendar.HOUR_OF_DAY);
        minutos=c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hinicio.setText(hourOfDay+":"+minute);
            }
        },hora,minutos,false);
        timePickerDialog.show();
    }

    public void horaFin(){
        final Calendar c= Calendar.getInstance();
        hora=c.get(Calendar.HOUR_OF_DAY);
        minutos=c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hfin.setText(hourOfDay+":"+minute);
            }
        },hora,minutos,false);
        timePickerDialog.show();
    }

    public void registrar(){
        RequestQueue queue= Volley.newRequestQueue(getActivity());

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "registrar-entrenosprogramados.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Entrenop user=new Entrenop();
                try {
                    JSONObject objresultado=new JSONObject(response);
                    String estadox=objresultado.get("estado").toString();
                    if(!estadox.equalsIgnoreCase("exito")){
                        //Toast.makeText(this,"errot",Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                    }else{

                        Toast.makeText(getActivity(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(getContext(), HomeActivity.class);
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


        RequestQueue queue= Volley.newRequestQueue(getActivity());


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


                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, datos); //selected item will look like a spinner set from XML

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
