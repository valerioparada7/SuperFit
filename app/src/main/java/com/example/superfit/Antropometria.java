package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.superfit.interfaces.ClienteApi;
import com.example.superfit.models.AlertasModel;
import com.example.superfit.models.AntropometriaModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Antropometria extends AppCompatActivity {
    EditText Peso, Altura,Brazoderechorelajado,Brazoderechofuerza,Brazoizquierdorelajado,
            Brazoizquierdofuerza,Cintura,Cadera,Piernaizquierda,Piernaderecho,
            Pantorrilladerecha,Pantorrillaizquierda;
    TextView IMC,Msj;
    Button Aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antropometria);
        Peso					=(EditText)findViewById(R.id.PesoTxt);
        Altura					=(EditText)findViewById(R.id.AlturaTxt);
        IMC						=(TextView)findViewById(R.id.imcTxt);
        Brazoderechorelajado	=(EditText)findViewById(R.id.BrazoderechorelajadoTxt);
        Brazoderechofuerza		=(EditText)findViewById(R.id.BrazoderechofuerzaTxt);
        Brazoizquierdorelajado	=(EditText)findViewById(R.id.BrazoizquierdorelajadoTxt);
        Brazoizquierdofuerza	=(EditText)findViewById(R.id.BrazoizquierdofuerzaTxt);
        Cintura					=(EditText)findViewById(R.id.CinturaTxt);
        Cadera					=(EditText)findViewById(R.id.CaderaTxt);
        Piernaizquierda			=(EditText)findViewById(R.id.PiernaizquierdaTxt);
        Piernaderecho			=(EditText)findViewById(R.id.PiernaderechoTxt);
        Pantorrilladerecha		=(EditText)findViewById(R.id.PantorrilladerechaTxt);
        Pantorrillaizquierda	=(EditText)findViewById(R.id.PantorrillaizquierdaTxt);


        Aceptar=(Button)findViewById(R.id.MedidasaceptarBtn);

        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Idmensualidad = getIntent().getExtras().getInt("Idmensualidad");
                String peso = Peso.getText().toString();
                String altura = Altura.getText().toString();
                String iMC=IMC.getText().toString();
                String brazoderechorelajado		=Brazoderechorelajado.getText().toString();
                String brazoderechofuerza		=Brazoderechofuerza.getText().toString();
                String brazoizquierdorelajado	=Brazoizquierdorelajado.getText().toString();
                String brazoizquierdofuerza		=Brazoizquierdofuerza.getText().toString();
                String cintura					=Cintura.getText().toString();
                String cadera					=Cadera.getText().toString();
                String piernaizquierda			=Piernaizquierda.getText().toString();
                String piernaderecho			=Piernaderecho.getText().toString();
                String pantorrilladerecha		=Pantorrilladerecha.getText().toString();
                String pantorrillaizquierda		=Pantorrillaizquierda.getText().toString();

                AntropometriaModel antropometriaModel= new AntropometriaModel();
                antropometriaModel.Mensualidad.Id_mensualidad=Idmensualidad;
                antropometriaModel.Peso						=Float.parseFloat(peso);
                antropometriaModel.Altura					=Integer.parseInt(altura);
                antropometriaModel.IMC						=Float.parseFloat(iMC);
                antropometriaModel.Brazoderechorelajado		=Float.parseFloat(brazoderechorelajado);
                antropometriaModel.Brazoderechofuerza		=Float.parseFloat(brazoderechofuerza);
                antropometriaModel.Brazoizquierdorelajado	=Float.parseFloat(brazoizquierdorelajado);
                antropometriaModel.Brazoizquierdofuerza		=Float.parseFloat(brazoizquierdofuerza);
                antropometriaModel.Cintura					=Float.parseFloat(cintura);
                antropometriaModel.Cadera					=Float.parseFloat(cadera);
                antropometriaModel.Piernaizquierda			=Float.parseFloat(piernaizquierda);
                antropometriaModel.Piernaderecho			=Float.parseFloat(piernaderecho);
                antropometriaModel.Pantorrilladerecha		=Float.parseFloat(pantorrilladerecha);
                antropometriaModel.Pantorrillaizquierda		=Float.parseFloat(pantorrillaizquierda);
                RegistrarAntropometria(antropometriaModel);

            }
        });

    }

    public void RegistrarAntropometria(AntropometriaModel antropometriaModel){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://192.168.56.1:8081/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ClienteApi clienteApi = retrofit.create(ClienteApi.class);
        Call<AlertasModel> call = clienteApi.RegistrarAntropometria(antropometriaModel);
        call.enqueue(new Callback<AlertasModel>() {
            @Override
            public void onResponse(Call<AlertasModel> call, Response<AlertasModel> response) {
                try {
                    if(response.isSuccessful()){
                        AlertasModel result = response.body();
                        if(result.Result==true){
                            Toast.makeText(Antropometria.this,result.Mensaje,Toast.LENGTH_SHORT).show();
                            SesionOpen();
                        }
                        else {
                            Toast.makeText(Antropometria.this,result.Mensaje,Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(Antropometria.this,"No se realizo la conexion "+response.message(),Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex){
                    Toast.makeText(Antropometria.this,"Ocurrio un error: \r\n" +ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AlertasModel> call, Throwable t) {
                Toast.makeText(Antropometria.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde \r\n Error:"+t.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void SesionOpen(){
        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        String usuario=preferences.getString("UsuarioCliente","No asignado");
        String contraseña=preferences.getString("ContraseñaCliente","No asignado");

    }


}