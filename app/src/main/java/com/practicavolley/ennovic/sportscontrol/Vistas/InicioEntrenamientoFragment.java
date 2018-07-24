package com.practicavolley.ennovic.sportscontrol.Vistas;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.practicavolley.ennovic.sportscontrol.InicioEntrenamiento;
import com.practicavolley.ennovic.sportscontrol.Modelos.Entreno;
import com.practicavolley.ennovic.sportscontrol.Modelos.Usuario;
import com.practicavolley.ennovic.sportscontrol.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class InicioEntrenamientoFragment extends Fragment {

    //Entreno iniciar
    EditText descripcion;
    Button iniciar, actualizar, parar;
    Spinner spinner;
    String[] datos = null, datosid;
    String tmp = "", identificador = "";
    LinearLayout layout_check;
    ArrayList<Integer> chekedList = new ArrayList<Integer>();
    ArrayList<Integer> chekedguardList = new ArrayList<Integer>();

    //gps
    TextView latitud, longitud;
    private String gpsrp, gpsfin;
    EditText e_latitud, e_longitud;
    LocationManager locationManager;
    LocationListener locationListener;


    View view;

    public InicioEntrenamientoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_inicio_entrenamiento, container, false);

        descripcion = (EditText) view.findViewById(R.id.descripcion_id);
        iniciar = (Button) view.findViewById(R.id.btnagregar);
        actualizar = (Button) view.findViewById(R.id.btnagregar2);
        parar = (Button) view.findViewById(R.id.btnagregar3);

        //gps
        //latitud = (TextView) findViewById(R.id.t_latitud_id);
        e_latitud = (EditText) view.findViewById(R.id.e_latitud_id);

        actualizar.setEnabled(false);

        parar.setEnabled(false);
        spinner = (Spinner) view.findViewById(R.id.spinner);

        layout_check = (LinearLayout) view.findViewById(R.id.base_layout);

        //this.listarAthletas();
        this.datoscheck();

        //spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos));

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "ENTRENAMIENTO INICIADO", Toast.LENGTH_LONG).show();
                registrar();
            }
        });

        parar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "ENTRENAMIENTO DETENIDO", Toast.LENGTH_LONG).show();
                pararentreno();
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "ACTUALIZANDO...", Toast.LENGTH_LONG).show();

                actualizarentreno();
            }
        });

        //gps
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocationInfo(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
            }
        };
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
                updateLocationInfo(lastKnownLocation);
            }
        }

        return view;
    }

    private View.OnClickListener ckListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            boolean checked = ((CheckBox) view).isChecked();
            if (checked) {
                chekedList.add(id);
            } else {
                chekedList.remove(new Integer(id));
            }
        }
    };

    public void registrar() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());

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
                        Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                    } else {
                        //Toast.makeText(InicioEntrenamiento.this, identificador,Toast.LENGTH_LONG).show();

                        for (Integer marcados : chekedList) {
                            String cadena = marcados + "";

                            registrarAthletas(tmp, cadena);
                            chekedguardList.add(marcados);


                        }
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
                params.put("gps", e_latitud.getText().toString());
                params.put("entrenop", tmp);
                params.put("descripcion", descripcion.getText().toString());

                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void registrarAthletas(final String entreno, final String athleta) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "registrar-athletas.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Entreno user = new Entreno();
                try {
                    JSONObject objresultado = new JSONObject(response);
                    String estadox = objresultado.get("estado").toString();
                    if (!estadox.equalsIgnoreCase("exito")) {
                        //Toast.makeText(this,"errot",Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(), "error athleta", Toast.LENGTH_LONG).show();
                    } else {
                        //Toast.makeText(InicioEntrenamiento.this, "exito ",Toast.LENGTH_LONG).show();
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
                params.put("entreno", entreno);
                params.put("athleta", athleta);

                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void pararentreno() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "actualizar-entrenos.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Entreno user = new Entreno();
                try {
                    JSONObject objresultado = new JSONObject(response);
                    String estadox = objresultado.get("estado").toString();
                    if (!estadox.equalsIgnoreCase("exito")) {
                        //Toast.makeText(this,"errot",Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                    } else {
                        //Toast.makeText(InicioEntrenamiento.this, "error",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), InicioEntrenamiento.class);
                        intent.putExtra("DATOS_USER", user);
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
                params.put("gps2", e_latitud.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void actualizarentreno() {
        for (Integer marcados : chekedList) {
            boolean estado = false;
            for (Integer reco : chekedguardList) {
                if (marcados == reco) {
                    estado = true;

                }
            }
            if (!estado) {
                String cadena = marcados + "";
                registrarAthletas(tmp, cadena);
                chekedguardList.add(marcados);

            }

        }
    }

    public void datoscheck() {


        RequestQueue queue = Volley.newRequestQueue(getActivity());


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


                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, datos); //selected item will look like a spinner set from XML

                        spinner.setAdapter(spinnerArrayAdapter);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                                // Toast.makeText(adapterView.getContext(),datosid[pos].toString(), Toast.LENGTH_SHORT).show();
                                tmp = datosid[pos].toString();
                                listarAthletas(tmp);
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

    public void listarAthletas(final String id) {

        layout_check.removeAllViews();


        RequestQueue queue = Volley.newRequestQueue(getActivity());


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "listar-athletasentreno.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //listarusr.setText(response);

                try {
                    JSONObject root = new JSONObject(response);
                    final JSONArray arrsemanas = root.getJSONArray("athleta");

                    if (arrsemanas.length() > 0) {

                        for (int i = 0; i < arrsemanas.length(); i++) {
                            JSONObject arrsemana = arrsemanas.getJSONObject(i);

                            CheckBox cb = new CheckBox(getActivity());
                            cb.setId(Integer.valueOf(arrsemana.getInt("athlete")));
                            cb.setText(arrsemana.getString("nombre"));
                            cb.setOnClickListener(ckListener);
                            layout_check.addView(cb);


                            Log.d("datos", arrsemana.getString("nombre"));
                        }


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
                params.put("id", id);

                return params;
            }

        };
        queue.add(stringRequest);


    }

    //gps
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startListening();
        }
    }

    public void startListening() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    public void updateLocationInfo(Location location) {


        //latitud.setText("Latitud: " + Double.toString(location.getLatitude()));
        //longitud.setText("Longitud: " + Double.toString(location.getLongitude()));
        e_latitud.setText("" + Double.toString(location.getLatitude()) + " , " + "" + Double.toString(location.getLongitude()));
        gpsrp = ("" + Double.toString(location.getLatitude()) + " , " + "" + Double.toString(location.getLongitude()));
        gpsfin = ("" + Double.toString(location.getLatitude()) + " , " + "" + Double.toString(location.getLongitude()));
        //latitud.setText("Latitud: " + Double.toString(location.getLatitude()) + " - " + "Longitud: " + Double.toString(location.getLongitude()));
        //e_longitud.setText("Longitud: " + Double.toString(location.getLongitude()));

    }
}
