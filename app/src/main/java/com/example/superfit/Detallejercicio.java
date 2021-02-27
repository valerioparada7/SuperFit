package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.superfit.Clases.AdaptadorEjercicios;
import com.example.superfit.Clases.Ejercicios;
import com.example.superfit.interfaces.DetallerutinaApi;
import com.example.superfit.interfaces.DiasApi;
import com.example.superfit.models.DetallerutinaModel;
import com.example.superfit.models.DiasModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.layout.simple_list_item_1;

public class Detallejercicio extends AppCompatActivity {
    public ListView lvitems;
    public AdaptadorEjercicios adptador;
    TextView rutina;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallejercicio);
        lvitems=(ListView)findViewById(R.id.lisviewejercicios);
        rutina=(TextView)findViewById(R.id.Rutinalb);
        GetDetalleRutinaEjercicios();
    }


    // Job http://192.168.56.1:8081/
    // Home http://192.168.100.11:8081/
    // web superfit.somee.com
    public void GetDetalleRutinaEjercicios(){

        //put extras para obtener el dia que selecciona
        int set = getIntent().getExtras().getInt("IdSet");
        int dia = getIntent().getExtras().getInt("IdDia");

        //variables guardadas en la aplicacion para saber que tipo de mensualidads tienes y estatus
        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        int mensualidad=preferences.getInt("Id_mensualidad",0);
        int estatus=preferences.getInt("Id_estatus",0);

        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://superfit.somee.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        DetallerutinaApi rutinaapi = retrofit.create(DetallerutinaApi.class);
        Call<List<DetallerutinaModel>> call = rutinaapi.GetDetalleRutinaEjercicios(mensualidad,estatus,dia,set);
        call.enqueue(new Callback<List<DetallerutinaModel>>() {
            @Override
            public void onResponse(Call<List<DetallerutinaModel>> call, Response<List<DetallerutinaModel>> response) {
                try {
                    if(response.isSuccessful()){
                        List<DetallerutinaModel> rutinalist = response.body();
                        rutina.setText(rutinalist.get(0).Rutinas.Descripcion);
                        ArrayList<com.example.superfit.Clases.Ejercicios> ejerciciosArrayList = new ArrayList<>();
                        for (int i=0;i<rutinalist.size();i++){
                            //web superfit.somee.com
                            String Url_Imagen="http://192.168.56.1:8081/"+rutinalist.get(i).Ejercicios.ubicacion_imagen+".gif";
                            ejerciciosArrayList.add(new com.example.superfit.Clases.Ejercicios(
                                    Url_Imagen,
                                    rutinalist.get(i).Ejercicios.Ejercicio,
                                    rutinalist.get(i).Ejercicios.Descripcion,
                                    String.valueOf(rutinalist.get(i).Repeticiones),
                                    String.valueOf(rutinalist.get(i).Series)));
                        }
                        Exercicies(ejerciciosArrayList);
                    }
                }
                catch (Exception ex){
                    Toast.makeText(Detallejercicio.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DetallerutinaModel>> call, Throwable t) {
                Toast.makeText(Detallejercicio.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde",Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void Exercicies(ArrayList<Ejercicios> Lista){
        adptador = new AdaptadorEjercicios(this,Lista);
        lvitems.setAdapter(adptador);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            int dia = getIntent().getExtras().getInt("IdDia");
            Bundle extras = new Bundle();
            extras.putInt("IdDia",dia);
            Intent intent = new Intent(Detallejercicio.this,Sets.class);
            intent.putExtras(extras);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}