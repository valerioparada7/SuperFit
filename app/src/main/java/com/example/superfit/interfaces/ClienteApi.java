package com.example.superfit.interfaces;

import com.example.superfit.models.Clientes;
import com.example.superfit.models.Usuarios;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ClienteApi {
    //Aqui llamaremos a nuestra api y sus metodos al controladores correspondientes

    @GET("api/Login/")
    public Call<Clientes> Login(@Query("User") String User,@Query("Pass") String Pass);
}
