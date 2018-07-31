package com.practicavolley.ennovic.sportscontrol.Clases;

import android.content.Context;
import android.content.SharedPreferences;

import com.practicavolley.ennovic.sportscontrol.Actividades.LoginActivity;

public class Preferences {

    public static final String STRING_PREFERENCES = "info.sportcontrol";
    public static final String PREFERENCES_ESTADO_SWITCH = "estado.switch";
    public static final String PREFERENCE_USUARIO_LOGIN = "usuario.login";
    public static final String PREFERENCE_NOMBRE_USUARIO_LOGIN = "nombre.login";
    public static final String PREFERENCE_APELLIDO_USUARIO_LOGIN = "apellido.login";
    public static final String PREFERENCE_ID_USUARIO_LOGIN = "id.login";
    public static final String PREFERENCE_ROLE_USUARIO_LOGIN = "role.login";


    public static void savePreferencesBoolean(Context c, boolean b, String key) {
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        preferences.edit().putBoolean(key, b).apply();
    }

    public static void savePreferenesString(Context c, String  b, String key) {
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        preferences.edit().putString(key, b).apply();
    }

    public static void savePreferenesInt(Context c, Integer b, String key) {
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        preferences.edit().putInt(key, b).apply();
    }


    public static boolean obtenerPreferencesBoolean(Context c, String key) {
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        return preferences.getBoolean(key, false); //Si es que nunca se ha guardado nada en esta key, entonces retornará false
    }

    public static String obtenerPreferencesString(Context c, String key) {
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        return preferences.getString(key, ""); //Si es que nunca se ha guardado nada en esta key, entonces retornará false
    }

    public static Integer obtenerPreferencesInt(Context c, String key) {
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        return preferences.getInt(key, 0); //Si es que nunca se ha guardado nada en esta key, entonces retornará false
    }

}
