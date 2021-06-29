package com.example.superfit.models;

import android.renderscript.ScriptIntrinsicYuvToRGB;

public class PagosmensualModel {
    public int Id_pago;
    public MensualidadModel mensualidad;
    public double Monto ;
    public String Fechapago;
    public String Descripcion ;
    public String Ubicacion_imagen_pago ;

    public int getId_pago() {
        return Id_pago;
    }

    public void setId_pago(int id_pago) {
        Id_pago = id_pago;
    }

    public MensualidadModel getMensualidad() {
        return mensualidad;
    }

    public void setMensualidad(MensualidadModel mensualidad) {
        this.mensualidad = mensualidad;
    }

    public double getMonto() {
        return Monto;
    }

    public void setMonto(double monto) {
        Monto = monto;
    }

    public String getFechapago() {
        return Fechapago;
    }

    public void setFechapago(String fechapago) {
        Fechapago = fechapago;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getUbicacion_imagen_pago() {
        return Ubicacion_imagen_pago;
    }

    public void setUbicacion_imagen_pago(String ubicacion_imagen_pago) {
        Ubicacion_imagen_pago = ubicacion_imagen_pago;
    }
}
