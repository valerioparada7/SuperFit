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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.superfit.interfaces.ClienteApi;
import com.example.superfit.models.AlertasModel;
import com.example.superfit.models.ClientesModel;
import com.example.superfit.models.MensualidadModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registro extends AppCompatActivity {
    //Botones de navegacion y para guardar
    Button BtnAtras,BtnAdelante,BtnGuardar;
    int pagactual = 1;
    //Ventanas layout a ocupar
    LinearLayout linerpersonal,linercuestionario,linermensualidad,linermedidas;


    //Datos personales
    EditText Nombre,Ap,Am,Apodo,Edad,Telefono,Email,Contraseña;
    Spinner listsexo;
    String[] Sexo=  {"Masculino","Femenino"};

    //Cuestionario
    CheckBox Padece_enfermedad,lesiones,Fuma,Alcohol,Actividad_fisica;
    EditText Medicamento_prescrito_medico,Alguna_recomendacion_lesiones,Veces_semana_fuma,
            Veces_semana_alcohol,Tipo_ejercicios,Tiempo_dedicado,Horario_entreno,MetasObjetivos,
            Compromisos,Comentarios;
    TextView TipoejercicioL,TiempodedicadoL,HorarioentrenoL;
    LinearLayout liner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
//BOTONES NAVEGACION
        BtnAtras=(Button)findViewById(R.id.BotonAtras);
        BtnAdelante=(Button)findViewById(R.id.BotonSiguiente);
        BtnGuardar=(Button)findViewById(R.id.registrarcliente);
        //Diseños
        linerpersonal =(LinearLayout)findViewById(R.id.DatosPersonalesLayout);
        linercuestionario=(LinearLayout)findViewById(R.id.CuestionarioLayout);
        //linermensualidad=(LinearLayout)findViewById(R.id.LinerCuestionario);
        //linermedidas=(LinearLayout)findViewById(R.id.LinerCuestionario);

        //Datos personales
        Nombre=(EditText)findViewById(R.id.NombreTxt);
        Ap=(EditText)findViewById(R.id.ApellidoPTxt);
        Am=(EditText)findViewById(R.id.ApellidoMTxt);
        Apodo=(EditText)findViewById(R.id.ApodoTxt);
        Telefono=(EditText)findViewById(R.id.TelefonoTxt);
        Edad=(EditText)findViewById(R.id.EdadTxt);
        Email=(EditText)findViewById(R.id.EmailTxt);
        Contraseña=(EditText)findViewById(R.id.Contratxt);
        listsexo =(Spinner)findViewById(R.id.SexoList);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_edittext_sexo,Sexo);
        listsexo.setAdapter(adapter);


        //Cuestionario
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



        //Botones de navegacion
        BtnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int siguiente = pagactual;
                if (siguiente > 1) {
                    siguiente--;
                    if (siguiente == 1) {
                        BtnAtras.setVisibility(View.INVISIBLE);
                        linerpersonal.setVisibility(View.VISIBLE);
                        linerpersonal.getLayoutParams().height =ViewGroup.LayoutParams.WRAP_CONTENT;
                        linerpersonal.requestLayout();
                        linercuestionario.setVisibility(View.INVISIBLE);
                        linercuestionario.getLayoutParams().height =0;
                        linercuestionario.requestLayout();
                    }
                    else if (siguiente ==2)
                    {
                        linercuestionario.setVisibility(View.VISIBLE);
                        linercuestionario.getLayoutParams().height =ViewGroup.LayoutParams.WRAP_CONTENT;
                        linercuestionario.requestLayout();
                        linermensualidad.setVisibility(View.INVISIBLE);
                        linermensualidad.getLayoutParams().height =0;
                        linermensualidad.requestLayout();
                    }
                    else {
                        BtnAdelante.setVisibility(View.VISIBLE);
                        BtnGuardar.setVisibility(View.INVISIBLE);
                        linermensualidad.setVisibility(View.VISIBLE);
                        linermensualidad.getLayoutParams().height =ViewGroup.LayoutParams.WRAP_CONTENT;
                        linermensualidad.requestLayout();
                        linermedidas.setVisibility(View.INVISIBLE);
                        linermedidas.getLayoutParams().height =0;
                        linermedidas.requestLayout();
                    }
                    pagactual = siguiente;
                }
            }
        });

        BtnAdelante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int siguiente = pagactual;
                if (siguiente < 4) {
                    siguiente++;
                    if (siguiente == 2) {
                        boolean result = true;//ValidarCliente();
                        if (result == true) {
                            BtnAtras.setVisibility(View.INVISIBLE);
                            linercuestionario.setVisibility(View.VISIBLE);
                            linercuestionario.getLayoutParams().height =ViewGroup.LayoutParams.WRAP_CONTENT;
                            linercuestionario.requestLayout();
                            linerpersonal.setVisibility(View.INVISIBLE);
                            linerpersonal.getLayoutParams().height =0;
                            linerpersonal.requestLayout();
                        }
                        else {
                            siguiente--;
                        }
                    }
                    else if (siguiente == 3) {
                        linermensualidad.setVisibility(View.VISIBLE);
                        linermensualidad.getLayoutParams().height =ViewGroup.LayoutParams.WRAP_CONTENT;
                        linermensualidad.requestLayout();
                        linercuestionario.setVisibility(View.INVISIBLE);
                        linercuestionario.getLayoutParams().height =0;
                        linercuestionario.requestLayout();

                    }
                    else {
                        BtnAdelante.setVisibility(View.VISIBLE);
                        BtnGuardar.setVisibility(View.VISIBLE);
                        linermedidas.setVisibility(View.VISIBLE);
                        linermedidas.getLayoutParams().height =ViewGroup.LayoutParams.WRAP_CONTENT;
                        linermedidas.requestLayout();
                        linermensualidad.setVisibility(View.INVISIBLE);
                        linermensualidad.getLayoutParams().height =0;
                        linermensualidad.requestLayout();
                    }
                    pagactual = siguiente;
                }
            }
        });
