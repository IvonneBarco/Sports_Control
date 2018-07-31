package com.practicavolley.ennovic.sportscontrol.Modelos;

public class EntrenamientoVo {
    private String id_entrenamiento;
    private String nombre_entrenamiento;
    private String fecha_entrenamiento;
    private String horainicio_entrenameinto;
    private String horafin_entrenamiento;
    private String lugar_entrenamiento;
    private String semana_entrenamiento;
    private String descripcion_entrenamiento;

    public EntrenamientoVo(){}

    public EntrenamientoVo( String id_entrenamiento, String nombre_entrenamiento, String fecha_entrenamiento,
                            String horainicio_entrenameinto, String horafin_entrenamiento,
                            String lugar_entrenamiento, String semana_entrenamiento, String descripcion_entrenamiento){

        this.id_entrenamiento = id_entrenamiento;
        this.nombre_entrenamiento = nombre_entrenamiento;
        this.fecha_entrenamiento = fecha_entrenamiento;
        this.horainicio_entrenameinto = horainicio_entrenameinto;
        this.horafin_entrenamiento = horafin_entrenamiento;
        this.lugar_entrenamiento = lugar_entrenamiento;
        this.semana_entrenamiento = semana_entrenamiento;
        this.descripcion_entrenamiento = descripcion_entrenamiento;
    }

    public String getId_entrenamiento() {
        return id_entrenamiento;
    }

    public void setId_entrenamiento(String id_entrenamiento) {
        this.id_entrenamiento = id_entrenamiento;
    }

    public String getNombre_entrenamiento() {
        return nombre_entrenamiento;
    }

    public void setNombre_entrenamiento(String nombre_entrenamiento) {
        this.nombre_entrenamiento = nombre_entrenamiento;
    }

    public String getFecha_entrenamiento() {
        return fecha_entrenamiento;
    }

    public String setFecha_entrenamiento(String fecha_entrenamiento) {
        this.fecha_entrenamiento = fecha_entrenamiento;
        return fecha_entrenamiento;
    }

    public String getHorainicio_entrenameinto() {
        return horainicio_entrenameinto;
    }

    public void setHorainicio_entrenameinto(String horainicio_entrenameinto) {
        this.horainicio_entrenameinto = horainicio_entrenameinto;
    }

    public String getHorafin_entrenamiento() {
        return horafin_entrenamiento;
    }

    public void setHorafin_entrenamiento(String horafin_entrenamiento) {
        this.horafin_entrenamiento = horafin_entrenamiento;
    }

    public String getLugar_entrenamiento() {
        return lugar_entrenamiento;
    }

    public void setLugar_entrenamiento(String lugar_entrenamiento) {
        this.lugar_entrenamiento = lugar_entrenamiento;
    }

    public String getSemana_entrenamiento() {
        return semana_entrenamiento;
    }

    public void setSemana_entrenamiento(String semana_entrenamiento) {
        this.semana_entrenamiento = semana_entrenamiento;
    }

    public String getDescripcion_entrenamiento() {
        return descripcion_entrenamiento;
    }

    public void setDescripcion_entrenamiento(String descripcion_entrenamiento) {
        this.descripcion_entrenamiento = descripcion_entrenamiento;
    }
}
