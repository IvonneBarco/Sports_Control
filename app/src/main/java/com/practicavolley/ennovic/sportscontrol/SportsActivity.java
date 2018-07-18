package com.practicavolley.ennovic.sportscontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.practicavolley.ennovic.sportscontrol.Conexiones.Conexion;

import java.util.HashMap;
import java.util.Map;

public class SportsActivity extends AppCompatActivity {

    TextView listarDeportes = (TextView) findViewById(R.id.todos_los_deportes);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);

        RequestQueue queue = Volley.newRequestQueue(SportsActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "listar-deportes.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        listarDeportes.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", "1");
                params.put("role", "admin");
                return params;
            }

        };
        queue.add(stringRequest);
    }
}
