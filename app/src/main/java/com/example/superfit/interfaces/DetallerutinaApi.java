package com.example.superfit.interfaces;

import com.example.superfit.models.DetallerutinaModel;
import com.example.superfit.models.DiasModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DetallerutinaApi {
    //Regresar nuemero de sets por dia
    @GET("api/Clientes")
    public Call<List<DetallerutinaModel>> GetDetalleRutinaSets(@Query("IdMensualidad") int IdMensualidad,
                                                               @Query("IdEstatusMes") int IdEstatusMes,
                                                               @Query("IdDIa") int IdDIa);

    //Regresar nuemero de ejercicios por sets correspondientes al dia
    @GET("api/Calendario")
    public Call<List<DetallerutinaModel>> GetDetalleRutinaEjercicios(@Query("IdMensualidad") int IdMensualidad,
                                                                     @Query("IdEstatusMes") int IdEstatusMes,
                                                                     @Query("IdDIa") int IdDIa,
                                                                     @Query("TipoSet") int TipoSet);
}
