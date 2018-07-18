package com.practicavolley.ennovic.sportscontrol.Modelos;

public class AtletaVo {

    private String idatleta;
    private String nombreatleta;
    private String apellidoatleta;
    private String nivelrendimientoatleta;
    private int foto;

    public AtletaVo() {
    }

    //Constructor
    public AtletaVo(String idatleta, String nombreatleta, String apellidoatleta, String nivelrendimientoatleta, int foto) {
        this.idatleta = idatleta;
        this.nombreatleta = nombreatleta;
        this.apellidoatleta = apellidoatleta;
        this.nivelrendimientoatleta = nivelrendimientoatleta;
        this.foto = foto;
    }


    public String getIdatleta() {
        return idatleta;
    }

    public void setIdatleta(String idatleta) {
        this.idatleta = idatleta;
    }

    public String getNombreatleta() {
        return nombreatleta;
    }

    public void setNombreatleta(String nombreatleta) {
        this.nombreatleta = nombreatleta;
    }

    public String getApellidoatleta() {
        return apellidoatleta;
    }

    public void setApellidoatleta(String apellidoatleta) {
        this.apellidoatleta = apellidoatleta;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getNivelrendimientoatleta() {
        return nivelrendimientoatleta;
    }

    public void setNivelrendimientoatleta(String nivelrendimientoatleta) {
        this.nivelrendimientoatleta = nivelrendimientoatleta;
    }




}
