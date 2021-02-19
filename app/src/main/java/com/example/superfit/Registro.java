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
        Contraseña=(EditText)findViewById(R.id.Contraseñatxt);
        Aceptar=(Button)findViewById(R.id.RegistrerseBtn2);

        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n=Nombre.getText().toString(),ap=Ap.getText().toString(),am=Am.getText().toString(),apo=Apodo.getText().toString(),
                        tel=Telefono.getText().toString(),ed=Edad.getText().toString(),em=Email.getText().toString(),pass=Contraseña.getText().toString();
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
        // Job http://192.168.56.1:8081/
        // Home http://192.168.100.11:8081/
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://192.168.56.1:8081/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ClienteApi clienteApi = retrofit.create(ClienteApi.class);
        Call<ClientesModel> call = clienteApi.RegistrarCliente(newcliente);
        call.enqueue(new Callback<ClientesModel>() {
            @Override
            public void onResponse(Call<ClientesModel> call, Response<ClientesModel> response) {
                try {
                        if(response.isSuccessful()){
                            Toast.makeText(Registro.this,"Registro Correcto",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Registro.this,"No se realizo correctamente la conexion",Toast.LENGTH_SHORT).show();
                        }

                }
                catch (Exception ex){

                }
            }
            @Override
            public void onFailure(Call<ClientesModel> call, Throwable t) {
                Toast.makeText(Registro.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde \r\n Error:"+t.getCause().toString(),Toast.LENGTH_SHORT).show();
            }

        });
    }
}