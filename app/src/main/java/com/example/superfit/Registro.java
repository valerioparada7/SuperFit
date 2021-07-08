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
import android.service.autofill.FieldClassification;
import android.service.autofill.RegexValidator;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.text.TextWatcher;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.superfit.interfaces.CatalogoApi;
import com.example.superfit.interfaces.ClienteApi;
import com.example.superfit.models.AlertasModel;
import com.example.superfit.models.AntropometriaModel;
import com.example.superfit.models.ClientesModel;
import com.example.superfit.models.CuestionarioModel;
import com.example.superfit.models.Imagenes;
import com.example.superfit.models.MensualidadModel;
import com.example.superfit.models.RegistroCliente;
import com.example.superfit.models.TipoentrenamientoModel;
import com.example.superfit.models.TiporutinaModel;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kotlin.text.Regex;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registro extends AppCompatActivity {
    //Pagina Actual nueva https://www.bsite.net/valerioparada/
    String PaginaWeb ="https://www.bsite.net/valerioparada/";
    //Variables globales
    AntropometriaModel antropometriaModel= new AntropometriaModel();
    ClientesModel cliente = new ClientesModel();
    CuestionarioModel cuestionario = new CuestionarioModel();
    MensualidadModel mensualidadModel= new MensualidadModel();
    Imagenes imagenes = new Imagenes();

    //Boton salir
    Button BtnSalir;

    //Botones de navegacion y para guardar
    Button BtnAtras,BtnAdelante,BtnGuardar;
    int pagactual = 1;
    //Ventanas layout a ocupar
    LinearLayout linerpersonal,linercuestionario,linermensualidad,linermedidas;
//contador de las imagenes que se seleccionan
    public int cuentaperfil=0,perfil=0,frontal=0,posterior=0;

    //Datos personales
    EditText Nombre,Ap,Am,Apodo,Edad,Telefono,Email,Contraseña;
    Spinner listsexo;
    String[] Sexo =  {"Hombre","Mujer"};
    //foto de perfil usuario
    ImageView imagenperfilusuario;
    Button fotoperfilususario;
    private int img_requestperfil = 21;
    private Bitmap bitmapfotousuarioperfil;
    LinearLayout linerfotousuario;

    //Cuestionario
    CheckBox Padece_enfermedad,lesiones,Fuma,Alcohol,Actividad_fisica;
    EditText Medicamento_prescrito_medico,Alguna_recomendacion_lesiones,Veces_semana_fuma,
            Veces_semana_alcohol,Tipo_ejercicios,Tiempo_dedicado,Horario_entreno,MetasObjetivos,
            Compromisos,Comentarios;
    TextView TipoejercicioL,TiempodedicadoL,HorarioentrenoL;
    LinearLayout liner;

    //Mensualidad
    ArrayList<String> tiporutinasapi = new ArrayList<String>();
    ArrayList<String> tipoentrenamientoapi = new ArrayList<String>();
    Spinner listtiporutinas,listtipoentrenamiento;

    //Medidas
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

    final Cargando cargando = new Cargando(Registro.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //Boton de salir
        BtnSalir=(Button)findViewById(R.id.salirbtn);
        //BOTONES NAVEGACION
        BtnAtras=(Button)findViewById(R.id.BotonAtras);
        BtnAdelante=(Button)findViewById(R.id.BotonSiguiente);
        BtnGuardar=(Button)findViewById(R.id.registrarcliente);

        //Diseños de cada ventana para cada tema
        linerpersonal =(LinearLayout)findViewById(R.id.DatosPersonalesLayout);
        linercuestionario=(LinearLayout)findViewById(R.id.CuestionarioLayout);
        linermensualidad=(LinearLayout)findViewById(R.id.MensualidadLayout);
        linermedidas=(LinearLayout)findViewById(R.id.MedidasLayout);

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
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_edittext_sexo,Sexo);
        listsexo.setAdapter(adapter);
        //imagen de usuario
        imagenperfilusuario=(ImageView)findViewById(R.id.FotoperfilId);
        fotoperfilususario=(Button)findViewById(R.id.FotoperfilBtn);
        linerfotousuario=(LinearLayout)findViewById(R.id.linerfotoperfilId);

        //Cuestionario
        liner = (LinearLayout)findViewById(R.id.LinerCuestionario);
        //Checkboxs de cuestionario
        Padece_enfermedad =(CheckBox)findViewById(R.id.Padece_enfermedadChk);
        lesiones =(CheckBox)findViewById(R.id.lesionesChk);
        Fuma =(CheckBox)findViewById(R.id.FumaChk);
        Alcohol =(CheckBox)findViewById(R.id.AlcoholChk);
        Actividad_fisica =(CheckBox)findViewById(R.id.Actividad_fisicaChk);

        //Edittext de cuestionario
        Medicamento_prescrito_medico =(EditText) findViewById(R.id.Medicamento_prescrito_medicoTxt);
        Alguna_recomendacion_lesiones =(EditText) findViewById(R.id.Alguna_recomendacion_lesionesTxt);
        Veces_semana_fuma =(EditText) findViewById(R.id.Veces_semana_fumaTxt);
        Veces_semana_alcohol =(EditText) findViewById(R.id.Veces_semana_alcoholTxt);
        Tipo_ejercicios =(EditText) findViewById(R.id.Tipo_ejerciciosTxt);
        Tiempo_dedicado =(EditText) findViewById(R.id.Tiempo_dedicadoTxt);
        Horario_entreno =(EditText) findViewById(R.id.Horario_entrenoTxt);
        MetasObjetivos =(EditText) findViewById(R.id.MetasObjetivosTxt);
        Compromisos =(EditText) findViewById(R.id.CompromisosTxt);
        Comentarios =(EditText) findViewById(R.id.ComentariosTxt);

        //TextView de cuestionario
        TipoejercicioL =(TextView)findViewById(R.id.TipoejercicioLabel);
        TiempodedicadoL =(TextView)findViewById(R.id.labelTiempo_dedicado);
        HorarioentrenoL =(TextView)findViewById(R.id.labelHorario_entreno);

        //Mensualidad
        listtiporutinas = (Spinner) findViewById(R.id.TiporutinaList);
        listtipoentrenamiento =(Spinner)findViewById(R.id.Tipoentrenamientolist);
        //Llenar combos de mensualidad
        GetTipoRutinas();
        GetTipoEntrenamiento();

        //Medidas
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

        //Botones de navegacion
        BtnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int siguiente = pagactual;
                if (siguiente > 1) {
                    siguiente--;
                    if (siguiente == 1) {
                        BtnAtras.setVisibility(View.INVISIBLE);
                        linerpersonal.setVisibility(View.VISIBLE);
                        linerpersonal.getLayoutParams().height =ViewGroup.LayoutParams.WRAP_CONTENT;
                        linerpersonal.requestLayout();
                        linercuestionario.setVisibility(View.INVISIBLE);
                        linercuestionario.getLayoutParams().height =0;
                        linercuestionario.requestLayout();
                    }
                    else if (siguiente ==2)
                    {
                        linercuestionario.setVisibility(View.VISIBLE);
                        linercuestionario.getLayoutParams().height =ViewGroup.LayoutParams.WRAP_CONTENT;
                        linercuestionario.requestLayout();
                        linermensualidad.setVisibility(View.INVISIBLE);
                        linermensualidad.getLayoutParams().height =0;
                        linermensualidad.requestLayout();
                    }
                    else {
                        BtnAdelante.setVisibility(View.VISIBLE);
                        BtnGuardar.setVisibility(View.INVISIBLE);
                        linermensualidad.setVisibility(View.VISIBLE);
                        linermensualidad.getLayoutParams().height =ViewGroup.LayoutParams.WRAP_CONTENT;
                        linermensualidad.requestLayout();
                        linermedidas.setVisibility(View.INVISIBLE);
                        linermedidas.getLayoutParams().height =0;
                        linermedidas.requestLayout();
                    }
                    pagactual = siguiente;
                }
            }
        });

        BtnAdelante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int siguiente = pagactual;
                if (siguiente < 4) {
                    siguiente++;
                    if (siguiente == 2) {
                        boolean result = ValidarCliente();
                        if (result == true) {
                            BtnAtras.setVisibility(View.VISIBLE);
                            linercuestionario.setVisibility(View.VISIBLE);
                            linercuestionario.getLayoutParams().height =ViewGroup.LayoutParams.WRAP_CONTENT;
                            linercuestionario.requestLayout();
                            linerpersonal.setVisibility(View.INVISIBLE);
                            linerpersonal.getLayoutParams().height =0;
                            linerpersonal.requestLayout();
                        }
                        else {
                            siguiente--;
                        }
                    }
                    else if (siguiente == 3) {
                        DatosCuestioario();
                        linermensualidad.setVisibility(View.VISIBLE);
                        linermensualidad.getLayoutParams().height =ViewGroup.LayoutParams.WRAP_CONTENT;
                        linermensualidad.requestLayout();
                        linercuestionario.setVisibility(View.INVISIBLE);
                        linercuestionario.getLayoutParams().height =0;
                        linercuestionario.requestLayout();

                    }
                    else {
                        DatosMensualidad();
                        BtnAdelante.setVisibility(View.INVISIBLE);
                        BtnGuardar.setVisibility(View.VISIBLE);
                        linermedidas.setVisibility(View.VISIBLE);
                        linermedidas.getLayoutParams().height =ViewGroup.LayoutParams.WRAP_CONTENT;
                        linermedidas.requestLayout();
                        linermensualidad.setVisibility(View.INVISIBLE);
                        linermensualidad.getLayoutParams().height =0;
                        linermensualidad.requestLayout();
                    }
                    pagactual = siguiente;
                }
            }
        });

        //boton de registro
        BtnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validacliente =ValidarCliente();
                if(validacliente==true){
                    boolean validamedidas = ValidarMedidas();
                    if(validamedidas==true){
                        cargando.cargardialogo();
                        RegistroCliente registro = new RegistroCliente();
                        registro.Cliente = new ClientesModel();
                        registro.Cuestionario = new CuestionarioModel();
                        registro.Mensualidad = new MensualidadModel();
                        registro.Medidas = new AntropometriaModel();
                        //Asignamos las imagenes
                        UpdateImagen();
                        //Asignamos valores
                        registro.Cliente = cliente;
                        registro.Medidas = antropometriaModel;
                        registro.Cuestionario = cuestionario;
                        registro.Mensualidad = mensualidadModel;
                        registro.Imagenes = imagenes;
                        Registrar(registro);

                    }
                }
            }
        });

        BtnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(Registro.this);
                builder.setMessage("¿Desea salir? se perdera el progreso")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Registro.this,MainActivity.class);
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
        });

      //checkbox para interactuar
        Padece_enfermedad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Padece_enfermedad.isChecked()==true){
                    Medicamento_prescrito_medico.setVisibility(View.VISIBLE);
                }
                else{
                    Medicamento_prescrito_medico.setVisibility(View.INVISIBLE);
                    Medicamento_prescrito_medico.setText("");
                }
            }
        });

        lesiones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lesiones.isChecked()==true){
                    Alguna_recomendacion_lesiones.setVisibility(View.VISIBLE);
                }
                else{
                    Alguna_recomendacion_lesiones.setVisibility(View.INVISIBLE);
                    Alguna_recomendacion_lesiones.setText("");
                }
            }
        });

        Fuma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Fuma.isChecked()==true){
                    Veces_semana_fuma.setVisibility(View.VISIBLE);
                }
                else{
                    Veces_semana_fuma.setVisibility(View.INVISIBLE);
                    Veces_semana_fuma.setText("");
                }
            }
        });

        Alcohol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Alcohol.isChecked()==true){
                    Veces_semana_alcohol.setVisibility(View.VISIBLE);
                }
                else{
                    Veces_semana_alcohol.setVisibility(View.INVISIBLE);
                    Veces_semana_alcohol.setText("");
                }
            }
        });

        Actividad_fisica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actividad();
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
        //perfil de la cuenta del usuario
        fotoperfilususario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,img_requestperfil);
                cuentaperfil=1;
                perfil=0;
                frontal=0;
                posterior=0;
            }
        });
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

    //Registro
    public void Registrar(RegistroCliente registro){
        // Job http://192.168.56.1:8081/
        // Home http://192.168.100.11:8081/
        // web superfit.somee.com
        //Primero registramos los datos
        Retrofit retrofit=new Retrofit.Builder().baseUrl(PaginaWeb)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ClienteApi clienteApi = retrofit.create(ClienteApi.class);
        Call<AlertasModel> call = clienteApi.RegistroCompleto(registro);
        call.enqueue(new Callback<AlertasModel>() {
            @Override
            public void onResponse(Call<AlertasModel> call, Response<AlertasModel> response) {
                try {
                    if(response.isSuccessful()){
                        AlertasModel result = response.body();
                        if(result.Result==true){
                            cargando.ocultar();
                            AlertDialog.Builder builder= new AlertDialog.Builder(Registro.this);
                            builder.setMessage("Registro exito ingresa a tu cuenta con tu correo eléctronico o numero celular")
                                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Registro.this,MainActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Registro.this,MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                            builder.show();
                        }
                        else {
                            cargando.ocultar();
                            Toast.makeText(Registro.this,result.Mensaje,Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        cargando.ocultar();
                        Toast.makeText(Registro.this,"No se realizo la conexion "+response.message(),Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex){
                    cargando.ocultar();
                    Toast.makeText(Registro.this,"Ocurrio un error: \r\n" +ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AlertasModel> call, Throwable t) {
                cargando.ocultar();
                Toast.makeText(Registro.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde \r\n Error:"+t.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    //subir imagenes en el registro
    public void UpdateImagen(){
        try
        {
            imagenes.ImagenPerfilCuenta ="";
            imagenes.ImagenPerfil ="";
            imagenes.ImagenFrontal ="";
            imagenes.ImagenPosterior ="";
            if(bitmapfotousuarioperfil!=null){
                ByteArrayOutputStream byteperfilcuenta = new ByteArrayOutputStream();
                bitmapfotousuarioperfil.compress(Bitmap.CompressFormat.JPEG,75,byteperfilcuenta);
                byte[] byte64cuenta =byteperfilcuenta.toByteArray();
                String base64cuenta = Base64.encodeToString(byte64cuenta,Base64.DEFAULT);
                imagenes.ImagenPerfilCuenta =base64cuenta;
            }
            if(bitmapfotousuarioperfilmedida!=null){
                ByteArrayOutputStream byteperfil = new ByteArrayOutputStream();
                bitmapfotousuarioperfilmedida.compress(Bitmap.CompressFormat.JPEG,75,byteperfil);
                byte[] byte64perfil =byteperfil.toByteArray();
                String base64perfil = Base64.encodeToString(byte64perfil,Base64.DEFAULT);
                imagenes.ImagenPerfil =base64perfil;
            }
            if(bitmapfotofrontal!=null){
                ByteArrayOutputStream bytefrontal = new ByteArrayOutputStream();
                bitmapfotofrontal.compress(Bitmap.CompressFormat.JPEG,75,bytefrontal);
                byte[] byte64frontal =bytefrontal.toByteArray();
                String base64frontal = Base64.encodeToString(byte64frontal,Base64.DEFAULT);
                imagenes.ImagenFrontal =base64frontal;
            }
            if(bitmapfotoposterior!=null){
                ByteArrayOutputStream byteposterior = new ByteArrayOutputStream();
                bitmapfotoposterior.compress(Bitmap.CompressFormat.JPEG,75,byteposterior);
                byte[] byte64posterior = byteposterior.toByteArray();
                String base64posterior= Base64.encodeToString(byte64posterior,Base64.DEFAULT);
                imagenes.ImagenPosterior =base64posterior;
            }
        }
        catch (Exception ex){
            Toast.makeText(Registro.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
            Log.w("Error VALERIO:",ex.getMessage());
        }
    }

    //para mostrar un diseño de si practica algun deporte en la seccion de cuestionario
    public void Actividad(){
        if(Actividad_fisica.isChecked()==true){
            liner.setVisibility(View.VISIBLE);
            liner.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            liner.requestLayout();
        }
        else{
            liner.setVisibility(View.INVISIBLE);
            liner.getLayoutParams().height =0;
            liner.requestLayout();
            Tipo_ejercicios.setText("");
            Tiempo_dedicado.setText("");
            Horario_entreno.setText("");
        }
    }

    //Cargar imagenes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode==img_requestperfil||requestCode==img_requestperfilmedida||requestCode==img_requestfrontal ||requestCode==img_requestposterior)
                &&resultCode==RESULT_OK && data !=null){
            Uri pathperfilcuenta = data.getData();
            Uri pathperfil = data.getData();
            Uri pathfrontal = data.getData();
            Uri pathposterior = data.getData();
            try {
                 if(cuentaperfil==1){
                    bitmapfotousuarioperfil = MediaStore.Images.Media.getBitmap(getContentResolver(),pathperfilcuenta);
                    imagenperfilusuario.setImageBitmap(bitmapfotousuarioperfil);
                    linerfotousuario.setVisibility(View.VISIBLE);
                     linerfotousuario.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                     linerfotousuario.requestLayout();
                }
                else if(perfil==1){
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


    //Consultas
    //Llenar los dropdown list o cobos
    public void GetTipoEntrenamiento() {

        Retrofit retrofit=new Retrofit.Builder().baseUrl(PaginaWeb)
                .addConverterFactory(GsonConverterFactory.create()).build();
        CatalogoApi catalogoapi = retrofit.create(CatalogoApi.class);
        Call<List<TipoentrenamientoModel>> call = catalogoapi.GetTypeTraining();
        call.enqueue(new Callback<List<TipoentrenamientoModel>>() {
            @Override
            public void onResponse(Call<List<TipoentrenamientoModel>> call, Response<List<TipoentrenamientoModel>> response) {
                try {
                    if(response.isSuccessful()){
                        List<TipoentrenamientoModel> tipoentrenamientolist = response.body();
                        if(tipoentrenamientolist.size()>0){
                            for (int i=0;i<tipoentrenamientolist.size();i++){
                                tipoentrenamientoapi.add(i+1+".- "+tipoentrenamientolist.get(i).Tipo_entrenamiento);
                            }
                            TipoEntrenamientos(tipoentrenamientoapi);
                        }
                        else{
                            tiporutinasapi.add("No hay rutinas");
                            TipoEntrenamientos(tipoentrenamientoapi);
                        }
                    }
                }
                catch (Exception ex){
                    Log.w("",ex.getMessage());
                    Toast.makeText(Registro.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TipoentrenamientoModel>> call, Throwable t) {
                Toast.makeText(Registro.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void GetTipoRutinas() {
        Retrofit retrofit=new Retrofit.Builder().baseUrl(PaginaWeb)
                .addConverterFactory(GsonConverterFactory.create()).build();
        CatalogoApi catalogoapi = retrofit.create(CatalogoApi.class);
        Call<List<TiporutinaModel>> call = catalogoapi.GetTypeRutines();
        call.enqueue(new Callback<List<TiporutinaModel>>() {
            @Override
            public void onResponse(Call<List<TiporutinaModel>> call, Response<List<TiporutinaModel>> response) {
                try {
                    if(response.isSuccessful()){
                        List<TiporutinaModel> tiporutinaslist = response.body();
                        if(tiporutinaslist.size()>0){
                            for (int i=0;i<tiporutinaslist.size();i++){
                                tiporutinasapi.add(i+1+".- "+tiporutinaslist.get(i).Descripcion);
                            }
                            TipoRutinas(tiporutinasapi);
                        }
                        else{
                            tiporutinasapi.add("No hay rutinas");
                            TipoRutinas(tiporutinasapi);
                        }
                    }
                }
                catch (Exception ex){
                    Log.w("",ex.getMessage());
                    Toast.makeText(Registro.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TiporutinaModel>> call, Throwable t) {
                Toast.makeText(Registro.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void TipoRutinas(ArrayList<String> Lista){
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_edittext_sexo,Lista);
        listtiporutinas.setAdapter(adapter);
    }

    public void TipoEntrenamientos(ArrayList<String> Lista){
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_edittext_sexo,Lista);
        listtipoentrenamiento.setAdapter(adapter);
    }
    //

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir? se perdera el progreso")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Registro.this,MainActivity.class);
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
            if(ValidarCelular(tel)){
                if(ValidarCorreo(em)){
                    String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
                    boolean valida=pass.matches(pattern);
                    if(pass.length()>=8){
                        if(valida==true){
                            cliente.Nombres =n.toUpperCase();
                            cliente.Apellido_paterno=ap.toUpperCase();
                            cliente.Apellido_materno=am.toUpperCase();
                            cliente.Apodo=apo;
                            cliente.Telefono= Double.parseDouble(tel);
                            cliente.Edad=Integer.parseInt(ed);
                            cliente.Correo_electronico=em;
                            cliente.Contraseña=pass;
                            cliente.Sexo=sex;
                            //Registrar(cliente);
                            result = true;
                        }
                        else{
                            Toast.makeText(Registro.this, "La contraseña debe tener caracteres combinados mayúsculas, minúsculas, números y caracteres especiales", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(Registro.this, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Registro.this, "La estructura del correo electrónico es incorrecta, debe ser (ejemplo@dominio.com)", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(Registro.this, "El numero de celular es incorrecto", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(Registro.this, "Complete todos los datos", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public Boolean ValidarCelular(String strNumber){
        Regex regex = new Regex("\\A[0-9]{7,10}\\z");
        boolean result = regex.matches(strNumber);
        if (result) {
            return true;
        }
        else {
            return false;
        }
    }
    public Boolean ValidarCorreo(String email){
        String expresion;
        expresion = "\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        if (email.matches(expresion))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void DatosCuestioario(){
        String fuma = Veces_semana_fuma.getText().toString();
        String alcohol = Veces_semana_alcohol.getText().toString();
        if(fuma.isEmpty()){
            fuma="0";
        }
        if(alcohol.isEmpty()){
            alcohol="0";
        }
        cuestionario.Cliente=new ClientesModel();
        cuestionario.Cliente.Id_cliente=0;
        cuestionario.Padece_enfermedad = Padece_enfermedad.isChecked();
        cuestionario.Medicamento_prescrito_medico = Medicamento_prescrito_medico.getText().toString();
        cuestionario.Lesiones = lesiones.isChecked();
        cuestionario.Alguna_recomendacion_lesiones = Alguna_recomendacion_lesiones.getText().toString();
        cuestionario.Fuma = Fuma.isChecked();
        cuestionario.Veces_semana_fuma = Integer.parseInt(fuma);
        cuestionario.Alcohol = Alcohol.isChecked();
        cuestionario.Veces_semana_alcohol =  Integer.parseInt(alcohol);
        cuestionario.Actividad_fisica = Actividad_fisica.isChecked();
        cuestionario.Tipo_ejercicios = Tipo_ejercicios.getText().toString();
        cuestionario.Tiempo_dedicado = Tiempo_dedicado.getText().toString();
        cuestionario.Horario_entreno = Horario_entreno.getText().toString();
        cuestionario.MetasObjetivos = MetasObjetivos.getText().toString();
        cuestionario.Compromisos = Compromisos.getText().toString();
        cuestionario.Comentarios = Comentarios.getText().toString();
    }

    public void DatosMensualidad(){
        int IdTipoRutina =listtiporutinas.getSelectedItemPosition();
        int IdTipoEntrenamiento =listtipoentrenamiento.getSelectedItemPosition();
        mensualidadModel.Cliente=new ClientesModel();
        mensualidadModel.TipoEntrenamiento=new TipoentrenamientoModel();
        mensualidadModel.Tiporutina=new TiporutinaModel();
        mensualidadModel.TipoEntrenamiento.Id_TipoEntrenamiento=IdTipoEntrenamiento+1;
        mensualidadModel.Tiporutina.Id_tiporutina=IdTipoRutina+1;
    }

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


            antropometriaModel.Mensualidad=new MensualidadModel();
            antropometriaModel.Mensualidad.Id_mensualidad=0;
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
            //RegistrarAntropometria(antropometriaModel);
        }
        else {
            Toast.makeText(Registro.this,"Complete todos los datos",Toast.LENGTH_SHORT).show();
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