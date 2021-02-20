package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.superfit.interfaces.ClienteApi;
import com.example.superfit.models.ClientesModel;
import com.example.superfit.models.MensualidadModel;

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
    EditText Usuario,Contraseña;
    Button Aceptar,registrarse;
    //subircambios
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Usuario =(EditText) findViewById(R.id.Usuariotxt);
        Contraseña=(EditText) findViewById(R.id.Contraseñatxt);
        Aceptar =(Button) findViewById(R.id.Loginbtn);
        registrarse=(Button)findViewById(R.id.Registrarsebtn);

        Getsesion();

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

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Registro.class);
                startActivity(intent);
            }
        });
    }


    public void Iniciar(String Usuario,String Contraseña){
        // Job http://192.168.56.1:8081/
        // Home http://192.168.100.11:8081/
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://192.168.56.1:8081/")
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
                            GuardarSesion(c,Usuario,Contraseña);
                            Toast.makeText(MainActivity.this,"Bienvenido "+c.Cliente.Nombres,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,Perfil.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(MainActivity.this,c.Cliente.Nombres,Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this,"No se realizo correctamente la conexion",Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception ex){
                    Toast.makeText(MainActivity.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MensualidadModel> call, Throwable t) {
                Toast.makeText(MainActivity.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde \r\n Error:"+t.getCause().toString(),Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void GuardarSesion(MensualidadModel mensualidadModel,String User,String Pass)
    {
        GetDates(mensualidadModel);
        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();

        editor.putString("UsuarioCliente",User);
        editor.putString("ContraseñaCliente",Pass);
        editor.putInt("Id_Cliente",mensualidadModel.Cliente.Id_Cliente);
        editor.putString("Nombrescliente",mensualidadModel.Cliente.Nombres);
        if(mensualidadModel.Id_mensualidad!=0){
            editor.putInt("Id_mensualidad",mensualidadModel.Id_mensualidad);

            editor.putInt("Id_tiporutina",mensualidadModel.Tiporutina.Id_tiporutina);
            editor.putString("tiporutina",mensualidadModel.Tiporutina.Tipo);

            editor.putInt("Id_estatus",mensualidadModel.Estatus.Id_estatus);
            editor.putString("estatusDescripcion",mensualidadModel.Estatus.Descripcion);

            editor.putInt("Id_TipoEntrenamiento",mensualidadModel.TipoEntrenamiento.Id_TipoEntrenamiento);
            editor.putString("Tipo_entrenamiento",mensualidadModel.TipoEntrenamiento.Tipo_entrenamiento);

            editor.putString("fechai",mensualidadModel.Fecha_inicio);
            editor.putString("fechaf",mensualidadModel.Fecha_fin);
        }
        else{
            editor.putInt("Id_mensualidad",0);

            editor.putInt("Id_tiporutina",0);
            editor.putString("tiporutina","No asigando");

            editor.putInt("Id_estatus",0);
            editor.putString("estatusDescripcion","No asigando");

            editor.putInt("Id_TipoEntrenamiento",0);
            editor.putString("Tipo_entrenamiento","No asigando");

            editor.putString("fechai","No hay fecha asiganda");
            editor.putString("fechaf","No hay fecha asiganda");
        }
        editor.commit();
    }

    public void Getsesion(){
        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        String usuario=preferences.getString("UsuarioCliente","");
        String contraseña=preferences.getString("ContraseñaCliente","");
        if(!usuario.isEmpty()&&!contraseña.isEmpty()){
            Intent intent = new Intent(MainActivity.this,Perfil.class);
            startActivity(intent);
        }
    }

    public void GetDates(MensualidadModel model)  {
        model.Fecha_inicio=model.Fecha_inicio.substring(0,10);
        model.Fecha_fin=model.Fecha_fin.substring(0,10);
    }

}