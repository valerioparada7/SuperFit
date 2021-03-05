package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.superfit.Clases.AdaptadorEjercicios;
import com.example.superfit.Clases.AdaptadorMensualidadDetalle;
import com.example.superfit.Clases.Ejercicios;
import com.example.superfit.Clases.MensualidadDetalle;
import com.example.superfit.interfaces.ClienteApi;
import com.example.superfit.interfaces.DetallerutinaApi;
import com.example.superfit.models.DetallerutinaModel;
import com.example.superfit.models.MensualidadModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Mensualidad_menu extends AppCompatActivity {
    public ListView lvitems;
    public AdaptadorMensualidadDetalle adptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensualidad_menu);

        lvitems=(ListView)findViewById(R.id.ListMensualidades);
        GetMensualidad();
    }

    // Job http://192.168.56.1:8081/
    // Home http://192.168.100.11:8081/
    // web superfit.somee.com
    public void GetMensualidad(){
        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        int Idcliente =preferences.getInt("Id_Cliente",0);
        Log.w("->",String.valueOf(Idcliente));
        /*Retrofit retrofit=new Retrofit.Builder().baseUrl("http://192.168.56.1:8081/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ClienteApi mensualidadapi = retrofit.create(ClienteApi.class);
        Call<List<MensualidadModel>> call = mensualidadapi.GetMensualidad(Idcliente);
        call.enqueue(new Callback<List<MensualidadModel>>() {
            @Override
            public void onResponse(Call<List<MensualidadModel>> call, Response<List<MensualidadModel>> response) {
                try {
                    if(response.isSuccessful()){
                        List<MensualidadModel> mensualidadlist = response.body();
                        ArrayList<MensualidadDetalle> menusalidadarrayList = new ArrayList<>();
                        for (int i=0;i<mensualidadlist.size();i++){
                            //web superfit.somee.com
                            menusalidadarrayList.add(
                                    new com.example.superfit.Clases.MensualidadDetalle(
                                            mensualidadlist.get(i).Tiporutina.Tipo,
                                            mensualidadlist.get(i).Tiporutina.Descripcion,
                                            mensualidadlist.get(i).TipoEntrenamiento.Tipo_entrenamiento,
                                            mensualidadlist.get(i).Mes.Mes,
                                            mensualidadlist.get(i).Estatus.Descripcion,
                                            mensualidadlist.get(i).Fecha_inicio,
                                            mensualidadlist.get(i).Fecha_fin
                                    ));
                        }
                        Mensualidad(menusalidadarrayList);
                    }
                }
                catch (Exception ex){
                    Toast.makeText(Mensualidad_menu.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<MensualidadModel>> call, Throwable t) {
                Toast.makeText(Mensualidad_menu.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde",Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    public void Mensualidad(ArrayList<MensualidadDetalle> Lista){
        adptador = new AdaptadorMensualidadDetalle(this,Lista);
        lvitems.setAdapter(adptador);
    }
}