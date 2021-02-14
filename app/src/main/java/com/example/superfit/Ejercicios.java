package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.superfit.interfaces.DiasApi;
import com.example.superfit.models.DiasModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.layout.simple_list_item_1;

public class Ejercicios extends AppCompatActivity {
    ArrayList<String> Daysapi = new ArrayList<String>();
    ListView Dias;
    String[] DiasApistring;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);
        Dias = (ListView) findViewById(R.id.DiasList);
        imagen =(ImageView) findViewById(R.id.Imagenlist);
        // GetDays();
        GetImagen();
    }

    public void GetImagen(){
        String Url_Imagen="http://192.168.56.1:8081/Imagenes/curlsfemoral.gif";
        Glide.with(getApplication()).load(Url_Imagen).into(imagen);
    }

    public void GetDays(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://192.168.56.1:8081/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        DiasApi diasApi = retrofit.create(DiasApi.class);
        Call<List<DiasModel>> call = diasApi.GetDias();
        call.enqueue(new Callback<List<DiasModel>>() {
            @Override
            public void onResponse(Call<List<DiasModel>> call, Response<List<DiasModel>> response) {
                try {
                    if(response.isSuccessful()){
                        List<DiasModel> diaslist = response.body();
                        for (int i=0;i<diaslist.size();i++){
                            Daysapi.add(diaslist.get(i).Dia);
                        }
                        Days(Daysapi);
                    }
                }
                catch (Exception ex){
                    Log.w("",ex.getMessage());
                    Toast.makeText(Ejercicios.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DiasModel>> call, Throwable t) {
                Toast.makeText(Ejercicios.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Days(ArrayList<String> Lista){
        ArrayAdapter adapter = new ArrayAdapter(this, simple_list_item_1,Lista);
        Dias.setAdapter(adapter);
    }
}