package com.example.superfit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.superfit.interfaces.ClienteApi;
import com.example.superfit.models.ClientesModel;
import com.example.superfit.models.Imagenes;
import com.example.superfit.models.MensualidadModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditarPerfil extends AppCompatActivity {
    //Pagina Actual nueva https://www.bsite.net/valerioparada/
    String PaginaWeb ="https://www.bsite.net/valerioparada/";
    Imagenes imagenes = new Imagenes();
    //contador de las imagenes que se seleccionan


    //Datos personales
    EditText Nombre,Ap,Am,Apodo,Edad,Telefono,Email,Contraseña;
    Spinner listsexo;
    String[] Sexo =  {"Hombre","Mujer"};
    ClientesModel cliente = new ClientesModel();
    Button Registraclientebtn;
    int IdclienteUpdate=0;
    final Cargando cargando = new Cargando(EditarPerfil.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

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
        Registraclientebtn =(Button)findViewById(R.id.RegistrarDatosbtn);

        Registraclientebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = ValidarCliente();
                if(result==true){
                    cargando.cargardialogo();
                    UpdateCliente();
                }
            }
        });

        GetCliente();

    }

    public void GetCliente(){
        cargando.cargardialogo();
        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        int Idcliente =preferences.getInt("Idcliente",0);
        IdclienteUpdate=Idcliente;
        Retrofit retrofit=new Retrofit.Builder().baseUrl(PaginaWeb)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ClienteApi clienteApi = retrofit.create(ClienteApi.class);
        Call<ClientesModel> call = clienteApi.GetCliente(Idcliente);
        call.enqueue(new Callback<ClientesModel>() {
            @Override
            public void onResponse(Call<ClientesModel> call, Response<ClientesModel> response) {
                try {
                    if(response.isSuccessful()){
                        ClientesModel c = response.body();
                        MostrarDatos(c);
                    }
                    else{
                        Toast.makeText(EditarPerfil.this,"No se realizo correctamente la conexion",Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception ex){
                    Toast.makeText(EditarPerfil.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ClientesModel> call, Throwable t) {
                Toast.makeText(EditarPerfil.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde \r\n Error:"+t.getCause().toString(),Toast.LENGTH_SHORT).show();
            }
        });
        cargando.ocultar();
    }
    public void MostrarDatos(ClientesModel cliente){
        Nombre.setText(cliente.Nombres);
        Ap.setText(cliente.Apellido_paterno);
        Am.setText(cliente.Apellido_materno);
        Apodo.setText(cliente.Apodo);
        Telefono.setText(String.valueOf(cliente.Telefono).replace(".",""));
        Edad.setText(String.valueOf(cliente.Edad));
        Email.setText(cliente.Correo_electronico);
        Contraseña.setText(cliente.Contraseña);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_edittext_sexo,Sexo);
        listsexo.setAdapter(adapter);
        int spinnerPosition = adapter.getPosition(cliente.Sexo);
        listsexo.setSelection(spinnerPosition);
    }
    public void UpdateCliente(){
    Retrofit retrofit=new Retrofit.Builder().baseUrl(PaginaWeb)
            .addConverterFactory(GsonConverterFactory.create()).build();
    ClienteApi clienteApi = retrofit.create(ClienteApi.class);
    Call<Boolean> call = clienteApi.UpdateCliente(cliente);
    call.enqueue(new Callback<Boolean>() {
        @Override
        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
            try {
                if(response.isSuccessful()){
                    Boolean c = response.body();
                    if(c==true){
                        Toast.makeText(EditarPerfil.this,"Datos Actualizados",Toast.LENGTH_SHORT).show();
                        GetCliente();
                    }
                    else{
                        Toast.makeText(EditarPerfil.this,"Ocurrio un error al actualizar los datos intente mas tarde",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(EditarPerfil.this,"No se realizo correctamente la conexion",Toast.LENGTH_SHORT).show();
                }

            }
            catch (Exception ex){
                Toast.makeText(EditarPerfil.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Boolean> call, Throwable t) {
            Toast.makeText(EditarPerfil.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde \r\n Error:"+t.getCause().toString(),Toast.LENGTH_SHORT).show();
        }
    });
        cargando.ocultar();
}
    public Boolean ValidarCliente(){
        boolean result= false;
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
            cliente.Id_cliente =IdclienteUpdate;
            cliente.Nombres =n.toUpperCase();
            cliente.Apellido_paterno=ap.toUpperCase();
            cliente.Apellido_materno=am.toUpperCase();
            cliente.Apodo=apo;
            cliente.Telefono= Double.parseDouble(tel);
            cliente.Edad=Integer.parseInt(ed);
            cliente.Correo_electronico=em;
            cliente.Contraseña=pass;
            cliente.Sexo=sex;
            result = true;
        }
        else{
            Toast.makeText(EditarPerfil.this, "Complete todos los datos", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            Intent intent = new Intent(EditarPerfil.this,Perfil.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}