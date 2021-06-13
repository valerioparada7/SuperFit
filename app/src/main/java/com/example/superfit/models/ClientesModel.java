package com.example.superfit.models;

public class ClientesModel {
    public int Id_Cliente ;
    public String Clave_Cliente ;
    public String Nombres ;
    public String Apellido_Paterno ;
    public String Apellido_Materno ;
    public int Edad ;
    public double Telefono ;
    public String Correo_electronico ;
    public String Apodo ;
    public String Contraseña;
    public String Sexo;
    public String Foto_perfil;
    public Boolean Validar ;

    public int getId_Cliente() {
        return Id_Cliente;
    }

    public void setId_Cliente(int id_Cliente) {
        Id_Cliente = id_Cliente;
    }

    public String getClave_Cliente() {
        return Clave_Cliente;
    }

    public void setClave_Cliente(String clave_Cliente) {
        Clave_Cliente = clave_Cliente;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getApellido_Paterno() {
        return Apellido_Paterno;
    }

    public void setApellido_Paterno(String apellido_Paterno) {
        Apellido_Paterno = apellido_Paterno;
    }

    public String getApellido_Materno() {
        return Apellido_Materno;
    }

    public void setApellido_Materno(String apellido_Materno) {
        Apellido_Materno = apellido_Materno;
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

    public Boolean getValidar() {
        return Validar;
    }

    public void setValidar(Boolean validar) {
        Validar = validar;
    }

    public String getFoto_perfil() {
        return Foto_perfil;
    }

    public void setFotoperfil(String fotoperfil) {
        Foto_perfil = fotoperfil;
    }


    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }
}
