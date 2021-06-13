package com.example.superfit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.superfit.interfaces.ClienteApi;
import com.example.superfit.models.AlertasModel;
import com.example.superfit.models.AntropometriaModel;
import com.example.superfit.models.ClientesModel;
import com.example.superfit.models.EstatusModel;
import com.example.superfit.models.MensualidadModel;
import com.example.superfit.models.MesesModel;
import com.example.superfit.models.TipoentrenamientoModel;
import com.example.superfit.models.TiporutinaModel;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Antropometria extends AppCompatActivity {
    EditText Peso, Altura,Brazoderechorelajado,Brazoderechofuerza,Brazoizquierdorelajado,
            Brazoizquierdofuerza,Cintura,Cadera,Piernaizquierda,Piernaderecho,
            Pantorrilladerecha,Pantorrillaizquierda;
    TextView IMC,imc2,Msj;
    Button Aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antropometria);

        Peso=(EditText)findViewById(R.id.PesoTxt);
        Altura=(EditText)findViewById(R.id.AlturaTxt);
        IMC=(TextView)findViewById(R.id.imcTxt);
        imc2=(TextView)findViewById(R.id.labelimc);
        Brazoderechorelajado=(EditText)findViewById(R.id.BrazoderechorelajadoTxt);
        Brazoderechofuerza=(EditText)findViewById(R.id.BrazoderechofuerzaTxt);
        Brazoizquierdorelajado=(EditText)findViewById(R.id.BrazoizquierdorelajadoTxt);
        Brazoizquierdofuerza=(EditText)findViewById(R.id.BrazoizquierdofuerzaTxt);
        Cintura=(EditText)findViewById(R.id.CinturaTxt);
        Cadera=(EditText)findViewById(R.id.CaderaTxt);
        Piernaizquierda=(EditText)findViewById(R.id.PiernaizquierdaTxt);
        Piernaderecho=(EditText)findViewById(R.id.PiernaderechoTxt);
        Pantorrilladerecha=(EditText)findViewById(R.id.PantorrilladerechaTxt);
        Pantorrillaizquierda=(EditText)findViewById(R.id.PantorrillaizquierdaTxt);

        Aceptar=(Button)findViewById(R.id.MedidasaceptarBtn);

        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Antropometria.this,Perfil.class);
                startActivity(intent);
