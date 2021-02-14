package com.example.superfit.models;

public class DetallerutinaModel {
    public int Id_detallerutina ;
    public DiasModel Dias ;
    public EjerciciosModel Ejercicios ;
    public MensualidadModel Mensualidad ;
    public RutinasModel Rutinas ;
    public int Repeticiones ;
    public int Series ;
    public int Tipo_set ;
    public String TipoSet ;

    public int getId_detallerutina() {
        return Id_detallerutina;
    }

    public void setId_detallerutina(int id_detallerutina) {
        Id_detallerutina = id_detallerutina;
    }

    public DiasModel getDias() {
        return Dias;
    }

    public void setDias(DiasModel dias) {
        Dias = dias;
    }

    public EjerciciosModel getEjercicios() {
        return Ejercicios;
    }

    public void setEjercicios(EjerciciosModel ejercicios) {
        Ejercicios = ejercicios;
    }

    public MensualidadModel getMensualidad() {
        return Mensualidad;
    }

    public void setMensualidad(MensualidadModel mensualidad) {
        Mensualidad = mensualidad;
    }

    public RutinasModel getRutinas() {
        return Rutinas;
    }

    public void setRutinas(RutinasModel rutinas) {
        Rutinas = rutinas;
    }

    public int getRepeticiones() {
        return Repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        Repeticiones = repeticiones;
    }

    public int getSeries() {
        return Series;
    }

    public void setSeries(int series) {
        Series = series;
    }

    public int getTipo_set() {
        return Tipo_set;
    }

    public void setTipo_set(int tipo_set) {
        Tipo_set = tipo_set;
    }

    public String getTipoSet() {
        return TipoSet;
    }

    public void setTipoSet(String tipoSet) {
        TipoSet = tipoSet;
    }
}
