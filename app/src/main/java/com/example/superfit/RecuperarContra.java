package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.superfit.interfaces.ClienteApi;
import com.example.superfit.models.MensualidadModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecuperarContra extends AppCompatActivity {
    String PaginaWeb ="https://www.bsite.net/valerioparada/";
    Button recuperar,alitzaestabienbuena;
    EditText Usuario;
    final Cargando cargando = new Cargando(RecuperarContra.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contra);


        recuperar =(Button)findViewById(R.id.Recuperarcuentabtn);
        alitzaestabienbuena =(Button)findViewById(R.id.salirrecuperabtn);
        Usuario =(EditText)findViewById(R.id.Usertxt);


        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alitzarecuperatucontra();
            }
        });

        alitzaestabienbuena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecuperarContra.this,MainActivity.class);
                startActivity(intent);
            }
        });



    }

    public void alitzarecuperatucontra(){
        cargando.cargardialogo();
        String usuario = Usuario.getText().toString();
        if(!usuario.isEmpty()){
            Retrofit retrofit=new Retrofit.Builder().baseUrl(PaginaWeb)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            ClienteApi clienteApi = retrofit.create(ClienteApi.class);
            Call<MensualidadModel> call = clienteApi.Login(Usuario);
        }
        else{
            Toast.makeText(RecuperarContra.this,"Ingrese usuario ",Toast.LENGTH_SHORT).show();
        }
    }
}

