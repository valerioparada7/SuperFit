package com.example.superfit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.superfit.Clases.AdaptadorMedidasDetalle;
import com.example.superfit.Clases.AdaptadorMensualidadDetalle;
import com.example.superfit.Clases.Antropometria;
import com.example.superfit.Clases.MensualidadDetalle;
import com.example.superfit.interfaces.ClienteApi;
import com.example.superfit.models.AntropometriaModel;
import com.example.superfit.models.MensualidadModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class mensualidadmedidasmenu extends AppCompatActivity {

    // Job http://192.168.56.1:8081/
    // Home http://192.168.100.11:8081/
    // Pagina http://superfit.somee.com/
    // Pagina nueva http://valerioparada7-001-site1.etempurl.com/
    //Pagina Actual nueva https://www.bsite.net/valerioparada/
    String PaginaWeb ="https://www.bsite.net/valerioparada/";
    public MensualidadDetalle ModeloMensualidad;
    public ListView lvitems;
    public AdaptadorMedidasDetalle adptador;

    //Para el modal


//Boton para abrir el modal
    Button OpenModal;

    public AlertDialog.Builder modalmeidas;
    public AlertDialog modal;
    final Cargando cargando = new Cargando(mensualidadmedidasmenu.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensualidadmedidasmenu);
        OpenModal =(Button) findViewById(R.id.RegistrarMedidaBtn);
        lvitems=(ListView)findViewById(R.id.Listmensualidadmedidasmenu);
        cargando.cargardialogo();
        GetMedidas();
        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        int IdEstatus = preferences.getInt("IdEstatus",0);
        if(IdEstatus==2||IdEstatus==4){
            OpenModal.setVisibility(View.VISIBLE);
        }
        else{
            OpenModal.setVisibility(View.INVISIBLE);
        }

        OpenModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(mensualidadmedidasmenu.this,RegistroMedidas.class);
                    ModeloMensualidad = (MensualidadDetalle) getIntent().getSerializableExtra("ModeloMes");
                    intent.putExtra("ModeloMes",ModeloMensualidad);
                    startActivity(intent);
            }
        });
    }

    public void GetMedidas(){
        ModeloMensualidad = (MensualidadDetalle) getIntent().getSerializableExtra("ModeloMes");
        int IdMensualidad = ModeloMensualidad.getIdmensualidad();
        Retrofit retrofit=new Retrofit.Builder().baseUrl(PaginaWeb)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ClienteApi medidas = retrofit.create(ClienteApi.class);
        Call<List<AntropometriaModel>> call = medidas.GetAsesoriaantropometria(IdMensualidad);
        call.enqueue(new Callback<List<AntropometriaModel>>() {
            @Override
            public void onResponse(Call<List<AntropometriaModel>> call, Response<List<AntropometriaModel>> response) {
                try {
                    if(response.isSuccessful()){
                        List<AntropometriaModel> medidaslist = response.body();
                        ArrayList<com.example.superfit.Clases.Antropometria> medidasArray = new ArrayList<>();
                        if(medidaslist.size()>0)
                        {
                            for (int i = 0; i < medidaslist.size(); i++) {
                                String Url_Imagenfrontal = PaginaWeb + medidaslist.get(i).Fotofrontal;
                                String Url_Imagenperfil = PaginaWeb + medidaslist.get(i).Fotoperfil;
                                String Url_Imagenposterior = PaginaWeb + medidaslist.get(i).Fotoposterior;
                                medidasArray.add(new com.example.superfit.Clases.Antropometria(
                                                medidaslist.get(i) .Id,
                                                medidaslist.get(i) .Peso,
                                                medidaslist.get(i) .Altura,
                                                medidaslist.get(i) .IMC,
                                                medidaslist.get(i) .Brazoderechorelajado,
                                                medidaslist.get(i) .Brazoderechofuerza,
                                                medidaslist.get(i) .Brazoizquierdorelajado,
                                                medidaslist.get(i) .Brazoizquierdofuerza,
                                                medidaslist.get(i) .Cintura,
                                                medidaslist.get(i) .Cadera,
                                                medidaslist.get(i) .Piernaizquierda,
                                                medidaslist.get(i) .Piernaderecho,
                                                medidaslist.get(i) .Pantorrilladerecha,
                                                medidaslist.get(i) .Pantorrillaizquierda,
                                                Url_Imagenfrontal,
                                                Url_Imagenperfil,
                                                Url_Imagenposterior,
                                                medidaslist.get(i).Fecharegistro
                                        ));
                            }
                            Medidas(medidasArray);
                        }
                        else{
                            Toast.makeText(mensualidadmedidasmenu.this,"No hay registro de medidas",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                catch (Exception ex){
                    Toast.makeText(mensualidadmedidasmenu.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AntropometriaModel>> call, Throwable t) {
                Toast.makeText(mensualidadmedidasmenu.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Medidas(ArrayList<com.example.superfit.Clases.Antropometria> Lista){
        adptador = new AdaptadorMedidasDetalle(this,Lista);
        lvitems.setAdapter(adptador);
        cargando.ocultar();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            Intent intent = new Intent(mensualidadmedidasmenu.this,Mensualidad_menu.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }


}