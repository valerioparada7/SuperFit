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
    public String Fechafin ;
    public String Fechainicio;
    public String SinfechaAsignadaI ;
    public String SinfechaAsignadaF;
    public PagosmensualModel PagoMes;



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

    public String getFechafin() {
        return Fechafin;
    }

    public void setFechafin(String fechafin) {
        Fechafin = fechafin;
    }

    public String getFechainicio() {
        return Fechainicio;
    }

    public void setFechainicio(String fechainicio) {
        Fechainicio = fechainicio;
    }

    public String getSinfechaAsignadaI() {
        return SinfechaAsignadaI;
    }

    public void setSinfechaAsignadaI(String sinfechaAsignadaI) {
        SinfechaAsignadaI = sinfechaAsignadaI;
    }

    public String getSinfechaAsignadaF() {
        return SinfechaAsignadaF;
    }

    public void setSinfechaAsignadaF(String sinfechaAsignadaF) {
        SinfechaAsignadaF = sinfechaAsignadaF;
    }

    public PagosmensualModel getPagomes() {
        return PagoMes;
    }

    public void setPagomes(PagosmensualModel pagomes) {
        this.PagoMes = pagomes;
    }
}
