//CARGAR IMAGEN

package com.practicavolley.ennovic.sportscontrol.Actividades;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.practicavolley.ennovic.sportscontrol.Adapters.AdaptadorAtletas;
import com.practicavolley.ennovic.sportscontrol.Adapters.AdaptadorAtletasAsistencia;
import com.practicavolley.ennovic.sportscontrol.Clases.Preferences;
import com.practicavolley.ennovic.sportscontrol.Clases.VolleySingleton;
import com.practicavolley.ennovic.sportscontrol.Conexiones.Conexion;
import com.practicavolley.ennovic.sportscontrol.Modelos.AtletaVo;
import com.practicavolley.ennovic.sportscontrol.Modelos.DeportesVo;
import com.practicavolley.ennovic.sportscontrol.Modelos.Entreno;
import com.practicavolley.ennovic.sportscontrol.Modelos.LigasVo;
import com.practicavolley.ennovic.sportscontrol.Modelos.Usuario;
import com.practicavolley.ennovic.sportscontrol.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class CargarImagen extends AppCompatActivity {

    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "SportControl";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
    private String path;//almacena la ruta de la imagen
    File fileImagen;
    Bitmap bitmap;

    private final int MIS_PERMISOS = 100;
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;

    EditText campoDescripcion, campoEntrenamiento, campoGps;
    Button botonIniciar, botonParar, botonActualizar;
    ImageButton btnFoto;
    ImageView imgFoto;
    ProgressDialog progreso;

    CoordinatorLayout layoutRegistrar;//permisos

    // RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    StringRequest stringRequest;

    //gps
    TextView latitud, longitud;
    private String gpsrp, gpsfin;
    EditText e_latitud, e_longitud;
    LocationManager locationManager;
    LocationListener locationListener;
    android.app.AlertDialog alertaGPS = null;

    private String IDUSUARIO, ROLEUSUARIO;
    int idliga = 1;

    //RECYCLER
    ArrayList<AtletaVo> listaAtletas;
    //Refencia al reclycler
    RecyclerView recyclerAtletas;


    //Notificacion
    NotificationCompat.Builder notificacion;
    int notificacionId = 1;
    String channelId = "my_channerl_01";
    long[] pattern = new long[]{1000, 500, 1000};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_imagen);

        IDUSUARIO = Preferences.obtenerPreferencesString(this, Preferences.PREFERENCE_ID_USUARIO_LOGIN);
        ROLEUSUARIO = Preferences.obtenerPreferencesString(this, Preferences.PREFERENCE_ROLE_USUARIO_LOGIN);


        // Codigo flecha atras...
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //regresar...
                DialogoRegresarrEntrenamiento();
            }
        });

        // * Codigo flecha atras...

        campoEntrenamiento = (EditText) findViewById(R.id.campoEntrenamiento);
        campoDescripcion = (EditText) findViewById(R.id.campoDescripcion);
        campoGps = (EditText) findViewById(R.id.campoGps);
        botonIniciar = (Button) findViewById(R.id.btnIniciar);
        botonActualizar = (Button) findViewById(R.id.btnActualizar);
        botonParar = (Button) findViewById(R.id.btnParar);
        btnFoto = (ImageButton) findViewById(R.id.btnFoto);

        //gps
        e_latitud = (EditText) findViewById(R.id.campoGps);
        campoEntrenamiento.setText(String.valueOf(getIntent().getStringExtra("nom_entrenamiento")));
        //gps
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
                updateLocationInfo(lastKnownLocation);
            }
        }

        //Inicio * Recycler
        listaAtletas = new ArrayList<>();
        recyclerAtletas = (RecyclerView) findViewById(R.id.Recycler_Asistencia_Atletas_Id);
        recyclerAtletas.setLayoutManager(new LinearLayoutManager(this));
        recyclerAtletas.setHasFixedSize(true);

        listarAsistencia(); //listando usuarios
        //Fin * Recycler

        imgFoto = (ImageView) findViewById(R.id.imgFoto);

        layoutRegistrar = (CoordinatorLayout) findViewById(R.id.idLayoutRegistrar);

        //Permisos
        if (solicitaPermisosVersionesSuperiores()) {
            btnFoto.setEnabled(true);
        } else {
            btnFoto.setEnabled(false);
        }

        botonIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogoIniciarEntrenamiento();
            }
        });

        botonParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(InicioEntrenamiento.this, "ENTRENAMIENTO DETENIDO", Toast.LENGTH_LONG).show();
                DialogoFinalizarEntrenamiento();
            }
        });

        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.info(CargarImagen.this, "ACTUALIZANDO...", Toast.LENGTH_LONG).show();
                //actualizarentreno();
                notificacionEntrenamientoIniciado();
            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogOpciones();
            }
        });

    }

    private void listarAsistencia() {

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(CargarImagen.this);

        // Initialize a new JsonArrayRequest instance
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "listar-athletas.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        AtletaVo athlete = null;

                        try {
                            JSONObject objresultado = new JSONObject(response);
                            JSONArray athletes = objresultado.getJSONArray("athleta");

                            if (athletes.length() <= 0) {
                                Drawable icon = getResources().getDrawable(R.drawable.ic_empty);
                                Toasty.normal(CargarImagen.this, "No se han encontrado datos", icon).show();

                            } else {

                                for (int i = 0; i < athletes.length(); i++) {
                                    athlete = new AtletaVo();
                                    JSONObject objAtletas = athletes.getJSONObject(i);

                                    athlete.setIdatleta(String.valueOf(objAtletas.optInt("athlete_id")));
                                    athlete.setNombreatleta(objAtletas.optString("nombre"));
                                    athlete.setApellidoatleta(objAtletas.optString("apellido"));
                                    athlete.setNivelrendimientoatleta(objAtletas.optString("nivelrendimiento"));
                                    athlete.setFoto(R.drawable.weightlifting);
                                    listaAtletas.add(athlete);


                                }
                                AdaptadorAtletasAsistencia adapter = new AdaptadorAtletasAsistencia(listaAtletas);
                                recyclerAtletas.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Drawable icon = getResources().getDrawable(R.drawable.ic_sin_conexion);
                            Toasty.normal(CargarImagen.this, "No se puede establecer una conexión", icon).show();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //
            }
        }) {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(4));
                params.put("liga", String.valueOf(idliga));
                return params;
            }
        };

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(stringRequest);
    }

    private void mostrarDialogOpciones() {
        final CharSequence[] opciones = {"Tomar Foto", "Elegir de Galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")) {
                    abriCamara();
                } else {
                    if (opciones[i].equals("Elegir de Galeria")) {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Seleccione"), COD_SELECCIONA);
                    } else {
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.show();
    }


    private void abriCamara() {
        File miFile = new File(Environment.getExternalStorageDirectory(), DIRECTORIO_IMAGEN);
        boolean isCreada = miFile.exists();

        if (isCreada == false) {
            isCreada = miFile.mkdirs();
        }

        if (isCreada == true) {
            Long consecutivo = System.currentTimeMillis() / 1000;
            String nombre = consecutivo.toString() + ".jpg";

            path = Environment.getExternalStorageDirectory() + File.separator + DIRECTORIO_IMAGEN
                    + File.separator + nombre;//indicamos la ruta de almacenamiento

            fileImagen = new File(path);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));

            ////
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                String authorities = this.getPackageName() + ".provider";
                Uri imageUri = FileProvider.getUriForFile(this, authorities, fileImagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));
            }
            startActivityForResult(intent, COD_FOTO);

            ////

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case COD_SELECCIONA:
                Uri miPath = data.getData();
                imgFoto.setImageURI(miPath);

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), miPath);
                    imgFoto.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case COD_FOTO:
                MediaScannerConnection.scanFile(this, new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("Path", "" + path);
                            }
                        });

                bitmap = BitmapFactory.decodeFile(path);
                imgFoto.setImageBitmap(bitmap);

                break;
        }
        bitmap = redimensionarImagen(bitmap, 600, 800);
    }

    private Bitmap redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {

        int ancho = bitmap.getWidth();
        int alto = bitmap.getHeight();

        if (ancho > anchoNuevo || alto > altoNuevo) {
            float escalaAncho = anchoNuevo / ancho;
            float escalaAlto = altoNuevo / alto;

            Matrix matrix = new Matrix();
            matrix.postScale(escalaAncho, escalaAlto);

            return Bitmap.createBitmap(bitmap, 0, 0, ancho, alto, matrix, false);

        } else {
            return bitmap;
        }
    }

    //permisos
    ////////////////

    private boolean solicitaPermisosVersionesSuperiores() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {//validamos si estamos en android menor a 6 para no buscar los permisos
            return true;
        }

        //validamos si los permisos ya fueron aceptados
        if ((this.checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) && this.checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }


        if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE) || (shouldShowRequestPermissionRationale(CAMERA)))) {
            cargarDialogoRecomendacion();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MIS_PERMISOS);
        }


        return false;//implementamos el que procesa el evento dependiendo de lo que se defina aqui
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MIS_PERMISOS) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {//el dos representa los 2 permisos
                Toast.makeText(this, "Permisos aceptados", Toast.LENGTH_SHORT);
                btnFoto.setEnabled(true);
            }
        } else {
            solicitarPermisosManual();
        }

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertaNoGps();
        }

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startListening();
        }
    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones = {"si", "no"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(this);//estamos en fragment
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getApplication().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                } else {
                    Toast.makeText(CargarImagen.this, "Los permisos no fueron aceptados", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
                }
            }
        });
        dialogo.show();
    }

    ///////////////


    private void cargarWebService() {

        progreso = new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        progreso.show();

        stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "registrar-entrenamientos.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progreso.hide();

                if (response.trim().equalsIgnoreCase("registra")) {
                    notificacionEntrenamientoIniciado();
                    Toasty.success(CargarImagen.this, "ENTRENAMIENTO INICIADO", Toast.LENGTH_LONG).show();

                    DesactivarBoton(botonIniciar, false);
                    campoDescripcion.setEnabled(false);
                    botonActualizar.setEnabled(true);
                    botonParar.setEnabled(true);

                } else {
                    //Toast.makeText(CargarImagen.this, "No se ha registrado ", Toast.LENGTH_SHORT).show();
                    Drawable icon = getResources().getDrawable(R.drawable.ic_sin_conexion);
                    Toasty.normal(CargarImagen.this, "Ups! parece que no tienes conexión", icon).show();
                    Log.i("RESPUESTA: ", "" + response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CargarImagen.this, "No se ha podido conectar", Toast.LENGTH_SHORT).show();
                progreso.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String id_entrenamiento = String.valueOf(getIntent().getStringExtra("id_entrenamiento"));
                String descripcion = campoDescripcion.getText().toString();

                String imagen = convertirImgString(bitmap);

                Map<String, String> parametros = new HashMap<>();
                parametros.put("gps", e_latitud.getText().toString());
                parametros.put("entrenoprogramado_id", id_entrenamiento);
                parametros.put("descripcion", descripcion);
                parametros.put("imagen", imagen);

                return parametros;
            }
        };
        //request.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);
    }

    public void pararentreno() {
        DesactivarBoton(botonIniciar, false);


    }

    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, array);
        byte[] imagenByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte, Base64.DEFAULT);

        return imagenString;
    }

    public void startListening() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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

    private void AlertaNoGps() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS esta desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        alertaGPS = builder.create();
        alertaGPS
                .show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            } else {
                locationManager.removeUpdates(locationListener);
            }
        } else {
            locationManager.removeUpdates(locationListener);
        }


    }

    @Override
    public void onBackPressed() {
        DialogoSalirEntrenamiento();
    }

    //Menu home

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            //Preferences.savePreferencesBoolean(this, false, Preferences.PREFERENCES_ESTADO_SWITCH);
            /*Intent i = new Intent(InicioEntrenamiento.this, OpcionesActivity.class);
            startActivity(i);
            finish();*/
            DialogoRegresarrEntrenamiento();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ResourceAsColor")
    public void DesactivarBoton(Button boton, Boolean b) {
        boton.setEnabled(false);
        boton.setBackgroundColor(R.color.colorGray);
    }


    @SuppressLint("ResourceAsColor")
    public void ActivarBoton(Button botona, Boolean c) {
        botona.setEnabled(true);
        botona.setBackgroundColor(R.color.colorPrimaryDark);
    }



    //Dialogos

    public void DialogoRegresarrEntrenamiento() {
        android.support.v7.app.AlertDialog.Builder alerta = new android.support.v7.app.AlertDialog.Builder(CargarImagen.this);
        alerta.setMessage("")
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(CargarImagen.this, Entrenamientos.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Codigo de continuar en la app
                        dialogInterface.cancel();
                    }
                });
        android.support.v7.app.AlertDialog titulo = alerta.create();
        titulo.setTitle("¿Salir del entrenamiento?");
        titulo.show();
    }

    public void notificacionEntrenamientoIniciado() {
        final NotificationManager notificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);

        notificacion = new NotificationCompat.Builder(this, null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //Nombre del canal
            CharSequence name = "Entrenamiento Iniciado";

            //Descripción
            String descripcion = "Entrenamiento en curso";
            int importancia = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mchannel = new NotificationChannel(channelId, name, importancia);

            //Configuracion canal de notificación
            mchannel.setDescription(descripcion);
            mchannel.enableLights(true);

            //Configuraciones de notificación
            mchannel.setLightColor(Color.RED);
            mchannel.enableVibration(true);
            mchannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(mchannel);

            notificacion = new NotificationCompat.Builder(this, channelId);


        }

        notificacion.setSmallIcon(R.drawable.logo_sportscontrol).setContentTitle("Entrenamiento en curso").setContentText("Toca aquí para ir al entrenamiento");
        notificacion.setAutoCancel(true);
        notificacion.setTicker("El entrenamiento ha iniciado");
        //notificacion.setUsesChronometer(true);
        notificacion.setVibrate(pattern);
        //Defino que la notificacion sea permamente
        //notificacion.setOngoing(true);
        Intent notificationIntent = new Intent(CargarImagen.this, InicioEntrenamiento.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);


        PendingIntent pendingIntent = PendingIntent.getActivity(CargarImagen.this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        notificationManager.cancel(1);
        notificacion.setContentIntent(pendingIntent);

        notificacion.setChannelId(channelId);
        notificationManager.notify(notificacionId, notificacion.build());
    }

    public void DialogoIniciarEntrenamiento() {
        android.support.v7.app.AlertDialog.Builder alerta = new android.support.v7.app.AlertDialog.Builder(CargarImagen.this);
        alerta.setMessage("Presione SI para iniciar el entrenamiento o CANCELAR para regresar a la pantalla anterior")
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cargarWebService();
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Codigo de continuar en la app
                        dialogInterface.cancel();
                    }
                });
        android.support.v7.app.AlertDialog titulo = alerta.create();
        titulo.setTitle("¿Iniciar Entrenamiento?");
        titulo.show();
    }

    public void DialogoSalirEntrenamiento() {
        android.support.v7.app.AlertDialog.Builder alerta = new android.support.v7.app.AlertDialog.Builder(CargarImagen.this);
        alerta.setMessage("Tenga en cuenta que si usted ha iniciado el entrenamiento y sale de esta pantalla, este se perdera y no podrá iniciarlo nuevamente")
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(CargarImagen.this, OpcionesActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Codigo de continuar en la app
                        dialogInterface.cancel();
                    }
                });
        android.support.v7.app.AlertDialog titulo = alerta.create();
        titulo.setTitle("¿Está seguro que desea salir?");
        titulo.show();
    }

    public void DialogoFinalizarEntrenamiento() {
        android.support.v7.app.AlertDialog.Builder alerta = new android.support.v7.app.AlertDialog.Builder(CargarImagen.this);
        alerta.setMessage("Presione SI para finalizar el entrenamiento o CANCELAR para continuar en el mismo")
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toasty.error(CargarImagen.this, "ENTRENAMIENTO DETENIDO", Toast.LENGTH_LONG).show();
                        pararentreno();
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Codigo de continuar en la app
                        dialogInterface.cancel();
                    }
                });
        android.support.v7.app.AlertDialog titulo = alerta.create();
        titulo.setTitle("¿Finalizar Entrenamiento?");
        titulo.show();
    }
}
