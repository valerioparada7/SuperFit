package com.example.superfit.Clases;

import com.example.superfit.models.MensualidadModel;

public class Antropometria {
    public int Id;
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
    public String Fecharegistro;

    public Antropometria(int id, double peso, int altura, double IMC, double brazoderechorelajado, double brazoderechofuerza, double brazoizquierdorelajado, double brazoizquierdofuerza, double cintura, double cadera, double piernaizquierda, double piernaderecho, double pantorrilladerecha, double pantorrillaizquierda, String fotofrontal, String fotoperfil, String fotoposterior, String fecharegistro) {
        Id = id;
        Peso = peso;
        Altura = altura;
        this.IMC = IMC;
        Brazoderechorelajado = brazoderechorelajado;
        Brazoderechofuerza = brazoderechofuerza;
        Brazoizquierdorelajado = brazoizquierdorelajado;
        Brazoizquierdofuerza = brazoizquierdofuerza;
        Cintura = cintura;
        Cadera = cadera;
        Piernaizquierda = piernaizquierda;
        Piernaderecho = piernaderecho;
        Pantorrilladerecha = pantorrilladerecha;
        Pantorrillaizquierda = pantorrillaizquierda;
        Fotofrontal = fotofrontal;
        Fotoperfil = fotoperfil;
        Fotoposterior = fotoposterior;
        Fecharegistro = fecharegistro;
    }


    public int getId() {
        return Id;
    }

    public double getPeso() {
        return Peso;
    }

    public int getAltura() {
        return Altura;
    }

    public double getIMC() {
        return IMC;
    }

    public double getBrazoderechorelajado() {
        return Brazoderechorelajado;
    }

    public double getBrazoderechofuerza() {
        return Brazoderechofuerza;
    }

    public double getBrazoizquierdorelajado() {
        return Brazoizquierdorelajado;
    }

    public double getBrazoizquierdofuerza() {
        return Brazoizquierdofuerza;
    }

    public double getCintura() {
        return Cintura;
    }

    public double getCadera() {
        return Cadera;
    }

    public double getPiernaizquierda() {
        return Piernaizquierda;
    }

    public double getPiernaderecho() {
        return Piernaderecho;
    }

    public double getPantorrilladerecha() {
        return Pantorrilladerecha;
    }

    public double getPantorrillaizquierda() {
        return Pantorrillaizquierda;
    }

    public String getFotofrontal() {
        return Fotofrontal;
    }

    public String getFotoperfil() {
        return Fotoperfil;
    }

    public String getFotoposterior() {
        return Fotoposterior;
    }

    public String getFecharegistro() {
        return Fecharegistro;
    }
}
