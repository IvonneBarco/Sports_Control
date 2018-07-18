package com.practicavolley.ennovic.sportscontrol.Modelos;

public class LigasVo {

    private String idliga;
    private String nombreliga;
    private int foto;

    public LigasVo() {
    }

    //Constructor
    public LigasVo(String idliga, String nombreliga, int foto) {
        this.idliga = idliga;
        this.nombreliga = nombreliga;
        this.foto = foto;
    }

    public String getIdliga() {
        return idliga;
    }

    public void setIdliga(String idliga) {
        this.idliga = idliga;
    }

    public String getNombreliga() {
        return nombreliga;
    }

    public void setNombreliga(String nombreliga) {
        this.nombreliga = nombreliga;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
