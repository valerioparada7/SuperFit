package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
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

        listsets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String valor = setsapis.get(position).toString();
                if(valor!="No hay rutina asignada"){
                    int dia = getIntent().getExtras().getInt("IdDia");
                    position=position+1;
                    Bundle extras = new Bundle();
                    extras.putInt("IdSet",position);
                    extras.putInt("IdDia",dia);
                    Intent intent = new Intent(Sets.this,Detallejercicio.class);
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            }
        });


    }

    public void GetSets(){
        // Job http://192.168.56.1:8081/
        // Home http://:192.168.100.118081/
        // web superfit.somee.com
        //put extras para obtener el dia que selecciona
        int dia = getIntent().getExtras().getInt("IdDia");

        //variables guardadas en la aplicacion para saber que tipo de mensualidads tienes y estatus
        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        int mensualidad=preferences.getInt("Id_mensualidad",0);
        int estatus=preferences.getInt("Id_estatus",0);


        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://superfit.somee.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        DetallerutinaApi detallerutinaApi = retrofit.create(DetallerutinaApi.class);
        Call<List<DetallerutinaModel>> call = detallerutinaApi.GetDetalleRutinaSets(mensualidad,estatus,dia);
        call.enqueue(new Callback<List<DetallerutinaModel>>() {
            @Override
            public void onResponse(Call<List<DetallerutinaModel>> call, Response<List<DetallerutinaModel>> response) {
                try {
                    if(response.isSuccessful()){
                        List<DetallerutinaModel> diaslist = response.body();
                        if(diaslist.size()>0){
                            for (int i=0;i<diaslist.size();i++){
                                setsapis.add( diaslist.get(i).TipoSet);
                            }
                            Sets(setsapis);
                        }
                        else{
                            setsapis.add("No hay rutina asignada");
                            Sets(setsapis);
                        }
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
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_edittext_sexo,Lista);
        listsets.setAdapter(adapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            Intent intent = new Intent(Sets.this,Rutinas.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}