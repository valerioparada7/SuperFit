package com.example.superfit.models;

public class AlertasModel {
    public String Mensaje ;
    public Boolean Result ;
    public int Id;

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public Boolean getResult() {
        return Result;
    }

    public void setResult(Boolean result) {
        Result = result;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
