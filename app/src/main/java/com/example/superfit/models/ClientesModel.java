package com.example.superfit.models;

public class ClientesModel {
    public int Id_cliente ;
    public String Clave_cliente ;
    public String Nombres ;
    public String Apellido_paterno ;
    public String Apellido_materno ;
    public int Edad ;
    public double Telefono ;
    public String Correo_electronico ;
    public Boolean Estado ;
    public String Apodo ;
    public String Contraseña;
    public String Foto_perfil;
    public String Sexo;
    public Boolean Validar ;

    public int getId_cliente() {
        return Id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        Id_cliente = id_cliente;
    }

    public String getClave_cliente() {
        return Clave_cliente;
    }

    public void setClave_cliente(String clave_cliente) {
        Clave_cliente = clave_cliente;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getApellido_paterno() {
        return Apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        Apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return Apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        Apellido_materno = apellido_materno;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int edad) {
        Edad = edad;
    }

    public double getTelefono() {
        return Telefono;
    }

    public void setTelefono(double telefono) {
        Telefono = telefono;
    }

    public String getCorreo_electronico() {
        return Correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        Correo_electronico = correo_electronico;
    }

    public Boolean getEstado() {
        return Estado;
    }

    public void setEstado(Boolean estado) {
        Estado = estado;
    }

    public String getApodo() {
        return Apodo;
    }

    public void setApodo(String apodo) {
        Apodo = apodo;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }

    public String getFoto_perfil() {
        return Foto_perfil;
    }

    public void setFoto_perfil(String foto_perfil) {
        Foto_perfil = foto_perfil;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }

    public Boolean getValidar() {
        return Validar;
    }

    public void setValidar(Boolean validar) {
        Validar = validar;
    }
}
