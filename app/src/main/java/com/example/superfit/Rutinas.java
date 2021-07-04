package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.superfit.Clases.AdaptadorEjercicios;
import com.example.superfit.Clases.Ejercicios;
import com.example.superfit.interfaces.CatalogoApi;
import com.example.superfit.interfaces.DetallerutinaApi;
import com.example.superfit.models.AlertasModel;
import com.example.superfit.models.DetallerutinaModel;
import com.example.superfit.models.TiporutinaModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.layout.simple_list_item_1;

public class Rutinas extends AppCompatActivity {
    //Pagina Actual nueva https://www.bsite.net/valerioparada/
    String PaginaWeb ="https://www.bsite.net/valerioparada/";

    //Spinner para los dias
    ArrayList<String> Diasarray = new ArrayList<String>();
    Spinner listDias;

    //Spinner para los sets
    ArrayList<String> Setsarray = new ArrayList<String>();
    Spinner listSets;

    //para los ejercicios
    public ListView lvitems;
    public AdaptadorEjercicios adptador;
    final Cargando cargando = new Cargando(Rutinas.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutinas);
        listDias =(Spinner)findViewById(R.id.ListaDiasId);
        listSets =(Spinner)findViewById(R.id.ListaSetsId);

        lvitems=(ListView)findViewById(R.id.lisviewejercicios);
        ObtenerDias();
        ObtenerSets();
        listDias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ObtenerSets();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listSets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GetDetalleRutinaEjercicios();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void ObtenerDias(){

        String[] dias = { "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado" };
        for (int i = 0; i < dias.length; i++)
        {
            Diasarray.add(dias[i]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_edittext_sexo,Diasarray);
        listDias.setAdapter(adapter);
    }

    public void ObtenerSets(){
        SharedPreferences preferences = getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        int IdMensualidad = preferences.getInt("IdMensualidad",0);
        int IdEstatus = preferences.getInt("IdEstatus",0);
        int Iddia =listDias.getSelectedItemPosition();
        Iddia+=2;
        if(IdMensualidad!=0){
        // Job http://192.168.56.1:8081/
            // Home http://:192.168.100.118081/
            //web superfit.somee.com
            Retrofit retrofit=new Retrofit.Builder().baseUrl(PaginaWeb)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            DetallerutinaApi detalle = retrofit.create(DetallerutinaApi.class);
            Call<List<DetallerutinaModel>> call = detalle.GetDetalleRutinaSets(IdMensualidad,IdEstatus,Iddia);
            call.enqueue(new Callback<List<DetallerutinaModel>>() {
                @Override
                public void onResponse(Call<List<DetallerutinaModel>> call, Response<List<DetallerutinaModel>> response) {
                    try {
                        if(response.isSuccessful()){
                            List<DetallerutinaModel> result = response.body();
                            if(result.size()>0){
                                Setsarray.clear();
                                for (int i=0;i<result.size();i++){
                                    Setsarray.add(""+result.get(i).TipoSet);
                                }
                                AgrgarSetsSpiner(Setsarray);
                                GetDetalleRutinaEjercicios();
                            }
                            else {
                                Setsarray.clear();
                                Setsarray.add("No hay sets asignados");
                                AgrgarSetsSpiner(Setsarray);
                            }
                        }
                        else{
                            Toast.makeText(Rutinas.this,"No se realizo la conexion "+response.message(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception ex){
                        Toast.makeText(Rutinas.this,"Ocurrio un error: \r\n" +ex.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<DetallerutinaModel>> call, Throwable t) {
                    Toast.makeText(Rutinas.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde \r\n Error:"+t.getMessage().toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(Rutinas.this,"No hay sets para este dia",Toast.LENGTH_SHORT).show();
        }
    }

    public void AgrgarSetsSpiner(ArrayList<String> Lista){
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_edittext_sexo,Lista);
        listSets.setAdapter(adapter);
    }

    //Agregar los ejercicios
    // Job http://192.168.56.1:8081/
    // Home http://192.168.100.11:8081/
    // web superfit.somee.com
    public void GetDetalleRutinaEjercicios(){
        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        int IdMensualidad =preferences.getInt("IdMensualidad",0);
        int IdEstatus =preferences.getInt("IdEstatus",0);
        int Iddia =listDias.getSelectedItemPosition();
        int Idset =listSets.getSelectedItemPosition();
        Iddia+=2;
        Idset+=1;
        if(IdMensualidad!=0) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(PaginaWeb)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            DetallerutinaApi rutinaapi = retrofit.create(DetallerutinaApi.class);
            Call<List<DetallerutinaModel>> call = rutinaapi.GetDetalleRutinaEjercicios(IdMensualidad, IdEstatus, Iddia, Idset);
            call.enqueue(new Callback<List<DetallerutinaModel>>() {
                @Override
                public void onResponse(Call<List<DetallerutinaModel>> call, Response<List<DetallerutinaModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            List<DetallerutinaModel> rutinalist = response.body();
                            ArrayList<com.example.superfit.Clases.Ejercicios> ejerciciosArrayList = new ArrayList<>();
                            for (int i = 0; i < rutinalist.size(); i++) {
                                String Url_Imagen = PaginaWeb + rutinalist.get(i).Ejercicios.Ubicacion_imagen;
                                ejerciciosArrayList.add(new com.example.superfit.Clases.Ejercicios(
                                        Url_Imagen,
                                        rutinalist.get(i).Ejercicios.Ejercicio,
                                        rutinalist.get(i).Ejercicios.Descripcion,
                                        String.valueOf(rutinalist.get(i).Repeticiones),
                                        String.valueOf(rutinalist.get(i).Series)
                                ));
                            }
                            Exercicies(ejerciciosArrayList);
                        }
                    }
                    catch (Exception ex) {
                        Toast.makeText(Rutinas.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<DetallerutinaModel>> call, Throwable t) {
                    Toast.makeText(Rutinas.this, "No se conecto al servidor verifique su conexion \r\nintente mas tarde", Toast.LENGTH_SHORT).show();
                }

            });
        }
        else{
            Toast.makeText(Rutinas.this,"No hay ejercicios para este set",Toast.LENGTH_SHORT).show();
        }
    }

    public void Exercicies(ArrayList<Ejercicios> Lista){
        adptador = new AdaptadorEjercicios(this,Lista);
        lvitems.setAdapter(adptador);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            Intent intent = new Intent(Rutinas.this,Perfil.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}