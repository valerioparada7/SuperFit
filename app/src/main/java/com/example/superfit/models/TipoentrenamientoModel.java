package com.example.superfit.models;

public class TipoentrenamientoModel {
    public int Id_TipoEntrenamiento ;
    public String Clave_Entrenamiento;
    public String Tipo_entrenamiento;

    public int getId_TipoEntrenamiento() {
        return Id_TipoEntrenamiento;
    }

    public void setId_TipoEntrenamiento(int id_TipoEntrenamiento) {
        Id_TipoEntrenamiento = id_TipoEntrenamiento;
    }

    public String getClave_Entrenamiento() {
        return Clave_Entrenamiento;
    }

    public void setClave_Entrenamiento(String clave_Entrenamiento) {
        Clave_Entrenamiento = clave_Entrenamiento;
    }

    public String getTipo_entrenamiento() {
        return Tipo_entrenamiento;
    }

    public void setTipo_entrenamiento(String tipo_entrenamiento) {
        Tipo_entrenamiento = tipo_entrenamiento;
    }
}
