package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.superfit.interfaces.ClienteApi;
import com.example.superfit.models.ClientesModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText Usuario,Contraseña;
    Button Aceptar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Usuario =findViewById(R.id.Usuariotxt);
        Contraseña=findViewById(R.id.Contraseñatxt);
        Aceptar =findViewById(R.id.Loginbtn);

        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuariostring =Usuario.getText().toString();
                String contraseñastring =Contraseña.getText().toString();
                if(!usuariostring.isEmpty()&&!contraseñastring.isEmpty()){
                    Iniciar(usuariostring,contraseñastring);
                }
                else{
                    Toast.makeText(MainActivity.this,"Ingrese los datos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void Iniciar(String Usuario,String Contraseña){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://192.168.100.11:8081/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ClienteApi clienteApi = retrofit.create(ClienteApi.class);
        Call<ClientesModel> call = clienteApi.Login(Usuario,Contraseña);
        call.enqueue(new Callback<ClientesModel>() {
            @Override
            public void onResponse(Call<ClientesModel> call, Response<ClientesModel> response) {
                try {
                    if(response.isSuccessful()){
                        ClientesModel c = response.body();
                        if(c.Validar==true){
                            Toast.makeText(MainActivity.this,"Bienvenido "+c.Nombres,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,Perfil.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(MainActivity.this,c.Nombres,Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                catch (Exception ex){
                    Toast.makeText(MainActivity.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ClientesModel> call, Throwable t) {
                Toast.makeText(MainActivity.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde",Toast.LENGTH_SHORT).show();
            }
        });
    }

}