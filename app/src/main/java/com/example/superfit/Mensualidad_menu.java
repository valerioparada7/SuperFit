package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
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
    // Job http://192.168.56.1:8081/
    // Home http://192.168.100.11:8081/
    // Pagina http://superfit.somee.com/
    // Pagina nueva http://valerioparada7-001-site1.etempurl.com/
    //Pagina Actual nueva https://www.bsite.net/valerioparada/
    String PaginaWeb ="https://www.bsite.net/valerioparada/";

    public ListView lvitems;
    public AdaptadorMensualidadDetalle adptador;
    public ArrayList<MensualidadDetalle> menusalidadarrayList = new ArrayList<>();
    final Cargando cargando = new Cargando(Mensualidad_menu.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensualidad_menu);
        lvitems=(ListView)findViewById(R.id.medidaslista);
        cargando.cargardialogo();
        GetMensualidades();
        lvitems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Mensualidad_menu.this,mensualidadmedidasmenu.class);
                intent.putExtra("ModeloMes",menusalidadarrayList.get(position));
                startActivity(intent);
            }
        });
    }

    public void GetMensualidades(){
        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        int Idcliente = preferences.getInt("Idcliente",0);
        Retrofit retrofit=new Retrofit.Builder().baseUrl(PaginaWeb)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ClienteApi mensualidadapi = retrofit.create(ClienteApi.class);
        Call<List<MensualidadModel>> call = mensualidadapi.GetMensualidades(Idcliente);
        call.enqueue(new Callback<List<MensualidadModel>>() {
            @Override
            public void onResponse(Call<List<MensualidadModel>> call, Response<List<MensualidadModel>> response) {
                try {
                    if(response.isSuccessful()){
                        List<MensualidadModel> mensualidadlist = response.body();
                        if(mensualidadlist.size()>0) {
                            Mensualidad(mensualidadlist);
                        }
                        else{
                            Toast.makeText(Mensualidad_menu.this,"No hay mensualidades",Toast.LENGTH_SHORT).show();
                        }
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
        });
    }

    public void Mensualidad(List<MensualidadModel> Lista){
        for (int i = 0; i < Lista.size(); i++) {
            menusalidadarrayList.add(
                    new com.example.superfit.Clases.MensualidadDetalle(
                            Lista.get(i).Id_mensualidad,
                            Lista.get(i).Tiporutina.Tipo,
                            Lista.get(i).Tiporutina.Descripcion,
                            Lista.get(i).TipoEntrenamiento.Tipo_entrenamiento,
                            Lista.get(i).Mes.Mes,
                            Lista.get(i).Estatus.Descripcion,
                            Lista.get(i).Fechainicio,
                            Lista.get(i).Fechafin
                    ));
        }
        adptador = new AdaptadorMensualidadDetalle(this,menusalidadarrayList);
        lvitems.setAdapter(adptador);
        cargando.ocultar();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            Intent intent = new Intent(Mensualidad_menu.this,Perfil.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}