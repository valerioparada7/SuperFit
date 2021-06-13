package com.example.superfit.models;

public class RegistroCliente {
    public ClientesModel Cliente;
    public MensualidadModel Mensualidad;
    public CuestionarioModel Cuestionario;
    public AntropometriaModel Medidas;

    public ClientesModel getCliente() {
        return Cliente;
    }

    public void setCliente(ClientesModel cliente) {
        Cliente = cliente;
    }

    public MensualidadModel getMensualidad() {
        return Mensualidad;
    }

    public void setMensualidad(MensualidadModel mensualidad) {
        Mensualidad = mensualidad;
    }

    public CuestionarioModel getCuestionario() {
        return Cuestionario;
    }

    public void setCuestionario(CuestionarioModel cuestionario) {
        Cuestionario = cuestionario;
    }

    public AntropometriaModel getMedidas() {
        return Medidas;
    }

    public void setMedidas(AntropometriaModel medidas) {
        Medidas = medidas;
    }
}
