package com.example.superfit.models;

public class Usuarios {
    public int Id_Usuario  			;
    public String Clave_Usuario  	;

    public int getId_Usuario() {
        return Id_Usuario;
    }

    public void setId_Usuario(int id_Usuario) {
        Id_Usuario = id_Usuario;
    }

    public String getClave_Usuario() {
        return Clave_Usuario;
    }

    public void setClave_Usuario(String clave_Usuario) {
        Clave_Usuario = clave_Usuario;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
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

    public String Contraseña  		;
    public String Nombres  			;
    public String Apellido_Paterno  ;
    public String Apellido_Materno  ;

}
