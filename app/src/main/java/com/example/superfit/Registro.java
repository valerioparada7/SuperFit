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
import android.widget.EditText;
import android.widget.Spinner;
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

    EditText Nombre,Ap,Am,Apodo,Edad,Telefono,Email,Contraseña;
    Button Aceptar;
    Spinner listsexo;
    String[] Sexo=  {"Masculino","Femenino"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

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
        Aceptar=(Button)findViewById(R.id.RegistrerseBtn2);

        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           Intent intent = new Intent(Registro.this,Cuestionario.class);
            startActivity(intent);
/*
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

 */
            }
        });
    }

    public void Registrar(ClientesModel newcliente){
        // Job http://192.168.56.1:8081/    http://192.168.56.1:8081/
        // Home http://192.168.100.11:8081/
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