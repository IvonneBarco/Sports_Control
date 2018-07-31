package com.practicavolley.ennovic.sportscontrol.Clases;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
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
import com.practicavolley.ennovic.sportscontrol.Actividades.InicioEntrenamiento;
import com.practicavolley.ennovic.sportscontrol.Conexiones.Conexion;
import com.practicavolley.ennovic.sportscontrol.Modelos.Entreno;
import com.practicavolley.ennovic.sportscontrol.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServicioEntrenamiento extends Service {


    @Override
    public void onCreate(){

    }

    @Override
    public int onStartCommand(Intent intent, int flag, int idProcess){



        return START_STICKY;
    }

    @Override
    public void onDestroy(){

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
