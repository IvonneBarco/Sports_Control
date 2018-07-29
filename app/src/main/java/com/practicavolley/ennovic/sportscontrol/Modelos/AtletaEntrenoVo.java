package com.practicavolley.ennovic.sportscontrol.Modelos;

public class AtletaEntrenoVo {

    private String idatleta;
    private String nombreatleta;
    private String apellidoatleta;
    private int foto;

    public AtletaEntrenoVo() {
    }

    //Constructor
    public AtletaEntrenoVo(String idatleta, String nombreatleta, String apellidoatleta, int foto) {
        this.idatleta = idatleta;
        this.nombreatleta = nombreatleta;
        this.apellidoatleta = apellidoatleta;
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




}
