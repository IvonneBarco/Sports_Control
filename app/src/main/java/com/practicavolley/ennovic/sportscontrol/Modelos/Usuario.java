package com.practicavolley.ennovic.sportscontrol.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Server on 02/06/2018.
 */

public class Usuario implements Parcelable {
    private String nombre;
    private int id;
    private String username;
    private String contrasena;
    private String role;

    protected Usuario(Parcel in) {
        setNombre(in.readString());
        setId(in.readInt());
        setUsername(in.readString());
        contrasena = in.readString();
        role = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public Usuario(){

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getNombre());
        dest.writeInt(getId());
        dest.writeString(getUsername());
        dest.writeString(contrasena);
        dest.writeString(getRole());
    }


    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
