package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.superfit.interfaces.ClienteApi;
import com.example.superfit.models.AlertasModel;
import com.example.superfit.models.CuestionarioModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cuestionario extends AppCompatActivity {
    CheckBox Padece_enfermedad,lesiones,Fuma,Alcohol,Actividad_fisica;
    EditText Medicamento_prescrito_medico,Alguna_recomendacion_lesiones,Veces_semana_fuma,
            Veces_semana_alcohol,Tipo_ejercicios,Tiempo_dedicado,Horario_entreno,MetasObjetivos,
            Compromisos,Comentarios;
    Button Aceptar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionario);
        //Checkboxs
        Padece_enfermedad =(CheckBox)findViewById(R.id.Padece_enfermedadChk);
        lesiones =(CheckBox)findViewById(R.id.lesionesChk);
        Fuma =(CheckBox)findViewById(R.id.FumaChk);
        Alcohol =(CheckBox)findViewById(R.id.AlcoholChk);
        Actividad_fisica =(CheckBox)findViewById(R.id.Actividad_fisicaChk);

        //editetexp
        Medicamento_prescrito_medico =(EditText) findViewById(R.id.Medicamento_prescrito_medicoTxt);
        Alguna_recomendacion_lesiones =(EditText) findViewById(R.id.Alguna_recomendacion_lesionesTxt);
        Veces_semana_fuma =(EditText) findViewById(R.id.Veces_semana_fumaTxt);
        Veces_semana_alcohol =(EditText) findViewById(R.id.Veces_semana_alcoholTxt);
        Tipo_ejercicios =(EditText) findViewById(R.id.Tipo_ejerciciosTxt);
        Tiempo_dedicado =(EditText) findViewById(R.id.Tiempo_dedicadoTxt);
        Horario_entreno =(EditText) findViewById(R.id.Horario_entrenoTxt);
        MetasObjetivos =(EditText) findViewById(R.id.MetasObjetivosTxt);
        Compromisos =(EditText) findViewById(R.id.CompromisosTxt);
        Comentarios =(EditText) findViewById(R.id.ComentariosTxt);

        //Butons
        Aceptar =(Button)findViewById(R.id.RegistrarCuestionarioBtn);

        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Cuestionario.this,Mensualidad.class);
                startActivity(intent);
                /*
                String fuma = Veces_semana_fuma.getText().toString();
                String alcohol = Veces_semana_alcohol.getText().toString();
                CuestionarioModel cuestionario = new CuestionarioModel();
                cuestionario.Padece_enfermedad = Padece_enfermedad.isChecked();
                cuestionario.Medicamento_prescrito_medico = Medicamento_prescrito_medico.getText().toString();
                cuestionario.lesiones = lesiones.isChecked();;
                cuestionario.Alguna_recomendacion_lesiones = Alguna_recomendacion_lesiones.getText().toString();
                cuestionario.Fuma = Fuma.isChecked();;
                cuestionario.Veces_semana_fuma = Integer.parseInt(fuma);
                cuestionario.Alcohol = Alcohol.isChecked();
                cuestionario.Veces_semana_alcohol =  Integer.parseInt(alcohol);
                cuestionario.Actividad_fisica = Actividad_fisica.isChecked();
                cuestionario.Tipo_ejercicios = Tipo_ejercicios.getText().toString();
                cuestionario.Tiempo_dedicado = Tiempo_dedicado.getText().toString();
                cuestionario.Horario_entreno = Horario_entreno.getText().toString();
                cuestionario.MetasObjetivos = MetasObjetivos.getText().toString();
                cuestionario.Compromisos = Compromisos.getText().toString();
                cuestionario.Comentarios = Comentarios.getText().toString();
                RegistroCuestionario(cuestionario);*/
            }
        });


    }

    public void RegistroCuestionario(CuestionarioModel newcuestionario){
        // Job http://192.168.56.1:8081/    http://192.168.56.1:8081/
        // Home http://192.168.100.11:8081/
        int IdCliente = getIntent().getExtras().getInt("IdCliente");
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://192.168.56.1:8081/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ClienteApi clienteApi = retrofit.create(ClienteApi.class);
        Call<AlertasModel> call = clienteApi.RegistroCuestionario(newcuestionario);
        call.enqueue(new Callback<AlertasModel>() {
            @Override
            public void onResponse(Call<AlertasModel> call, Response<AlertasModel> response) {
                try {
                    if(response.isSuccessful()){
                        AlertasModel result = response.body();
                        if(result.Result==true){
                            Toast.makeText(Cuestionario.this,result.Mensaje,Toast.LENGTH_SHORT).show();
                            Bundle extras = new Bundle();
                            extras.putInt("IdCliente",IdCliente);
                            Intent intent = new Intent(Cuestionario.this,Cuestionario.class);
                            intent.putExtras(extras);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(Cuestionario.this,result.Mensaje,Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(Cuestionario.this,"No se realizo la conexion "+response.message(),Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex){
                    Toast.makeText(Cuestionario.this,"Ocurrio un error: \r\n" +ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AlertasModel> call, Throwable t) {
                Toast.makeText(Cuestionario.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde \r\n Error:"+t.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}