package com.example.superfit.models;

public class RutinasModel {
    public int Id_rutina;
    public String Clave_rutina;
    public String Descripcion ;

    public int getId_rutina() {
        return Id_rutina;
    }

    public void setId_rutina(int id_rutina) {
        Id_rutina = id_rutina;
    }

    public String getClave_rutina() {
        return Clave_rutina;
    }

    public void setClave_rutina(String clave_rutina) {
        Clave_rutina = clave_rutina;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
