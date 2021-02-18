package com.example.superfit.Clases;


import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.request.target.ViewTarget;

public class Ejercicios {
    public String Imgfoto;
    public String TituloEjercicio;
    public String DescripcionEjercicio;
    public String Repeticiones;
    public String Series;


    public Ejercicios(String imgfoto, String titutlo, String descripcion,String repeticiones,String series){
        this.Imgfoto=imgfoto;
        this.TituloEjercicio=titutlo;
        this.DescripcionEjercicio=descripcion;
        this.Repeticiones =repeticiones;
        this.Series=series;
    }

    public String getImgfoto() {
        return Imgfoto;
    }

    public String getTituloEjercicio() {
        return TituloEjercicio;
    }

    public String getDescripcionEjercicio() {
        return DescripcionEjercicio;
    }

    public String getRepeticiones() {
        return Repeticiones;
    }

    public String getSeries() {
        return Series;
    }


}
