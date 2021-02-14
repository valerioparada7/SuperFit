package com.example.superfit.interfaces;

import com.example.superfit.models.ClientesModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClienteApi {
    //Aqui llamaremos a nuestra api y sus metodos al controladores correspondientes

    @GET("api/Login/")
    public Call<ClientesModel> Login(@Query("User") String User, @Query("Pass") String Pass);
}
