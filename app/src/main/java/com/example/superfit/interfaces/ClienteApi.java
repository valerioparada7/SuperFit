package com.example.superfit.interfaces;

import com.example.superfit.models.AlertasModel;
import com.example.superfit.models.AntropometriaModel;
import com.example.superfit.models.ClientesModel;
import com.example.superfit.models.CuestionarioModel;
import com.example.superfit.models.DetallerutinaModel;
import com.example.superfit.models.Imagenes;
import com.example.superfit.models.MensualidadModel;
import com.example.superfit.models.RegistroCliente;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ClienteApi {
    //Aqui llamaremos a nuestra api y sus metodos al controladores correspondientes

    //Login
    @GET("api/Login/Login")
    public Call<MensualidadModel> Login(@Query("User") String User, @Query("Pass") String Pass);

    //Obtener los datos personales del cliente
    @GET("api/Clientes/GetCliente")
    public Call<ClientesModel> GetCliente(@Query("Id_cliente") int Id_cliente);

    //Actualizar los datos del cliente
    @PUT("api/Clientes/UpdateCliente")
    public Call<Boolean> UpdateCliente(@Body ClientesModel ClientesMdl);

    //Actualizar foto de perfil
    @POST("api/Clientes/UpdateClienteFoto")
    public Call<Boolean> UpdateClienteFoto(@Body Imagenes imagenes,@Query("Id_cliente") int Id_cliente);


    //Obtener las mensualidaddes
    @GET("api/Clientes/GetMensualidad")
    public Call<MensualidadModel> GetMensualidad(@Query("IdCliente") int IdCliente);

    @GET("api/Clientes/GetMensualidades")
    public Call<List<MensualidadModel>> GetMensualidades(@Query("IdCliente") int IdCliente);

    //Obtener las medidas por una mensualidad
    @GET("api/Clientes/GetAsesoriaantropometria")
    public Call<List<AntropometriaModel>> GetAsesoriaantropometria(@Query("Id_mensualidad") int Id_mensualidad);

    //Registar una nueva medida a una mensualidad
    @POST("api/Clientes/RegistrarMedidas")
    public Call<AlertasModel> RegistrarMedidas(@Body AntropometriaModel antropometriaModel,@Query("Id_Cliente") int Id_Cliente);

    //Obtener el cuestionario o actualizarlo
    @GET("api/Clientes/GetCuestionario")
    public Call<CuestionarioModel> GetCuestionario(@Query("IdCliente") int IdCliente);

    @POST("api/Clientes/UpdateCuestionario")
    public Call<AlertasModel> UpdateCuestionario(@Body CuestionarioModel cuestionarioModel);

    //Registro completo
    @POST("api/Login/RegistroCompleto")
    public Call<AlertasModel> RegistroCompleto(@Body RegistroCliente Registro);

    //Registro completo para las imagenes
    @POST("api/Login/UpdateImagenes")
    public Call<AlertasModel> UpdateImagenes(@Body Imagenes imagenes);

    //Registro de pago
    @POST("api/Clientes/PagoMes")
    public Call<AlertasModel> PagoMes(@Query("imagen") String imagen,@Query("IdCliente") int IdCliente,
                                      @Query("Idmes") int Idmes,@Query("monto") double monto,
                                        @Query("descripcion") String descripcion);
}
