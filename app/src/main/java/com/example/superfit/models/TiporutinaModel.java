package com.example.superfit.models;

public class TiporutinaModel {
    public int Id_tiporutina;
    public String Tipo ;
    public String Descripcion;

    public int getId_tiporutina() {
        return Id_tiporutina;
    }

    public void setId_tiporutina(int id_tiporutina) {
        Id_tiporutina = id_tiporutina;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
