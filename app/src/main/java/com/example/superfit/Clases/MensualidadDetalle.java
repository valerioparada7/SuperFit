package com.example.superfit.Clases;

public class MensualidadDetalle {
    public String Tipo;
    public String DescripcionTiporutina;
    public String Tipo_entrenamiento;
    public String Mes;
    public String DescripcionEstatus;
    public String Fecha_inicio;
    public String Fecha_fin;

    public MensualidadDetalle(String tipo,String descripcionTiporutina,String tipo_entrenamiento,
                              String mes,String descripcionEstatus,String fecha_inicio,String fecha_fin){

        this.Tipo=tipo;
        this.DescripcionTiporutina	=descripcionTiporutina;
        this.Tipo_entrenamiento	=tipo_entrenamiento;
        this.Mes=mes;
        this.DescripcionEstatus	=descripcionEstatus;
        this.Fecha_inicio=fecha_inicio;
        this.Fecha_fin=fecha_fin;

    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getDescripcionTiporutina() {
        return DescripcionTiporutina;
    }

    public void setDescripcionTiporutina(String descripcionTiporutina) {
        DescripcionTiporutina = descripcionTiporutina;
    }

    public String getTipo_entrenamiento() {
        return Tipo_entrenamiento;
    }

    public void setTipo_entrenamiento(String tipo_entrenamiento) {
        Tipo_entrenamiento = tipo_entrenamiento;
    }

    public String getMes() {
        return Mes;
    }

    public void setMes(String mes) {
        Mes = mes;
    }

    public String getDescripcionEstatus() {
        return DescripcionEstatus;
    }

    public void setDescripcionEstatus(String descripcionEstatus) {
        DescripcionEstatus = descripcionEstatus;
    }

    public String getFecha_inicio() {
        return Fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        Fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return Fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        Fecha_fin = fecha_fin;
    }
}
