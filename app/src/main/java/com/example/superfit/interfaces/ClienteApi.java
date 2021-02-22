package com.example.superfit.interfaces;

import com.example.superfit.models.AlertasModel;
import com.example.superfit.models.AntropometriaModel;
import com.example.superfit.models.ClientesModel;
import com.example.superfit.models.CuestionarioModel;
import com.example.superfit.models.MensualidadModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ClienteApi {
    //Aqui llamaremos a nuestra api y sus metodos al controladores correspondientes

    //Login
    @GET("api/Login")
    public Call<MensualidadModel> Login(@Query("User") String User, @Query("Pass") String Pass);

    @POST("api/Login/RegistrarCliente")
    public Call<AlertasModel> RegistrarCliente(@Body ClientesModel clientesModel);

    @POST("api/Login/RegistroCuestionario")
    public Call<AlertasModel> RegistroCuestionario(@Body CuestionarioModel cuestionarioModel);

    @POST("api/Login/RegistrarMensualidad")
    public Call<AlertasModel> RegistrarMensualidad(@Body MensualidadModel mensualidadModel);

    @POST("api/Login/RegistrarAntropometria")
    public Call<AlertasModel> RegistrarAntropometria(@Body AntropometriaModel antropometriaModel);



}
