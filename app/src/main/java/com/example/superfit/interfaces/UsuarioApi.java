package com.example.superfit.interfaces;

import com.example.superfit.models.UsuariosModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UsuarioApi {
    @GET("api/Login")
    public Call<UsuariosModel> Login(@Query("User") String User, @Query("Pass") String Pass);
}
