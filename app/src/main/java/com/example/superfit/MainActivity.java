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
    Button Aceptar;
    //subircambios
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
                            GuardarSesion(c);
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
                Log.w("",t.getCause().toString());
                Toast.makeText(MainActivity.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde \r\n Error:"+t.getCause().toString(),Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void GuardarSesion(MensualidadModel mensualidadModel)
    {
        GetDates(mensualidadModel);
        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt("Id_mensualidad",mensualidadModel.Id_mensualidad);

        editor.putInt("Id_Cliente",mensualidadModel.Cliente.Id_Cliente);
        editor.putString("Nombrescliente",mensualidadModel.Cliente.Nombres);

        editor.putInt("Id_tiporutina",mensualidadModel.Tiporutina.Id_tiporutina);
        editor.putString("tiporutina",mensualidadModel.Tiporutina.Tipo);

        editor.putInt("Id_estatus",mensualidadModel.Estatus.Id_estatus);
        editor.putString("estatusDescripcion",mensualidadModel.Estatus.Descripcion);

        editor.putInt("Id_TipoEntrenamiento",mensualidadModel.TipoEntrenamiento.Id_TipoEntrenamiento);
        editor.putString("Tipo_entrenamiento",mensualidadModel.TipoEntrenamiento.Tipo_entrenamiento);

        editor.putString("fechai",mensualidadModel.Fecha_inicio);
        editor.putString("fechaf",mensualidadModel.Fecha_fin);

        editor.commit();
    }
    public void GetDates(MensualidadModel model)  {
        model.Fecha_inicio=model.Fecha_inicio.substring(0,10);
        model.Fecha_fin=model.Fecha_fin.substring(0,10);
    }

}