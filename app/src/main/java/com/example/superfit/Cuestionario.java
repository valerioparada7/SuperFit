package com.example.superfit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
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
import com.example.superfit.models.MensualidadModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cuestionario extends AppCompatActivity {
    // Job http://192.168.56.1:8081/
    // Home http://192.168.100.11:8081/
    // Pagina http://superfit.somee.com/
    // Pagina nueva http://valerioparada7-001-site1.etempurl.com/
    //Pagina Actual nueva https://www.bsite.net/valerioparada/
    String PaginaWeb ="https://www.bsite.net/valerioparada/";

    CheckBox Padece_enfermedad,lesiones,Fuma,Alcohol,Actividad_fisica;
    EditText Medicamento_prescrito_medico,Alguna_recomendacion_lesiones,Veces_semana_fuma,
            Veces_semana_alcohol,Tipo_ejercicios,Tiempo_dedicado,Horario_entreno,MetasObjetivos,
            Compromisos,Comentarios;
    TextView TipoejercicioL,TiempodedicadoL,HorarioentrenoL;
    LinearLayout liner;
    Button Aceptar;
    public int Idcuestionario=0;
    CuestionarioModel cuestionario = new CuestionarioModel();
    final Cargando cargando = new Cargando(Cuestionario.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionario);
        //Obtnemos los datos
        cargando.cargardialogo();
        GetCuestionario();
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
        //Butons
        Aceptar =(Button)findViewById(R.id.GuardarCuestionarioBtn);

        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatosCuestioario();
                if(cuestionario!=null){
                    RegistroCuestionario();
                }

            }
        });

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
        int Idcliente =preferences.getInt("Idcliente",0);
        Retrofit retrofit=new Retrofit.Builder().baseUrl(PaginaWeb)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ClienteApi clienteApi = retrofit.create(ClienteApi.class);
        Call<CuestionarioModel> call = clienteApi.GetCuestionario(Idcliente);
        call.enqueue(new Callback<CuestionarioModel>() {
            @Override
            public void onResponse(Call<CuestionarioModel> call, Response<CuestionarioModel> response) {
                try {
                    if(response.isSuccessful()){
                        CuestionarioModel C = response.body();
                        MostrarCuestionario(C);
                    }
                    else{
                        Toast.makeText(Cuestionario.this,"No se realizo correctamente la conexion",Toast.LENGTH_SHORT).show();
                        cargando.ocultar();
                    }

                }
                catch (Exception ex){
                    Toast.makeText(Cuestionario.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                    cargando.ocultar();
                }
            }

            @Override
            public void onFailure(Call<CuestionarioModel> call, Throwable t) {
                cargando.ocultar();
                Toast.makeText(Cuestionario.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde \r\n Error:"+t.getCause().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void MostrarCuestionario(CuestionarioModel cuestionario){
        //Mostrar datos
        Idcuestionario = cuestionario.Id_cuestionario;
        Padece_enfermedad.setChecked(cuestionario.Padece_enfermedad);
        if(Padece_enfermedad.isChecked()==true){
            Medicamento_prescrito_medico.setVisibility(View.VISIBLE);
        }
        lesiones.setChecked(cuestionario.Lesiones);
        if(lesiones.isChecked()==true){
            Alguna_recomendacion_lesiones.setVisibility(View.VISIBLE);
        }
        Fuma.setChecked(cuestionario.Fuma);
        if(Fuma.isChecked()==true){
            Veces_semana_fuma.setVisibility(View.VISIBLE);
        }
        Alcohol.setChecked(cuestionario.Alcohol);
        if(Alcohol.isChecked()==true){
            Veces_semana_alcohol.setVisibility(View.VISIBLE);
        }
        Actividad_fisica.setChecked(cuestionario.Actividad_fisica);
        //Validamos los datos de actividad fisica
        Actividad();

        //editetexp
        Medicamento_prescrito_medico.setText(cuestionario.Medicamento_prescrito_medico);
        Alguna_recomendacion_lesiones.setText(cuestionario.Alguna_recomendacion_lesiones);
        Veces_semana_fuma.setText(String.valueOf(cuestionario.Veces_semana_fuma));
        Veces_semana_alcohol.setText(String.valueOf(cuestionario.Veces_semana_alcohol));
        Tipo_ejercicios.setText(cuestionario.Tipo_ejercicios);
        Tiempo_dedicado.setText(cuestionario.Tiempo_dedicado);
        Horario_entreno.setText(cuestionario.Horario_entreno);
        MetasObjetivos.setText(cuestionario.MetasObjetivos);
        Compromisos.setText(cuestionario.Compromisos);
        Comentarios.setText(cuestionario.Comentarios);
        cargando.ocultar();
    }

    public void RegistroCuestionario(){
        cargando.cargardialogo();
        // Job http://192.168.56.1:8081/
        // Home http://192.168.100.11:8081/
        // web superfit.somee.com
        Retrofit retrofit=new Retrofit.Builder().baseUrl(PaginaWeb)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ClienteApi clienteApi = retrofit.create(ClienteApi.class);
        Call<AlertasModel> call = clienteApi.UpdateCuestionario(cuestionario);
        call.enqueue(new Callback<AlertasModel>() {
            @Override
            public void onResponse(Call<AlertasModel> call, Response<AlertasModel> response) {
                try {
                    if(response.isSuccessful()){
                        AlertasModel result = response.body();
                        if(result.Result==true){
                            cargando.ocultar();
                            Toast.makeText(Cuestionario.this,"Se actualizaron tus datos con exito",Toast.LENGTH_SHORT).show();
                            GetCuestionario();
                        }
                        else {
                            cargando.ocultar();
                            Toast.makeText(Cuestionario.this,result.Mensaje,Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        cargando.ocultar();
                        Toast.makeText(Cuestionario.this,"No se realizo la conexion "+response.message(),Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex){
                    cargando.ocultar();
                    Toast.makeText(Cuestionario.this,"Ocurrio un error: \r\n" +ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AlertasModel> call, Throwable t) {
                cargando.ocultar();
                Toast.makeText(Cuestionario.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde \r\n Error:"+t.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void DatosCuestioario(){
        String fuma = Veces_semana_fuma.getText().toString();
        String alcohol = Veces_semana_alcohol.getText().toString();
        if(fuma.isEmpty()){
            fuma="0";
        }
        if(alcohol.isEmpty()){
            alcohol="0";
        }
        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        int Idcliente =preferences.getInt("Idcliente",0);
        cuestionario.Cliente=new ClientesModel();
        cuestionario.Cliente.Id_cliente=Idcliente;
        cuestionario.Id_cuestionario =Idcuestionario;
        cuestionario.Padece_enfermedad = Padece_enfermedad.isChecked();
        cuestionario.Medicamento_prescrito_medico = Medicamento_prescrito_medico.getText().toString();
        cuestionario.Lesiones = lesiones.isChecked();
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