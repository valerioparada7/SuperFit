package com.example.superfit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.superfit.Clases.AdaptadorEjercicios;
import com.example.superfit.Clases.Ejercicios;
import com.example.superfit.interfaces.CatalogoApi;
import com.example.superfit.interfaces.DetallerutinaApi;
import com.example.superfit.models.AlertasModel;
import com.example.superfit.models.DetallerutinaModel;
import com.example.superfit.models.TiporutinaModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.layout.simple_list_item_1;

public class Rutinas extends AppCompatActivity {
    //Pagina Actual nueva https://www.bsite.net/valerioparada/
    String PaginaWeb ="https://www.bsite.net/valerioparada/";
    String PaginaWebFotos ="https://superflyfit.000webhostapp.com/imagenes/estiramiento/";

    //Spinner para los dias
    ArrayList<String> Diasarray = new ArrayList<String>();
    Spinner listDias;

    //Spinner para los sets
    ArrayList<String> Setsarray = new ArrayList<String>();
    Spinner listSets;

    //para los ejercicios
    public ListView lvitems;
    public AdaptadorEjercicios adptador;
    Button instrucciones,calentamiento;
    final Cargando cargando = new Cargando(Rutinas.this);

    public AlertDialog.Builder dialogbuildinstru;
    public AlertDialog dialoginstru;

    public AlertDialog.Builder dialogbuildercalen;
    public AlertDialog dialogcalen;

    TextView parrafof;

