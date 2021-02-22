package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.superfit.interfaces.CatalogoApi;
import com.example.superfit.interfaces.ClienteApi;
import com.example.superfit.models.AlertasModel;
import com.example.superfit.models.MensualidadModel;
import com.example.superfit.models.TipoentrenamientoModel;
import com.example.superfit.models.TiporutinaModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.layout.simple_list_item_1;

public class Mensualidad extends AppCompatActivity {
    ArrayList<String> tiporutinasapi = new ArrayList<String>();
    ArrayList<String> tipoentrenamientoapi = new ArrayList<String>();
    Spinner listtiporutinas,listtipoentrenamiento;
    Button Aceptar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensualidad);
        listtiporutinas = (Spinner) findViewById(R.id.TiporutinaList);
        listtipoentrenamiento =(Spinner)findViewById(R.id.Tipoentrenamientolist);
        Aceptar=(Button) findViewById(R.id.MensualidadAceptar);
        GetTipoRutinas();
        GetTipoEntrenamiento();

        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int IdTipoRutina =listtiporutinas.getSelectedItemPosition();
                int IdTipoEntrenamiento =listtipoentrenamiento.getSelectedItemPosition();

                Toast.makeText(Mensualidad.this,IdTipoRutina+" "+IdTipoEntrenamiento,Toast.LENGTH_LONG).show();

                MensualidadModel mensualidadModel= new MensualidadModel();
                int IdCliente = getIntent().getExtras().getInt("IdCliente");
                mensualidadModel.Cliente.Id_Cliente=IdCliente;
                mensualidadModel.TipoEntrenamiento.Id_TipoEntrenamiento=IdTipoEntrenamiento+1;
                mensualidadModel.Tiporutina.Id_tiporutina=IdTipoRutina+1;
                RegistrarMensualidad(mensualidadModel);
            }
        });
    }

    public void RegistrarMensualidad(MensualidadModel newmensualidad){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://192.168.56.1:8081/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ClienteApi clienteApi = retrofit.create(ClienteApi.class);
        Call<AlertasModel> call = clienteApi.RegistrarMensualidad(newmensualidad);
        call.enqueue(new Callback<AlertasModel>() {
            @Override
            public void onResponse(Call<AlertasModel> call, Response<AlertasModel> response) {
                try {
                    if(response.isSuccessful()){
                        AlertasModel result = response.body();
                        if(result.Result==true){
                            Toast.makeText(Mensualidad.this,result.Mensaje,Toast.LENGTH_SHORT).show();
                            Bundle extras = new Bundle();
                            extras.putInt("Idmensualidad",result.Id);
                            Intent intent = new Intent(Mensualidad.this,Cuestionario.class);
                            intent.putExtras(extras);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(Mensualidad.this,result.Mensaje,Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(Mensualidad.this,"No se realizo la conexion "+response.message(),Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex){
                    Toast.makeText(Mensualidad.this,"Ocurrio un error: \r\n" +ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<AlertasModel> call, Throwable t) {
                Toast.makeText(Mensualidad.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde \r\n Error:"+t.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GetTipoEntrenamiento() {
        // Job http://192.168.56.1:8081/
        // Home http://:192.168.100.118081/

        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://192.168.56.1:8081/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        CatalogoApi catalogoapi = retrofit.create(CatalogoApi.class);
        Call<List<TipoentrenamientoModel>> call = catalogoapi.GetTypeTraining();
        call.enqueue(new Callback<List<TipoentrenamientoModel>>() {
            @Override
            public void onResponse(Call<List<TipoentrenamientoModel>> call, Response<List<TipoentrenamientoModel>> response) {
                try {
                    if(response.isSuccessful()){
                        List<TipoentrenamientoModel> tipoentrenamientolist = response.body();
                        if(tipoentrenamientolist.size()>0){
                            for (int i=0;i<tipoentrenamientolist.size();i++){
                                tipoentrenamientoapi.add(tipoentrenamientolist.get(i).Tipo_entrenamiento);
                            }
                            TipoEntrenamientos(tipoentrenamientoapi);
                        }
                        else{
                            tiporutinasapi.add("No hay rutinas");
                            TipoEntrenamientos(tipoentrenamientoapi);
                        }
                    }
                }
                catch (Exception ex){
                    Log.w("",ex.getMessage());
                    Toast.makeText(Mensualidad.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TipoentrenamientoModel>> call, Throwable t) {
                Toast.makeText(Mensualidad.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GetTipoRutinas() {
        // Job http://192.168.56.1:8081/
        // Home http://:192.168.100.118081/

        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://192.168.56.1:8081/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        CatalogoApi catalogoapi = retrofit.create(CatalogoApi.class);
        Call<List<TiporutinaModel>> call = catalogoapi.GetTypeRutines();
        call.enqueue(new Callback<List<TiporutinaModel>>() {
            @Override
            public void onResponse(Call<List<TiporutinaModel>> call, Response<List<TiporutinaModel>> response) {
                try {
                    if(response.isSuccessful()){
                        List<TiporutinaModel> tiporutinaslist = response.body();
                        if(tiporutinaslist.size()>0){
                            for (int i=0;i<tiporutinaslist.size();i++){
                                tiporutinasapi.add(tiporutinaslist.get(i).Descripcion);
                            }
                            TipoRutinas(tiporutinasapi);
                        }
                        else{
                            tiporutinasapi.add("No hay rutinas");
                            TipoRutinas(tiporutinasapi);
                        }
                    }
                }
                catch (Exception ex){
                    Log.w("",ex.getMessage());
                    Toast.makeText(Mensualidad.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TiporutinaModel>> call, Throwable t) {
                Toast.makeText(Mensualidad.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void TipoRutinas(ArrayList<String> Lista){
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,Lista);
        listtiporutinas.setAdapter(adapter);
    }

    public void TipoEntrenamientos(ArrayList<String> Lista){
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,Lista);
        listtipoentrenamiento.setAdapter(adapter);
    }

}