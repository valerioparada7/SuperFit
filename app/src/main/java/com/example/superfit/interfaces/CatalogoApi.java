package com.example.superfit.interfaces;

import com.example.superfit.models.MensualidadModel;
import com.example.superfit.models.TipoentrenamientoModel;
import com.example.superfit.models.TiporutinaModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CatalogoApi {
    @GET("api/Catalogo/GetTypeRutines")
    public Call<List<TiporutinaModel>> GetTypeRutines();

    @GET("api/Catalogo/GetTypeTraining")
    public Call<List<TipoentrenamientoModel>> GetTypeTraining();

}
