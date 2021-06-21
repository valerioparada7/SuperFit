package com.example.superfit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.superfit.interfaces.ClienteApi;
import com.example.superfit.models.ClientesModel;
import com.example.superfit.models.CuestionarioModel;
import com.example.superfit.models.MensualidadModel;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    // Job http://192.168.56.1:8081/
    // Home http://192.168.100.11:8081/
    // Pagina http://superfit.somee.com/
    // Pagina nueva http://valerioparada7-001-site1.etempurl.com/
    //Pagina Actual nueva https://www.bsite.net/valerioparada/
    String PaginaWeb ="https://www.bsite.net/valerioparada/";
    EditText Usuario,Contraseña;
    Button Aceptar,registrarse;
    LinearLayout linea;
    final Cargando cargando = new Cargando(MainActivity.this);
    //subircambios
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Usuario =(EditText) findViewById(R.id.Usuariotxt);
        Contraseña=(EditText) findViewById(R.id.Contraseñatxt);
        Aceptar =(Button) findViewById(R.id.Loginbtn);
        registrarse=(Button)findViewById(R.id.Registrarsebtn);
        //Validamos si esta logueado
        Getsesion();
        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargando.cargardialogo();
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

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Registro.class);
                startActivity(intent);
            }
        });

    }


    public void Iniciar(String Usuario,String Contraseña){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(PaginaWeb)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ClienteApi clienteApi = retrofit.create(ClienteApi.class);
        Call<MensualidadModel> call = clienteApi.Login(Usuario,Contraseña);
        call.enqueue(new Callback<MensualidadModel>() {
            @Override
            public void onResponse(Call<MensualidadModel> call, Response<MensualidadModel> response) {
                try {
                    if(response.isSuccessful()){
                        MensualidadModel c = response.body();
                        if(c.Cliente.Validar==true){
                            if(GuardarSesion(c.Cliente)==true){
                                cargando.ocultar();
                                Toast.makeText(MainActivity.this,"Bienvenido ",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this,Perfil.class);
                                startActivity(intent);
                            }
                        }
                        else {
                            cargando.ocultar();
                            Toast.makeText(MainActivity.this,c.Cliente.Nombres,Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        cargando.ocultar();
                        Toast.makeText(MainActivity.this,"No se realizo correctamente la conexion",Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception ex){
                    cargando.ocultar();
                    Toast.makeText(MainActivity.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MensualidadModel> call, Throwable t) {
                cargando.ocultar();
                Toast.makeText(MainActivity.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde \r\n Error:"+t.getCause().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean GuardarSesion(ClientesModel cliente)
    {
        SharedPreferences preferences = getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Idcliente",cliente.Id_cliente);
        editor.commit();
        return true;
    }

    public void Getsesion(){
        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        int idcliente = preferences.getInt("Idcliente",0);
        if(idcliente!=0){
            Intent intent = new Intent(MainActivity.this,Perfil.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){

            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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