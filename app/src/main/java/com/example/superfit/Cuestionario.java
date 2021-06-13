package com.example.superfit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.superfit.interfaces.ClienteApi;
import com.example.superfit.models.AlertasModel;
import com.example.superfit.models.ClientesModel;
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
    TextView TipoejercicioL,TiempodedicadoL,HorarioentrenoL;
    LinearLayout liner;
    Button Aceptar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionario);

        liner = (LinearLayout)findViewById(R.id.LinerCuestionario);
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

        //TextView
        TipoejercicioL =(TextView)findViewById(R.id.TipoejercicioLabel);
        TiempodedicadoL =(TextView)findViewById(R.id.labelTiempo_dedicado);
        HorarioentrenoL =(TextView)findViewById(R.id.labelHorario_entreno);

        //Obtnemos los datos
        GetCuestionario();


        //Butons
        //Aceptar =(Button)findViewById(R.id.RegistrarCuestionarioBtn);
/*
        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(Cuestionario.this,Mensualidad.class);
               startActivity(intent);

                int IdCliente = getIntent().getExtras().getInt("IdCliente");
                String fuma = Veces_semana_fuma.getText().toString();
                String alcohol = Veces_semana_alcohol.getText().toString();
                if(fuma.isEmpty()){
                    fuma="0";
                }
                if(alcohol.isEmpty()){
                    alcohol="0";
                }
                CuestionarioModel cuestionario = new CuestionarioModel();
                cuestionario.Cliente=new ClientesModel();
                cuestionario.Cliente.Id_Cliente=IdCliente;
                cuestionario.Padece_enfermedad = Padece_enfermedad.isChecked();
                cuestionario.Medicamento_prescrito_medico = Medicamento_prescrito_medico.getText().toString();
                cuestionario.lesiones = lesiones.isChecked();
                cuestionario.Alguna_recomendacion_lesiones = Alguna_recomendacion_lesiones.getText().toString();
                cuestionario.Fuma = Fuma.isChecked();
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
                RegistroCuestionario(cuestionario);
            }
        });*/

        Padece_enfermedad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Padece_enfermedad.isChecked()==true){
                    Medicamento_prescrito_medico.setVisibility(View.VISIBLE);
                }
                else{
                    Medicamento_prescrito_medico.setVisibility(View.INVISIBLE);
                    Medicamento_prescrito_medico.setText("");
                }
            }
        });

        lesiones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lesiones.isChecked()==true){
                    Alguna_recomendacion_lesiones.setVisibility(View.VISIBLE);
                }
                else{
                    Alguna_recomendacion_lesiones.setVisibility(View.INVISIBLE);
                    Alguna_recomendacion_lesiones.setText("");
                }
            }
        });

        Fuma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Fuma.isChecked()==true){
                    Veces_semana_fuma.setVisibility(View.VISIBLE);
                }
                else{
                    Veces_semana_fuma.setVisibility(View.INVISIBLE);
                    Veces_semana_fuma.setText("");
                }
            }
        });

        Alcohol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Alcohol.isChecked()==true){
                    Veces_semana_alcohol.setVisibility(View.VISIBLE);
                }
                else{
                    Veces_semana_alcohol.setVisibility(View.INVISIBLE);
                    Veces_semana_alcohol.setText("");
                }
            }
        });

        Actividad_fisica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actividad();
            }
        });

    }

    public void GetCuestionario(){
        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        //Cuestionario
        Boolean Padece_enfermedadU = preferences.getBoolean("Padece_enfermedad",false);
        String Medicamento_prescrito_medicoU = preferences.getString("Medicamento_prescrito_medico","" );
        Boolean lesionesU =preferences.getBoolean("lesiones",false);
        String Alguna_recomendacion_lesionesU = preferences.getString ("Alguna_recomendacion_lesiones","");
        Boolean FumaU =preferences.getBoolean("Fuma",false );
        int  Veces_semana_fumaU =preferences.getInt("Veces_semana_fuma",0 );
        Boolean AlcoholU = preferences.getBoolean("Alcohol",false );
        int Veces_semana_alcoholU = preferences.getInt("Veces_semana_alcohol",0 );
        Boolean Actividad_fisicaU = preferences.getBoolean("Actividad_fisica",false);

        String Tipo_ejerciciosU=preferences.getString("Tipo_ejercicios","" );
        String Tiempo_dedicadoU=preferences.getString("Tiempo_dedicado","" );
        String Horario_entrenoU=preferences.getString("Horario_entreno","" );
        String MetasObjetivosU=preferences.getString("MetasObjetivos", "");
        String CompromisosU=preferences.getString("Compromisos","" );
        String ComentariosU=preferences.getString("Comentarios","" );

        //Mostrar datos
        Padece_enfermedad.setChecked(Padece_enfermedadU);
        lesiones.setChecked(lesionesU);
        Fuma.setChecked(FumaU);
        Alcohol.setChecked(AlcoholU);
        Actividad_fisica.setChecked(Actividad_fisicaU);
        //Validamos los datos de actividad fisica
        Actividad();
        //editetexp
        Medicamento_prescrito_medico.setText(Medicamento_prescrito_medicoU);
        Alguna_recomendacion_lesiones.setText(Alguna_recomendacion_lesionesU);
        Veces_semana_fuma.setText(String.valueOf(Veces_semana_fumaU));
        Veces_semana_alcohol.setText(String.valueOf(Veces_semana_alcoholU));
        Tipo_ejercicios.setText(Tipo_ejerciciosU);
        Tiempo_dedicado.setText(Tiempo_dedicadoU);
        Horario_entreno.setText(Horario_entrenoU);
        MetasObjetivos.setText(MetasObjetivosU);
        Compromisos.setText(CompromisosU);
        Comentarios.setText(ComentariosU);
    }

    public void RegistroCuestionario(CuestionarioModel newcuestionario){
        // Job http://192.168.56.1:8081/
        // Home http://192.168.100.11:8081/
        // web superfit.somee.com
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
                            Toast.makeText(Cuestionario.this,"Se actualizaron tus datos con exito",Toast.LENGTH_SHORT).show();
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

    public void Actividad(){
        if(Actividad_fisica.isChecked()==true){
            liner.setVisibility(View.VISIBLE);
            liner.getLayoutParams().height =ViewGroup.LayoutParams.WRAP_CONTENT;
            liner.requestLayout();
        }
        else{
            liner.setVisibility(View.INVISIBLE);
            liner.getLayoutParams().height =0;
            liner.requestLayout();
            Tipo_ejercicios.setText("");
            Tiempo_dedicado.setText("");
            Horario_entreno.setText("");
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            Intent intent = new Intent(Cuestionario.this,Perfil.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}