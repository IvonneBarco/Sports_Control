package com.practicavolley.ennovic.sportscontrol.Modelos;

public class DeportesVo {

    private String iddeporte;
    private String nombredeporte;
    private int foto;

    public DeportesVo(){}

    //Constructor
    public DeportesVo(String iddeporte, String nombredeporte, int foto){
        this.iddeporte = iddeporte;
        this.nombredeporte = nombredeporte;
        this.foto = foto;
    }

    public String getNombredeporte() {
        return nombredeporte;
    }

    public void setNombredeporte(String nombredeporte) {
        this.nombredeporte = nombredeporte;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getIddeporte() {
        return iddeporte;
    }

    public void setIddeporte(String iddeporte) {
        this.iddeporte = iddeporte;
    }
}
