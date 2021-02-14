package com.example.superfit.models;

import java.time.LocalDateTime;
import java.util.Date;

public class AntropometriaModel {
    public int Id ;
    public MensualidadModel Mensualidad ;
    public double Peso ;
    public int Altura ;
    public double IMC ;
    public double Brazoderechorelajado ;
    public double Brazoderechofuerza ;
    public double Brazoizquierdorelajado ;
    public double Brazoizquierdofuerza ;
    public double Cintura ;
    public double Cadera ;
    public double Piernaizquierda ;
    public double Piernaderecho ;
    public double Pantorrilladerecha ;
    public double Pantorrillaizquierda ;
    public Date Fecha_registro ;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public MensualidadModel getMensualidad() {
        return Mensualidad;
    }

    public void setMensualidad(MensualidadModel mensualidad) {
        Mensualidad = mensualidad;
    }

    public double getPeso() {
        return Peso;
    }

    public void setPeso(double peso) {
        Peso = peso;
    }

    public int getAltura() {
        return Altura;
    }

    public void setAltura(int altura) {
        Altura = altura;
    }

    public double getIMC() {
        return IMC;
    }

    public void setIMC(double IMC) {
        this.IMC = IMC;
    }

    public double getBrazoderechorelajado() {
        return Brazoderechorelajado;
    }

    public void setBrazoderechorelajado(double brazoderechorelajado) {
        Brazoderechorelajado = brazoderechorelajado;
    }

    public double getBrazoderechofuerza() {
        return Brazoderechofuerza;
    }

    public void setBrazoderechofuerza(double brazoderechofuerza) {
        Brazoderechofuerza = brazoderechofuerza;
    }

    public double getBrazoizquierdorelajado() {
        return Brazoizquierdorelajado;
    }

    public void setBrazoizquierdorelajado(double brazoizquierdorelajado) {
        Brazoizquierdorelajado = brazoizquierdorelajado;
    }

    public double getBrazoizquierdofuerza() {
        return Brazoizquierdofuerza;
    }

    public void setBrazoizquierdofuerza(double brazoizquierdofuerza) {
        Brazoizquierdofuerza = brazoizquierdofuerza;
    }

    public double getCintura() {
        return Cintura;
    }

    public void setCintura(double cintura) {
        Cintura = cintura;
    }

    public double getCadera() {
        return Cadera;
    }

    public void setCadera(double cadera) {
        Cadera = cadera;
    }

    public double getPiernaizquierda() {
        return Piernaizquierda;
    }

    public void setPiernaizquierda(double piernaizquierda) {
        Piernaizquierda = piernaizquierda;
    }

    public double getPiernaderecho() {
        return Piernaderecho;
    }

    public void setPiernaderecho(double piernaderecho) {
        Piernaderecho = piernaderecho;
    }

    public double getPantorrilladerecha() {
        return Pantorrilladerecha;
    }

    public void setPantorrilladerecha(double pantorrilladerecha) {
        Pantorrilladerecha = pantorrilladerecha;
    }

    public double getPantorrillaizquierda() {
        return Pantorrillaizquierda;
    }

    public void setPantorrillaizquierda(double pantorrillaizquierda) {
        Pantorrillaizquierda = pantorrillaizquierda;
    }

    public Date getFecha_registro() {
        return Fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        Fecha_registro = fecha_registro;
    }
}