    TextView ll1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14,l15,l16,l17,l18,l19;
    ImageView m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12,m13,m14,m15,m16,m17,m18,m19;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutinas);
        listDias =(Spinner)findViewById(R.id.ListaDiasId);
        listSets =(Spinner)findViewById(R.id.ListaSetsId);
        instrucciones =(Button)findViewById(R.id.instruccionesbtn);
        calentamiento =(Button)findViewById(R.id.calentamientobtn);
        lvitems=(ListView)findViewById(R.id.lisviewejercicios);
        ObtenerDias();
        ObtenerSets();
        listDias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ObtenerSets();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listSets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GetDetalleRutinaEjercicios();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        instrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenModalInstruccion();
            }
        });

        calentamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenModalCalentamiento();
            }
        });
    }

    public void OpenModalInstruccion(){
        dialogbuildinstru = new AlertDialog.Builder(this);
        final View conetent = getLayoutInflater().inflate(R.layout.modalinstrucciones,null);
        parrafof = (TextView)conetent.findViewById(R.id.parrafomodal);
        String narracion = "- Estirar por lo menos 5 minutos cada grupo muscular antes de todo.\n" +
                "\n- Calentar de 5 a 10 minutos antes de cada rutina de ejercicio.\n" +
                "\n- Calentamiento para la parte inferior (piernas): ejemplo hacer 20 a 25 sentadillas con tu propio peso o saltar por 2 min la cuerda descansas 1 minuto y lo vuelves hacer hasta completar los 5 a 10 minutos cuando sientas que tu cuerpo ya está sintiéndose activo.\n" +
                "\n- Calentamiento para la parte superior (brazos, espalda, hombres y pecho): ejemplo puede ser de levantamiento de pesas de 20 repeticiones con un peso super ligero de ½ kg a 1 kg como se muestra en la imagen descansas 1 minuto y lo vuelves hacer hasta completar los 5 a 10 minutos cuando sientas que tu cuerpo ya está sintiéndose activo.\n" +
                "\n- Descanso entre ejercicios será de 1 a 2 minutos tomar agua entre descansos de 1 a 3 sorbos sin sobrepasarse.\n" +
                "\n- Lleva el cronometro de tus descansos con ayuda de un reloj para maximizar tiempos.\n" +
                "\n- Ocupar botellas de agua de 600militros en caso de no tener pesas pequeñas y de garrafas de 5 litros o 10 litros en el caso de pesas grandes.\n" +
                "\n- Cuando termines cada set apretar y contraer el musculo correspondiente que estas ejercitando por ejemplo si estas entrenando piernas al terminar tu set apretaras las piernas durante 10 segundos.\n" +
                "\n- Inhalar por la nariz y exhalar por la boca: inhalar cuando hagas el movimiento hacia arriba y exhalar cuando bajas.";
        parrafof.setText(narracion);
        dialogbuildinstru.setView(conetent);
        dialoginstru = dialogbuildinstru.create();
        dialoginstru.show();
    }

    public void OpenModalCalentamiento(){
        dialogbuildercalen = new AlertDialog.Builder(this);
        final View conetent = getLayoutInflater().inflate(R.layout.modalestiramiento,null);
        ll1 = (TextView)conetent.findViewById(R.id.label1);
        l2 = (TextView)conetent.findViewById(R.id.label2);
        l3 = (TextView)conetent.findViewById(R.id.label3);
        l4 = (TextView)conetent.findViewById(R.id.label4);
        l5 = (TextView)conetent.findViewById(R.id.label5);
        l6 = (TextView)conetent.findViewById(R.id.label6);
        l7 = (TextView)conetent.findViewById(R.id.label7);
        l8 = (TextView)conetent.findViewById(R.id.label8);
        l9 = (TextView)conetent.findViewById(R.id.label9);
        l10 = (TextView)conetent.findViewById(R.id.label10);
        ll1 = (TextView)conetent.findViewById(R.id.label11);
        l12 = (TextView)conetent.findViewById(R.id.label12);
        l13 = (TextView)conetent.findViewById(R.id.label13);
        l14 = (TextView)conetent.findViewById(R.id.label14);
        l15 = (TextView)conetent.findViewById(R.id.label15);
        l16 = (TextView)conetent.findViewById(R.id.label16);
        l17 = (TextView)conetent.findViewById(R.id.label17);
        l18 = (TextView)conetent.findViewById(R.id.label18);
        l19 = (TextView)conetent.findViewById(R.id.label19);
        m1 = (ImageView)conetent.findViewById(R.id.imagen1);
        m2 =  (ImageView)conetent.findViewById(R.id.imagen2);
        m3 =  (ImageView)conetent.findViewById(R.id.imagen3);
        m4 =  (ImageView)conetent.findViewById(R.id.imagen4);
        m5 =  (ImageView)conetent.findViewById(R.id.imagen5);
        m6 =  (ImageView)conetent.findViewById(R.id.imagen6);
        m7 =  (ImageView)conetent.findViewById(R.id.imagen7);
        m8 =  (ImageView)conetent.findViewById(R.id.imagen8);
        m9 =  (ImageView)conetent.findViewById(R.id.imagen9);
        m10 = (ImageView)conetent.findViewById(R.id.imagen10);
        m11 = (ImageView)conetent.findViewById(R.id.imagen11);
        m12 = (ImageView)conetent.findViewById(R.id.imagen12);
        m13 = (ImageView)conetent.findViewById(R.id.imagen13);
        m14 = (ImageView)conetent.findViewById(R.id.imagen14);
        m15 = (ImageView)conetent.findViewById(R.id.imagen15);
        m16 = (ImageView)conetent.findViewById(R.id.imagen16);
        m17 = (ImageView)conetent.findViewById(R.id.imagen17);
        m18 = (ImageView)conetent.findViewById(R.id.imagen18);
        m19 = (ImageView)conetent.findViewById(R.id.imagen19);
        Glide.with(getApplication()).load(PaginaWebFotos+"cuelloup.gif").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache (true).into(m1);
        Glide.with(getApplication()).load(PaginaWebFotos+"cuellodown.gif").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache (true).into(m2);
        Glide.with(getApplication()).load(PaginaWebFotos+"cuelloder.gif").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache (true).into(m3);
        Glide.with(getApplication()).load(PaginaWebFotos+"cuello.gif ").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache (true).into(m4);
        Glide.with(getApplication()).load(PaginaWebFotos+"flexionder.gif").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache (true).into(m5);
        Glide.with(getApplication()).load(PaginaWebFotos+"flexionizq.gif").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache (true).into(m6);
        Glide.with(getApplication()).load(PaginaWebFotos+"pies.gif").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache (true).into(m7);
        Glide.with(getApplication()).load(PaginaWebFotos+"piernas.gif").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache (true).into(m8);
        Glide.with(getApplication()).load(PaginaWebFotos+"piernaizq.gif").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache (true).into(m9);
        Glide.with(getApplication()).load(PaginaWebFotos+"piernader.gif").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache (true).into(m10);
        Glide.with(getApplication()).load(PaginaWebFotos+"tobilloizq.gif").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache (true).into(m11);
        Glide.with(getApplication()).load(PaginaWebFotos+"tobilloder.gif").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache (true).into(m12);
        Glide.with(getApplication()).load(PaginaWebFotos+"hombrosc.gif").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache (true).into(m13);
        Glide.with(getApplication()).load(PaginaWebFotos+"molinos.gif").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache (true).into(m14);
        Glide.with(getApplication()).load(PaginaWebFotos+"brazosc.gif").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache (true).into(m15);
        Glide.with(getApplication()).load(PaginaWebFotos+"brazosp.gif").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache (true).into(m16);
        Glide.with(getApplication()).load(PaginaWebFotos+"brazohombro.gif").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache (true).into(m17);
        Glide.with(getApplication()).load(PaginaWebFotos+"muñecas.gif").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache (true).into(m18);
        Glide.with(getApplication()).load(PaginaWebFotos+"torso.gif ").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache (true).into(m19);
        dialogbuildercalen.setView(conetent);
        dialogcalen = dialogbuildercalen.create();
        dialogcalen.show();

    }

    public void SetImagenes(){

    }

    public void ObtenerDias(){

        String[] dias = { "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado" };
        for (int i = 0; i < dias.length; i++)
        {
            Diasarray.add(dias[i]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_edittext_sexo,Diasarray);
        listDias.setAdapter(adapter);
    }

    public void ObtenerSets(){
        SharedPreferences preferences = getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        int IdMensualidad = preferences.getInt("IdMensualidad",0);
        int IdEstatus = preferences.getInt("IdEstatus",0);
        int Iddia =listDias.getSelectedItemPosition();
        Iddia+=2;
        if(IdMensualidad!=0){
        // Job http://192.168.56.1:8081/
            // Home http://:192.168.100.118081/
            //web superfit.somee.com
            Retrofit retrofit=new Retrofit.Builder().baseUrl(PaginaWeb)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            DetallerutinaApi detalle = retrofit.create(DetallerutinaApi.class);
            Call<List<DetallerutinaModel>> call = detalle.GetDetalleRutinaSets(IdMensualidad,IdEstatus,Iddia);
            call.enqueue(new Callback<List<DetallerutinaModel>>() {
                @Override
                public void onResponse(Call<List<DetallerutinaModel>> call, Response<List<DetallerutinaModel>> response) {
                    try {
                        if(response.isSuccessful()){
                            List<DetallerutinaModel> result = response.body();
                            if(result.size()>0){
                                Setsarray.clear();
                                for (int i=0;i<result.size();i++){
                                    Setsarray.add(""+result.get(i).TipoSet);
                                }
                                AgrgarSetsSpiner(Setsarray);
                                GetDetalleRutinaEjercicios();
                            }
                            else {
                                Setsarray.clear();
                                Setsarray.add("No hay sets asignados");
                                AgrgarSetsSpiner(Setsarray);
                            }
                        }
                        else{
                            Toast.makeText(Rutinas.this,"No se realizo la conexion "+response.message(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception ex){
                        Toast.makeText(Rutinas.this,"Ocurrio un error: \r\n" +ex.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<DetallerutinaModel>> call, Throwable t) {
                    Toast.makeText(Rutinas.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde \r\n Error:"+t.getMessage().toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(Rutinas.this,"No hay sets para este dia",Toast.LENGTH_SHORT).show();
        }
    }

    public void AgrgarSetsSpiner(ArrayList<String> Lista){
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_edittext_sexo,Lista);
        listSets.setAdapter(adapter);
    }

    //Agregar los ejercicios
    // Job http://192.168.56.1:8081/
    // Home http://192.168.100.11:8081/
    // web superfit.somee.com
    public void GetDetalleRutinaEjercicios(){
        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        int IdMensualidad =preferences.getInt("IdMensualidad",0);
        int IdEstatus =preferences.getInt("IdEstatus",0);
        int Iddia =listDias.getSelectedItemPosition();
        int Idset =listSets.getSelectedItemPosition();
        Iddia+=2;
        Idset+=1;
        if(IdMensualidad!=0) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(PaginaWeb)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            DetallerutinaApi rutinaapi = retrofit.create(DetallerutinaApi.class);
            Call<List<DetallerutinaModel>> call = rutinaapi.GetDetalleRutinaEjercicios(IdMensualidad, IdEstatus, Iddia, Idset);
            call.enqueue(new Callback<List<DetallerutinaModel>>() {
                @Override
                public void onResponse(Call<List<DetallerutinaModel>> call, Response<List<DetallerutinaModel>> response) {
                    try {
                        if (response.isSuccessful()) {
                            List<DetallerutinaModel> rutinalist = response.body();
                            ArrayList<com.example.superfit.Clases.Ejercicios> ejerciciosArrayList = new ArrayList<>();
                            for (int i = 0; i < rutinalist.size(); i++) {
                                String Url_Imagen = PaginaWeb + rutinalist.get(i).Ejercicios.Ubicacion_imagen;
                                ejerciciosArrayList.add(new com.example.superfit.Clases.Ejercicios(
                                        Url_Imagen,
                                        rutinalist.get(i).Ejercicios.Ejercicio,
                                        rutinalist.get(i).Ejercicios.Descripcion,
                                        String.valueOf(rutinalist.get(i).Repeticiones),
                                        String.valueOf(rutinalist.get(i).Series)
                                ));
                            }
                            Exercicies(ejerciciosArrayList);
                        }
                    }
                    catch (Exception ex) {
                        Toast.makeText(Rutinas.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<DetallerutinaModel>> call, Throwable t) {
                    Toast.makeText(Rutinas.this, "No se conecto al servidor verifique su conexion \r\nintente mas tarde", Toast.LENGTH_SHORT).show();
                }

            });
        }
        else{
            Toast.makeText(Rutinas.this,"No hay ejercicios para este set",Toast.LENGTH_SHORT).show();
        }
    }

    public void Exercicies(ArrayList<Ejercicios> Lista){
        adptador = new AdaptadorEjercicios(this,Lista);
        lvitems.setAdapter(adptador);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            Intent intent = new Intent(Rutinas.this,Perfil.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}