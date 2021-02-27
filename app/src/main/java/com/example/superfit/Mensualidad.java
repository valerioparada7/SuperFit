package com.example.superfit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.superfit.interfaces.CatalogoApi;
import com.example.superfit.interfaces.ClienteApi;
import com.example.superfit.models.AlertasModel;
import com.example.superfit.models.ClientesModel;
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
                Intent intent = new Intent(Mensualidad.this,Antropometria.class);
                startActivity(intent);
/*
                int IdTipoRutina =listtiporutinas.getSelectedItemPosition();
                int IdTipoEntrenamiento =listtipoentrenamiento.getSelectedItemPosition();
                int IdCliente = getIntent().getExtras().getInt("IdCliente");
                MensualidadModel mensualidadModel= new MensualidadModel();
                mensualidadModel.Cliente=new ClientesModel();
                mensualidadModel.TipoEntrenamiento=new TipoentrenamientoModel();
                mensualidadModel.Tiporutina=new TiporutinaModel();
                mensualidadModel.Cliente.Id_Cliente=IdCliente;
                mensualidadModel.TipoEntrenamiento.Id_TipoEntrenamiento=IdTipoEntrenamiento+1;
                mensualidadModel.Tiporutina.Id_tiporutina=IdTipoRutina+1;
                RegistrarMensualidad(mensualidadModel);*/
            }
        });
    }

    public void RegistrarMensualidad(MensualidadModel newmensualidad){
        // Job http://192.168.56.1:8081/
        // Home http://192.168.100.11:8081/
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
                            Intent intent = new Intent(Mensualidad.this,Antropometria.class);
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
        // web superfit.somee.com

        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://superfit.somee.com/")
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
                                tipoentrenamientoapi.add(i+1+".- "+tipoentrenamientolist.get(i).Tipo_entrenamiento);
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
        //web superfit.somee.com
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://superfit.somee.com/")
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
                                tiporutinasapi.add(i+1+".- "+tiporutinaslist.get(i).Descripcion);
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
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_edittext_sexo,Lista);
        listtiporutinas.setAdapter(adapter);
    }

    public void TipoEntrenamientos(ArrayList<String> Lista){
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_edittext_sexo,Lista);
        listtipoentrenamiento.setAdapter(adapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir? se perdera el progreso")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("UsuarioCliente","");
                            editor.putString("ContraseñaCliente","");
                            editor.putInt("EdadCliente",0);
                            editor.commit();
                            Intent intent = new Intent(Mensualidad.this,MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }

}