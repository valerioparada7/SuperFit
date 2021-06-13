package com.example.superfit.models;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;
import java.util.Date;
public class CuestionarioModel {
    public int Id_cuestionario;
    @Expose(deserialize = false, serialize = false)
    public  ClientesModel  Cliente;

    public String Clave_cuestionario;
    public  Boolean  Padece_enfermedad;
    public String Medicamento_prescrito_medico;
    public  Boolean  lesiones;
    public  String Alguna_recomendacion_lesiones;
    public  Boolean  Fuma;
    public  int  Veces_semana_fuma;
    public  Boolean  Alcohol;
    public  int  Veces_semana_alcohol;
    public  Boolean  Actividad_fisica;
    public String Tipo_ejercicios;
    public String Tiempo_dedicado;
    public String Horario_entreno;
    public String MetasObjetivos;
    public String Compromisos;
    public String Comentarios;
    public Date Fecha_registro;

    public int getId_cuestionario() {
        return Id_cuestionario;
    }

    public void setId_cuestionario(int id_cuestionario) {
        Id_cuestionario = id_cuestionario;
    }

    public ClientesModel getCliente() {
        return Cliente;
    }

    public void setCliente(ClientesModel cliente) {
        Cliente = cliente;
    }

    public String getClave_cuestionario() {
        return Clave_cuestionario;
    }

    public void setClave_cuestionario(String clave_cuestionario) {
        Clave_cuestionario = clave_cuestionario;
    }

    public Boolean getPadece_enfermedad() {
        return Padece_enfermedad;
    }

    public void setPadece_enfermedad(Boolean padece_enfermedad) {
        Padece_enfermedad = padece_enfermedad;
    }

    public String getMedicamento_prescrito_medico() {
        return Medicamento_prescrito_medico;
    }

    public void setMedicamento_prescrito_medico(String medicamento_prescrito_medico) {
        Medicamento_prescrito_medico = medicamento_prescrito_medico;
    }

    public Boolean getLesiones() {
        return lesiones;
    }

    public void setLesiones(Boolean lesiones) {
        this.lesiones = lesiones;
    }

    public String getAlguna_recomendacion_lesiones() {
        return Alguna_recomendacion_lesiones;
    }

    public void setAlguna_recomendacion_lesiones(String alguna_recomendacion_lesiones) {
        Alguna_recomendacion_lesiones = alguna_recomendacion_lesiones;
    }

    public Boolean getFuma() {
        return Fuma;
    }

    public void setFuma(Boolean fuma) {
        Fuma = fuma;
    }

    public int getVeces_semana_fuma() {
        return Veces_semana_fuma;
    }

    public void setVeces_semana_fuma(int veces_semana_fuma) {
        Veces_semana_fuma = veces_semana_fuma;
    }

    public Boolean getAlcohol() {
        return Alcohol;
    }

    public void setAlcohol(Boolean alcohol) {
        Alcohol = alcohol;
    }

    public int getVeces_semana_alcohol() {
        return Veces_semana_alcohol;
    }

    public void setVeces_semana_alcohol(int veces_semana_alcohol) {
        Veces_semana_alcohol = veces_semana_alcohol;
    }

    public Boolean getActividad_fisica() {
        return Actividad_fisica;
    }

    public void setActividad_fisica(Boolean actividad_fisica) {
        Actividad_fisica = actividad_fisica;
    }

    public String getTipo_ejercicios() {
        return Tipo_ejercicios;
    }

    public void setTipo_ejercicios(String tipo_ejercicios) {
        Tipo_ejercicios = tipo_ejercicios;
    }

    public String getTiempo_dedicado() {
        return Tiempo_dedicado;
    }

    public void setTiempo_dedicado(String tiempo_dedicado) {
        Tiempo_dedicado = tiempo_dedicado;
    }

    public String getHorario_entreno() {
        return Horario_entreno;
    }

    public void setHorario_entreno(String horario_entreno) {
        Horario_entreno = horario_entreno;
    }

    public String getMetasObjetivos() {
        return MetasObjetivos;
    }

    public void setMetasObjetivos(String metasObjetivos) {
        MetasObjetivos = metasObjetivos;
    }

    public String getCompromisos() {
        return Compromisos;
    }

    public void setCompromisos(String compromisos) {
        Compromisos = compromisos;
    }

    public String getComentarios() {
        return Comentarios;
    }

    public void setComentarios(String comentarios) {
        Comentarios = comentarios;
    }

    public Date getFecha_registro() {
        return Fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        Fecha_registro = fecha_registro;
    }
}