/*
        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           Intent intent = new Intent(Registro.this,Cuestionario.class);
            startActivity(intent);

               String n=Nombre.getText().toString();
                String  ap=Ap.getText().toString();
                String  am=Am.getText().toString();
                String  apo=Apodo.getText().toString();
                String  tel=Telefono.getText().toString();
                String  ed=Edad.getText().toString();
                String  em=Email.getText().toString();
                String  pass=Contraseña.getText().toString();
                String sex=listsexo.getSelectedItem().toString();

                if(!n.isEmpty()&&!ap.isEmpty()&&!am.isEmpty()&&!apo.isEmpty()&&!tel.isEmpty()&&!ed.isEmpty()&&!em.isEmpty()&&!pass.isEmpty()){
                    ClientesModel cliente = new ClientesModel();
                    cliente.Nombres =n.toUpperCase();
                    cliente.Apellido_Paterno=ap.toUpperCase();
                    cliente.Apellido_Materno=am.toUpperCase();
                    cliente.Apodo=apo;
                    cliente.Telefono= Double.parseDouble(tel);
                    cliente.Edad=Integer.parseInt(ed);
                    cliente.Correo_electronico=em;
                    cliente.Contraseña=pass;
                    cliente.Sexo=sex;
                    Registrar(cliente);
                }
                else{
                    Toast.makeText(Registro.this, "Llene todos los campos requeridos", Toast.LENGTH_SHORT).show();
                }


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
    public void Actividad(){
        if(Actividad_fisica.isChecked()==true){
            liner.setVisibility(View.VISIBLE);
            liner.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
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
    public void Registrar(ClientesModel newcliente){
        // Job http://192.168.56.1:8081/
        // Home http://192.168.100.11:8081/
        // web superfit.somee.com
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://192.168.56.1:8081/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ClienteApi clienteApi = retrofit.create(ClienteApi.class);
        Call<AlertasModel> call = clienteApi.RegistrarCliente(newcliente);
        call.enqueue(new Callback<AlertasModel>() {
            @Override
            public void onResponse(Call<AlertasModel> call, Response<AlertasModel> response) {
                try {
                    if(response.isSuccessful()){
                        AlertasModel result = response.body();
                        if(result.Result==true){
                            SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("UsuarioCliente",newcliente.Correo_electronico);
                            editor.putString("ContraseñaCliente",newcliente.Contraseña);
                            editor.putString("SexoCliente", listsexo.getSelectedItem().toString());
                            editor.putInt("EdadCliente",newcliente.Edad);
                            editor.commit();
                            Toast.makeText(Registro.this,result.Mensaje,Toast.LENGTH_SHORT).show();
                            Bundle extras = new Bundle();
                            extras.putInt("IdCliente",result.Id);
                            Intent intent = new Intent(Registro.this,Cuestionario.class);
                            intent.putExtras(extras);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(Registro.this,result.Mensaje,Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(Registro.this,"No se realizo la conexion "+response.message(),Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex){
                    Toast.makeText(Registro.this,"Ocurrio un error: \r\n" +ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AlertasModel> call, Throwable t) {
                Toast.makeText(Registro.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde \r\n Error:"+t.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }


        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir? se perdera el progreso")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Registro.this,MainActivity.class);
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