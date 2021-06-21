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
    public String Fotofrontal;
    public String Fotoperfil;
    public String Fotoposterior;
    public String Foto_frontal64 ;
    public String Foto_perfil64;
    public String Foto_posterior64;
    public String Fecharegistro;

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

    public String getFotofrontal() {
        return Fotofrontal;
    }

    public void setFotofrontal(String fotofrontal) {
        Fotofrontal = fotofrontal;
    }

    public String getFotoperfil() {
        return Fotoperfil;
    }

    public void setFotoperfil(String fotoperfil) {
        Fotoperfil = fotoperfil;
    }

    public String getFotoposterior() {
        return Fotoposterior;
    }

    public void setFotoposterior(String fotoposterior) {
        Fotoposterior = fotoposterior;
    }

    public String getFecharegistro() {
        return Fecharegistro;
    }

    public void setFecharegistro(String fecharegistro) {
        Fecharegistro = fecharegistro;
    }

    public String getFoto_frontal64() {
        return Foto_frontal64;
    }

    public void setFoto_frontal64(String foto_frontal64) {
        Foto_frontal64 = foto_frontal64;
    }

    public String getFoto_perfil64() {
        return Foto_perfil64;
    }

    public void setFoto_perfil64(String foto_perfil64) {
        Foto_perfil64 = foto_perfil64;
    }

    public String getFoto_posterior64() {
        return Foto_posterior64;
    }

    public void setFoto_posterior64(String foto_posterior64) {
        Foto_posterior64 = foto_posterior64;
    }
}
