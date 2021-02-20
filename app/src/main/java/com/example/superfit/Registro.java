package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.superfit.interfaces.ClienteApi;
import com.example.superfit.models.AlertasModel;
import com.example.superfit.models.ClientesModel;
import com.example.superfit.models.MensualidadModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registro extends AppCompatActivity {

    EditText Nombre,Ap,Am,Apodo,Edad,Telefono,Email,Contraseña;
    Button Aceptar;
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
        Aceptar=(Button)findViewById(R.id.RegistrerseBtn2);

        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n=Nombre.getText().toString();
                String  ap=Ap.getText().toString();
                String  am=Am.getText().toString();
                String  apo=Apodo.getText().toString();
                String  tel=Telefono.getText().toString();
                String  ed=Edad.getText().toString();
                String  em=Email.getText().toString();
                String  pass=Contraseña.getText().toString();

                if(!n.isEmpty()&&!ap.isEmpty()&&!am.isEmpty()&&!apo.isEmpty()&&!tel.isEmpty()&&!ed.isEmpty()&&!em.isEmpty()&&!pass.isEmpty()){
                    ClientesModel cliente = new ClientesModel();
                    cliente.Nombres =n;
                    cliente.Apellido_Paterno=ap;
                    cliente.Apellido_Materno=am;
                    cliente.Apodo=apo;
                    cliente.Telefono= Double.parseDouble(tel);
                    cliente.Edad=Integer.parseInt(ed);
                    cliente.Correo_electronico=em;
                    cliente.Contraseña=pass;
                    Registrar(cliente);
                }
                else{
                    Toast.makeText(Registro.this, "Llene todos los campos requeridos", Toast.LENGTH_SHORT).show();
                }
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
                            Toast.makeText(Registro.this,result.Mensaje,Toast.LENGTH_SHORT).show();
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
}