package com.example.superfit.models;

public class EjerciciosModel {
    public int Id_ejercicio ;
    public String Clave_ejercicio ;
    public String Ejercicio ;
    public String Descripcion ;
    public String Ubicacion_imagen ;
    public String Posicion;
    public String Imagen;
    public int Width;
    public int Height;
    public String Tipo;

    public int getId_ejercicio() {
        return Id_ejercicio;
    }

    public void setId_ejercicio(int id_ejercicio) {
        Id_ejercicio = id_ejercicio;
    }

    public String getClave_ejercicio() {
        return Clave_ejercicio;
    }

    public void setClave_ejercicio(String clave_ejercicio) {
        Clave_ejercicio = clave_ejercicio;
    }

    public String getEjercicio() {
        return Ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        Ejercicio = ejercicio;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getPosicion() {
        return Posicion;
    }

    public void setPosicion(String posicion) {
        Posicion = posicion;
    }

    public String getUbicacion_imagen() {
        return Ubicacion_imagen;
    }

    public void setUbicacion_imagen(String ubicacion_imagen) {
        Ubicacion_imagen = ubicacion_imagen;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public int getWidth() {
        return Width;
    }

    public void setWidth(int width) {
        Width = width;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }
}