/*
                int Idmensualidad = getIntent().getExtras().getInt("Idmensualidad");
                String peso = Peso.getText().toString();
                String altura = Altura.getText().toString();
                String iMC=IMC.getText().toString();
                String brazoderechorelajado		=Brazoderechorelajado.getText().toString();
                String brazoderechofuerza		=Brazoderechofuerza.getText().toString();
                String brazoizquierdorelajado	=Brazoizquierdorelajado.getText().toString();
                String brazoizquierdofuerza		=Brazoizquierdofuerza.getText().toString();
                String cintura					=Cintura.getText().toString();
                String cadera					=Cadera.getText().toString();
                String piernaizquierda			=Piernaizquierda.getText().toString();
                String piernaderecho			=Piernaderecho.getText().toString();
                String pantorrilladerecha		=Pantorrilladerecha.getText().toString();
                String pantorrillaizquierda		=Pantorrillaizquierda.getText().toString();

                if(!peso.isEmpty()&&!altura.isEmpty()&&!brazoderechorelajado.isEmpty()&&!brazoderechofuerza.isEmpty()&&
                    !brazoizquierdorelajado.isEmpty()&&!brazoizquierdofuerza.isEmpty()&&!cintura.isEmpty()&&!cadera.isEmpty()&&
                    !piernaderecho.isEmpty()&&!piernaizquierda.isEmpty()&&!pantorrilladerecha.isEmpty()&&!pantorrillaizquierda.isEmpty()){

                    AntropometriaModel antropometriaModel= new AntropometriaModel();
                    antropometriaModel.Mensualidad=new MensualidadModel();
                    antropometriaModel.Mensualidad.Id_mensualidad=Idmensualidad;
                    antropometriaModel.Peso=Double.parseDouble(peso);
                    antropometriaModel.Altura=Integer.parseInt(altura);
                    antropometriaModel.IMC=Double.parseDouble(iMC);
                    antropometriaModel.Brazoderechorelajado=Double.parseDouble(brazoderechorelajado);
                    antropometriaModel.Brazoderechofuerza=Double.parseDouble(brazoderechofuerza);
                    antropometriaModel.Brazoizquierdorelajado=Double.parseDouble(brazoizquierdorelajado);
                    antropometriaModel.Brazoizquierdofuerza=Double.parseDouble(brazoizquierdofuerza);
                    antropometriaModel.Cintura=Double.parseDouble(cintura);
                    antropometriaModel.Cadera=Double.parseDouble(cadera);
                    antropometriaModel.Piernaizquierda=Double.parseDouble(piernaizquierda);
                    antropometriaModel.Piernaderecho=Double.parseDouble(piernaderecho);
                    antropometriaModel.Pantorrilladerecha=Double.parseDouble(pantorrilladerecha);
                    antropometriaModel.Pantorrillaizquierda=Double.parseDouble(pantorrillaizquierda);
                    RegistrarAntropometria(antropometriaModel);
                }
                else {
                    Toast.makeText(Antropometria.this,"Complete todos los datos antes de continuar",Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        Peso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
                int edad=preferences.getInt("EdadCliente",0);
                String sexo=preferences.getString("SexoCliente","none");
                DecimalFormat df = new DecimalFormat("#.00");
                String height =Altura.getText().toString();
                String weight=s.toString();
                if(!height.isEmpty()&&!weight.isEmpty()){
                    double he2 = 0,we=0,imc=0;
                    he2=Double.parseDouble(height);
                    we=Double.parseDouble(weight);
                    he2=he2/100;
                    he2=he2*he2;
                    imc=we/he2;
                    String valor =df.format(imc);
                    valor=valor.replace(",",".");
                    imc=Double.parseDouble(valor);
                    String rango=ValidarImc(imc,edad,sexo);
                    imc2.setText("Tu IMC es: "+imc +" "+rango);
                    IMC.setText(String.valueOf(imc));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Altura.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
                int edad=preferences.getInt("EdadCliente",0);
                String sexo=preferences.getString("SexoCliente","none");
                DecimalFormat df = new DecimalFormat("#.00");
                String height =s.toString();
                String weight=Peso.getText().toString();
                if(!weight.isEmpty()&&!height.isEmpty()){
                    double he2 = 0,we=0,imc=0;
                    he2=Double.parseDouble(height);
                    we=Double.parseDouble(weight);//2.7225
                    he2=he2/100;
                    he2=he2*he2;
                    imc=we/he2;
                    String valor =df.format(imc);
                    valor=valor.replace(",",".");
                    imc=Double.parseDouble(valor);
                    String rango=ValidarImc(imc,edad,sexo);
                    imc2.setText("Tu IMC es: "+imc +" "+rango);
                    IMC.setText(String.valueOf(imc));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void RegistrarAntropometria(AntropometriaModel newantro){
        // Job http://192.168.56.1:8081/ api/Login/RegistrarAntropometria
        // Home http://192.168.100.11:8081/
        //web superfit.somee.com
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://192.168.56.1:8081/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ClienteApi clienteApi = retrofit.create(ClienteApi.class);
        Call<AlertasModel> call = clienteApi.RegistrarAntropometria(newantro);
        call.enqueue(new Callback<AlertasModel>() {
            @Override
            public void onResponse(Call<AlertasModel> call, Response<AlertasModel> response) {
                try {
                    if(response.isSuccessful()){
                        AlertasModel result = response.body();
                        if(result.Result==true){
                            Toast.makeText(Antropometria.this,result.Mensaje,Toast.LENGTH_SHORT).show();
                            Iniciar();
                        }
                        else {
                            Toast.makeText(Antropometria.this,result.Mensaje,Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(Antropometria.this,"No se realizo la conexion "+response.message(),Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex){
                    Toast.makeText(Antropometria.this,"Ocurrio un error: \r\n" +ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AlertasModel> call, Throwable t) {
                Toast.makeText(Antropometria.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde \r\n Error:"+t.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Iniciar(){
        // Job http://192.168.56.1:8081/
        // Home http://192.168.100.11:8081/

        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        String Usuario=preferences.getString("UsuarioCliente","No asignado");
        String Contraseña=preferences.getString("ContraseñaCliente","No asignado");

        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://192.168.56.1:8081/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ClienteApi clienteApi = retrofit.create(ClienteApi.class);
        Call<MensualidadModel> call = clienteApi.Login(Usuario,Contraseña);
        call.enqueue(new Callback<MensualidadModel>() {
            @Override
            public void onResponse(Call<MensualidadModel> call, Response<MensualidadModel> response) {
                try
                {
                    if(response.isSuccessful()){
                        MensualidadModel c = response.body();
                        if(c.Cliente.Validar==true){
                            GuardarSesion(c,Usuario,Contraseña);
                            Toast.makeText(Antropometria.this,"Bienvenido "+c.Cliente.Nombres,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Antropometria.this,Perfil.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(Antropometria.this,c.Cliente.Nombres,Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(Antropometria.this,"No se realizo la conexion "+response.message(),Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex){
                    Toast.makeText(Antropometria.this,"Ocurrio un error: \r\n" +ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MensualidadModel> call, Throwable t) {
                Toast.makeText(Antropometria.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde \r\n Error:"+t.getCause().toString(),Toast.LENGTH_SHORT).show();
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
        editor.putString("FotoCliente",mensualidadModel.Cliente.Foto_perfil);
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

    public void GetDates(MensualidadModel model)  {
        model.Fecha_inicio=model.Fecha_inicio.substring(0,10);
        model.Fecha_fin=model.Fecha_fin.substring(0,10);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir? se perdera el progreso")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("UsuarioCliente","");
                            editor.putString("ContraseñaCliente","");
                            editor.putInt("EdadCliente",0);
                            editor.commit();
                            Intent intent = new Intent(Antropometria.this,MainActivity.class);
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

    //Calulcar el Imc
    public String ValidarImc(Double imc1,int Edad,String sexo)
    {
        if(sexo.toUpperCase()=="Femenino")
        {
            if(Edad<=14){
                if (imc1 <= 15.49) {
                    return  "Bajo peso";
                }
                else if (imc1 >= 15.50 && imc1 <= 22.79) {
                    return  "Normal";
                }
                else if (imc1 >= 22.80 && imc1 <= 27.39) {
                    return  "Sobrepreso";
                }
                else{
                    return  "Obesidad";
                }
            }
            else if(Edad==15){
                if (imc1 <= 15.99) {
                    return  "Bajo peso";
                }
                else if (imc1 >= 16.00 && imc1 <= 23.59) {
                    return  "Normal";
                }
                else if (imc1 >= 23.60 && imc1 <= 28.29) {
                    return  "Sobrepreso";
                }
                else{
                    return  "Obesidad";
                }
            }
            else if(Edad==16){
                if (imc1 <= 16.29) {
                    return  "Bajo peso";
                }
                else if (imc1 >= 16.30 && imc1 <= 24.19) {
                    return  "Normal";
                }
                else if (imc1 >= 24.20 && imc1 <= 28.99) {
                    return  "Sobrepreso";
                }
                else{
                    return  "Obesidad";
                }
            }
            else if(Edad==17){
                if (imc1 <= 16.49) {
                    return  "Bajo peso";
                }
                else if (imc1 >= 16.50 && imc1 <= 24.59) {
                    return  "Normal";
                }
                else if (imc1 >= 24.60 && imc1 <=29.39) {
                    return  "Sobrepreso";
                }
                else{
                    return  "Obesidad";
                }
            }
            else if(Edad==18){
                if (imc1 <= 16.49) {
                    return  "Bajo peso";
                }
                else if (imc1 >= 16.50 && imc1 <= 24.89) {
                    return  "Normal";
                }
                else if (imc1 >= 24.90 && imc1 <=29.59) {
                    return  "Sobrepreso";
                }
                else{
                    return  "Obesidad";
                }
            }
            else if(Edad==19){
                if (imc1 <= 16.59) {
                    return  "Bajo peso";
                }
                else if (imc1 >= 16.60 && imc1 <= 25.09) {
                    return  "Normal";
                }
                else if (imc1 >= 25.10 && imc1 <=29.79) {
                    return  "Sobrepreso";
                }
                else{
                    return  "Obesidad";
                }
            }
            else{
                if (imc1 <= 16.59) {
                    return  "Bajo peso";
                }
                else if (imc1 >= 16.60 && imc1 <= 25.09) {
                    return  "Normal";
                }
                else if (imc1 >= 25.10 && imc1 <=29.79) {
                    return  "Sobrepreso";
                }
                else{
                    return  "Obesidad";
                }
            }
        }
        else{
            if(Edad<=14){
                if (imc1 <= 15.59) {
                    return  "Bajo peso";
                }
                else if (imc1 >= 15.60 && imc1 <= 21.89) {
                    return  "Normal";
                }
                else if (imc1 >= 21.90 && imc1 <=25.99) {
                    return  "Sobrepreso";
                }
                else{
                    return  "Obesidad";
                }
            }
            else if(Edad==15){
                if (imc1 <= 16.09) {
                    return  "Bajo peso";
                }
                else if (imc1 >= 16.10 && imc1 <=  22.79) {
                    return  "Normal";
                }
                else if (imc1 >= 22.80 && imc1 <=27.09) {
                    return  "Sobrepreso";
                }
                else{
                    return  "Obesidad";
                }
            }
            else if(Edad==16){
                if (imc1 <= 16.59) {
                    return  "Bajo peso";
                }
                else if (imc1 >= 16.60 && imc1 <= 23.59) {
                    return  "Normal";
                }
                else if (imc1 >= 23.60 && imc1 <=27.99) {
                    return  "Sobrepreso";
                }
                else{
                    return  "Obesidad";
                }
            }
            else if(Edad==17){
                if (imc1 <= 16.99) {
                    return  "Bajo peso";
                }
                else if (imc1 >= 17.00 && imc1 <= 24.39) {
                    return  "Normal";
                }
                else if (imc1 >= 24.40 && imc1 <=28.69) {
                    return  "Sobrepreso";
                }
                else{
                    return  "Obesidad";
                }
            }
            else if(Edad==18){
                if (imc1 <= 17.39) {
                    return  "Bajo peso";
                }
                else if (imc1 >= 17.40 && imc1 <= 24.99) {
                    return  "Normal";
                }
                else if (imc1 >= 25.00 && imc1 <=29.29) {
                    return  "Sobrepreso";
                }
                else{
                    return  "Obesidad";
                }
            }
            else if(Edad==19){
                if (imc1 <= 17.69) {
                    return  "Bajo peso";
                }
                else if (imc1 >= 17.70 && imc1 <= 25.49) {
                    return  "Normal";
                }
                else if (imc1 >= 25.50 && imc1 <=29.79) {
                    return  "Sobrepreso";
                }
                else{
                    return  "Obesidad";
                }
            }
            else{
                if (imc1 <= 17.69) {
                    return  "Bajo peso";
                }
                else if (imc1 >= 17.70 && imc1 <= 25.49) {
                    return  "Normal";
                }
                else if (imc1 >= 25.50 && imc1 <=29.79) {
                    return  "Sobrepreso";
                }
                else{
                    return  "Obesidad";
                }
            }
        }
    }

}