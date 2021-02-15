package com.example.superfit.models;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

public class MensualidadModel {
    public int Id_mensualidad;
    public ClientesModel Cliente;
    public TiporutinaModel Tiporutina;
    public MesesModel Mes;
    public EstatusModel Estatus;
    public TipoentrenamientoModel TipoEntrenamiento;
    public String Fecha_inicio;
    public String  Fecha_fin;

    public int getId_mensualidad() {
        return Id_mensualidad;
    }

    public void setId_mensualidad(int id_mensualidad) {
        Id_mensualidad = id_mensualidad;
    }

    public ClientesModel getCliente() {
        return Cliente;
    }

    public void setCliente(ClientesModel cliente) {
        Cliente = cliente;
    }

    public TiporutinaModel getTiporutina() {
        return Tiporutina;
    }

    public void setTiporutina(TiporutinaModel tiporutina) {
        Tiporutina = tiporutina;
    }

    public MesesModel getMes() {
        return Mes;
    }

    public void setMes(MesesModel mes) {
        Mes = mes;
    }

    public EstatusModel getEstatus() {
        return Estatus;
    }

    public void setEstatus(EstatusModel estatus) {
        Estatus = estatus;
    }

    public TipoentrenamientoModel getTipoEntrenamiento() {
        return TipoEntrenamiento;
    }

    public void setTipoEntrenamiento(TipoentrenamientoModel tipoEntrenamiento) {
        TipoEntrenamiento = tipoEntrenamiento;
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
