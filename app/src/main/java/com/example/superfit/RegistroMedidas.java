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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.superfit.Clases.MensualidadDetalle;
import com.example.superfit.interfaces.ClienteApi;
import com.example.superfit.models.AlertasModel;
import com.example.superfit.models.AntropometriaModel;
import com.example.superfit.models.Imagenes;
import com.example.superfit.models.MensualidadModel;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroMedidas extends AppCompatActivity {
    // Job http://192.168.56.1:8081/
    // Home http://192.168.100.11:8081/
    // Pagina http://superfit.somee.com/
    // Pagina nueva http://valerioparada7-001-site1.etempurl.com/
    //Pagina Actual nueva https://www.bsite.net/valerioparada/
    String PaginaWeb ="https://www.bsite.net/valerioparada/";
    //Medidas
    //contador de las imagenes que se seleccionan
    public int cuentaperfil=0,perfil=0,frontal=0,posterior=0;
    EditText Peso, Altura,Brazoderechorelajado,Brazoderechofuerza,Brazoizquierdorelajado,
            Brazoizquierdofuerza,Cintura,Cadera,Piernaizquierda,Piernaderecho,
            Pantorrilladerecha,Pantorrillaizquierda;
    TextView IMC,imc2,Msj;
    //fotos de las medidas
    //perfil
    ImageView imagenperfilmedida;
    Button fotoperfilmedida;
    private int img_requestperfilmedida = 21;
    private Bitmap bitmapfotousuarioperfilmedida;
    LinearLayout linerfotoperfilmedida;
    //frontal
    ImageView imagenfrontal;
    Button fotofrontal;
    private int img_requestfrontal = 21;
    private Bitmap bitmapfotofrontal;
    LinearLayout linerfotofrontal;
    //posterior
    ImageView imagenposterior;
    Button fotoposterior;
    private int img_requestposterior = 21;
    private Bitmap bitmapfotoposterior;
    LinearLayout linerfotoposterior;

    Button RegistroMedidas;
    AntropometriaModel antropometriaModel= new AntropometriaModel();
    public MensualidadDetalle ModeloMensualidad;
    final Cargando cargando = new Cargando(RegistroMedidas.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_medidas);

        RegistroMedidas =(Button) findViewById(R.id.RegistrarMedidasbtn);

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
        //imagenes de las medidas
        //perfil
        imagenperfilmedida=(ImageView)findViewById(R.id.FotoperfilmedidaId);
        fotoperfilmedida=(Button)findViewById(R.id.FotoperfilmedidaBtn);
        linerfotoperfilmedida=(LinearLayout)findViewById(R.id.linerfotoperfilmedidaId);
        //frontal
        imagenfrontal=(ImageView)findViewById(R.id.FotofrontalId);
        fotofrontal=(Button)findViewById(R.id.FotofrontalBtn);
        linerfotofrontal=(LinearLayout)findViewById(R.id.linerfotofrontalId);
        //posterior
        imagenposterior=(ImageView)findViewById(R.id.FotoposteriorId);
        fotoposterior=(Button)findViewById(R.id.FotoposteriorBtn);
        linerfotoposterior=(LinearLayout)findViewById(R.id.linerfotoposteriorId);

        RegistroMedidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = ValidarMedidas();
                if(result==true){
                    cargando.cargardialogo();
                    //Asignamos los valores en las imagenes
                    UpdateImagen();
                    RegistrarMedidas();
                }
            }
        });

        //calcula imc
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
        //botones para seleccionar fotos
        //fotos de als medias

        fotoperfilmedida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,img_requestperfilmedida);
                perfil=1;
                cuentaperfil=0;
                frontal=0;
                posterior=0;
            }
        });
        fotofrontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,img_requestfrontal);
                frontal=1;
                perfil=0;
                cuentaperfil=0;
                posterior=0;
            }
        });
        fotoposterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,img_requestposterior);
                posterior=1;
                perfil=0;
                cuentaperfil=0;
                frontal=0;
            }
        });

    }

    public void RegistrarMedidas(){
        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        int Idcliente =preferences.getInt("Idcliente",0);
        Retrofit retrofit=new Retrofit.Builder().baseUrl(PaginaWeb)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ClienteApi clienteApi = retrofit.create(ClienteApi.class);
        Call<AlertasModel> call = clienteApi.RegistrarMedidas(antropometriaModel,Idcliente);
        call.enqueue(new Callback<AlertasModel>() {
            @Override
            public void onResponse(Call<AlertasModel> call, Response<AlertasModel> response) {
                try {
                    if(response.isSuccessful()){
                        AlertasModel result = response.body();
                        if(result.Result==true){
                            cargando.ocultar();
                            AlertDialog.Builder builder= new AlertDialog.Builder(RegistroMedidas.this);
                            builder.setMessage("Registro exito")
                                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(RegistroMedidas.this,mensualidadmedidasmenu.class);
                                            ModeloMensualidad = (MensualidadDetalle) getIntent().getSerializableExtra("ModeloMes");
                                            intent.putExtra("ModeloMes",ModeloMensualidad);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(RegistroMedidas.this,mensualidadmedidasmenu.class);
                                            ModeloMensualidad = (MensualidadDetalle) getIntent().getSerializableExtra("ModeloMes");
                                            intent.putExtra("ModeloMes",ModeloMensualidad);
                                            startActivity(intent);
                                        }
                                    });
                            builder.show();
                        }
                        else {
                            Toast.makeText(RegistroMedidas.this,result.Mensaje,Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(RegistroMedidas.this,"No se realizo la conexion "+response.message(),Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex){
                    Toast.makeText(RegistroMedidas.this,"Ocurrio un error: \r\n" +ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AlertasModel> call, Throwable t) {
                Toast.makeText(RegistroMedidas.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde \r\n Error:"+t.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void UpdateImagen(){
        try
        {
            if(bitmapfotousuarioperfilmedida!=null){
                ByteArrayOutputStream byteperfil = new ByteArrayOutputStream();
                bitmapfotousuarioperfilmedida.compress(Bitmap.CompressFormat.JPEG,75,byteperfil);
                byte[] byte64perfil =byteperfil.toByteArray();
                String base64perfil = Base64.encodeToString(byte64perfil,Base64.DEFAULT);
                antropometriaModel.Foto_perfil64 = base64perfil;
            }
            if(bitmapfotofrontal!=null){
                ByteArrayOutputStream bytefrontal = new ByteArrayOutputStream();
                bitmapfotofrontal.compress(Bitmap.CompressFormat.JPEG,75,bytefrontal);
                byte[] byte64frontal =bytefrontal.toByteArray();
                String base64frontal = Base64.encodeToString(byte64frontal,Base64.DEFAULT);
                antropometriaModel.Foto_frontal64 =base64frontal;
            }
            if(bitmapfotoposterior!=null){
                ByteArrayOutputStream byteposterior = new ByteArrayOutputStream();
                bitmapfotoposterior.compress(Bitmap.CompressFormat.JPEG,75,byteposterior);
                byte[] byte64posterior = byteposterior.toByteArray();
                String base64posterior= Base64.encodeToString(byte64posterior,Base64.DEFAULT);
                antropometriaModel.Foto_posterior64 =base64posterior;
            }
        }
        catch (Exception ex){
            Toast.makeText(RegistroMedidas.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
            Log.w("Error VALERIO:",ex.getMessage());
        }
    }

    //Cargar imagenes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode==img_requestperfilmedida||requestCode==img_requestfrontal ||requestCode==img_requestposterior)
                &&resultCode==RESULT_OK && data !=null){
            Uri pathperfil = data.getData();
            Uri pathfrontal = data.getData();
            Uri pathposterior = data.getData();
            try {
                if(perfil==1){
                    bitmapfotousuarioperfilmedida = MediaStore.Images.Media.getBitmap(getContentResolver(),pathperfil);
                    imagenperfilmedida.setImageBitmap(bitmapfotousuarioperfilmedida);
                    linerfotoperfilmedida.setVisibility(View.VISIBLE);
                    linerfotoperfilmedida.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    linerfotoperfilmedida.requestLayout();
                }
                else if(frontal==1){
                    bitmapfotofrontal = MediaStore.Images.Media.getBitmap(getContentResolver(),pathfrontal);
                    imagenfrontal.setImageBitmap(bitmapfotofrontal);
                    linerfotofrontal.setVisibility(View.VISIBLE);
                    linerfotofrontal.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    linerfotofrontal.requestLayout();
                }
                else if(posterior==1){
                    bitmapfotoposterior = MediaStore.Images.Media.getBitmap(getContentResolver(),pathposterior);
                    imagenposterior.setImageBitmap(bitmapfotoposterior);
                    linerfotoposterior.setVisibility(View.VISIBLE);
                    linerfotoposterior.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    linerfotoposterior.requestLayout();
                }
                else{

                }

            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            Intent intent = new Intent(RegistroMedidas.this,mensualidadmedidasmenu.class);
            ModeloMensualidad = (MensualidadDetalle) getIntent().getSerializableExtra("ModeloMes");
            intent.putExtra("ModeloMes",ModeloMensualidad);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
    //Validar medidas
    public Boolean ValidarMedidas(){
        boolean result= false;
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

            ModeloMensualidad = (MensualidadDetalle) getIntent().getSerializableExtra("ModeloMes");
            int IdMensualidad = ModeloMensualidad.getIdmensualidad();
            antropometriaModel.Mensualidad=new MensualidadModel();
            antropometriaModel.Mensualidad.Id_mensualidad=IdMensualidad;
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
            result = true;
        }
        else {
            Toast.makeText(RegistroMedidas.this,"Complete todos los datos",Toast.LENGTH_SHORT).show();
        }
        return  result;
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