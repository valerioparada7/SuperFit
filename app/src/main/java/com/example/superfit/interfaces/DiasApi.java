package com.example.superfit.interfaces;

import com.example.superfit.models.DiasModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DiasApi {
    @GET("api/Calendario")
    public Call<List<DiasModel>> GetDias();
}
