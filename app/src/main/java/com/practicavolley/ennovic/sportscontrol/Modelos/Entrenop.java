package com.practicavolley.ennovic.sportscontrol.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Entrenop implements Parcelable {

    private String id;
    private String nombre;
    private String fecha;
    private String hinicio;
    private String hfin;
    private String lugar;
    private String semana_id;
    private String descripcion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHinicio() {
        return hinicio;
    }

    public void setHinicio(String hinicio) {
        this.hinicio = hinicio;
    }

    public String getHfin() {
        return hfin;
    }

    public void setHfin(String hfin) {
        this.hfin = hfin;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getSemana_id() {
        return semana_id;
    }

    public void setSemana_id(String semana_id) {
        this.semana_id = semana_id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static Creator<Entrenop> getCREATOR() {
        return CREATOR;
    }

    public Entrenop(){

    }

    protected Entrenop(Parcel in) {
        setNombre(in.readString());
        setFecha(in.readString());
        setHinicio(in.readString());
        setHfin(in.readString());
        setLugar(in.readString());
        setSemana_id(in.readString());
        setDescripcion(in.readString());
    }

    public static final Creator<Entrenop> CREATOR = new Creator<Entrenop>() {
        @Override
        public Entrenop createFromParcel(Parcel in) {
            return new Entrenop(in);
        }

        @Override
        public Entrenop[] newArray(int size) {
            return new Entrenop[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(getId());
        dest.writeString(getNombre());
        dest.writeString(getFecha());
        dest.writeString(getHinicio());
        dest.writeString(getHfin());
        dest.writeString(getLugar());
        dest.writeString(getSemana_id());
        dest.writeString(getDescripcion());
    }

}
