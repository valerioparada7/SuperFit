package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.superfit.interfaces.DetallerutinaApi;
import com.example.superfit.interfaces.DiasApi;
import com.example.superfit.models.DetallerutinaModel;
import com.example.superfit.models.DiasModel;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_1;

public class Sets extends AppCompatActivity {
    ArrayList<String> setsapis = new ArrayList<String>();
    ListView listsets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);
        listsets = (ListView) findViewById(R.id.SetsListview);
        GetSets();
    }
    public void GetSets(){
        // Job http://192.168.56.1:8081/
        // Home http://192.168.100.11:8081/
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://192.168.56.1:8081/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        DetallerutinaApi detallerutinaApi = retrofit.create(DetallerutinaApi.class);
        Call<List<DetallerutinaModel>> call = detallerutinaApi.GetDetalleRutinaSets(1,3,2);
        call.enqueue(new Callback<List<DetallerutinaModel>>() {
            @Override
            public void onResponse(Call<List<DetallerutinaModel>> call, Response<List<DetallerutinaModel>> response) {
                try {
                    if(response.isSuccessful()){
                        List<DetallerutinaModel> diaslist = response.body();
                        for (int i=0;i<diaslist.size();i++){
                            setsapis.add(diaslist.get(i).TipoSet);
                        }
                        Sets(setsapis);
                    }
                }
                catch (Exception ex){
                    Log.w("",ex.getMessage());
                    Toast.makeText(Sets.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DetallerutinaModel>> call, Throwable t) {
                Toast.makeText(Sets.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Sets(ArrayList<String> Lista){
        ArrayAdapter adapter = new ArrayAdapter(this, simple_list_item_1,Lista);
        listsets.setAdapter(adapter);
    }
}