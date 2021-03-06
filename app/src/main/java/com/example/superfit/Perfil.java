package com.example.superfit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.superfit.interfaces.ClienteApi;
import com.example.superfit.models.AlertasModel;
import com.example.superfit.models.Imagenes;
import com.example.superfit.models.MensualidadModel;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Perfil extends AppCompatActivity {
    // Job http://192.168.56.1:8081/
    // Home http://192.168.100.11:8081/
    // Pagina http://superfit.somee.com/
    // Pagina nueva http://valerioparada7-001-site1.etempurl.com/
    //Pagina Actual nueva https://www.bsite.net/valerioparada/
    String PaginaWeb ="https://www.bsite.net/valerioparada/";
    TextView Nombresclientet,tiporutinat,estatusDescripciont,Tipo_entrenamientot,fechait,fechaft,mes;
    Button menubtn1,cuestionariobtn,misrutinasbtn,mismensaulidadesbtn,rutinasrapidasbtn,alimentacionbtn,editarperfilbtn;
    ImageView FotoPerfil;
    Imagenes imagenes = new Imagenes();
    final Cargando cargando = new Cargando(Perfil.this);

    //Datos para actualizar la foto de perfil
    //foto de perfil usuario
    private ImageView ImagenPerfilmodallocal;
    Button fotoperfilususario;
    private Button actualizarfotobtn,cancelbtn;
    public int img_requestperfil = 21;
    public Bitmap bitmapfotousuarioperfil;
    public AlertDialog.Builder dialogbuilder;
    public AlertDialog dialog;

    //Datos para abrir el modal de pago
    private ImageView Imagenpago;
    Button fotopago;
    EditText monto,descripcion;
    private Button actualizarpago,cancelarpago;
    public int img_requetspago = 21;
    public Bitmap bitmappago;
    public AlertDialog.Builder dialogbuilderpago;
    public AlertDialog dialogpago;
    int cfotoperfil=0,cfotopago=0;
    int estatuspago=0;
    String Url_Imagen ="";

    public AlertDialog.Builder dialogbuildermenu;
    public AlertDialog dialogmenu;
    Button salirmodalseccionbtn,modalguiapagobtn,whatsappbtn1;

    public AlertDialog.Builder dialogbuilderguiapago;
    public AlertDialog dialogguiapago;
    TextView parrafoguia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Nombresclientet=(TextView)findViewById(R.id.Nombrecliente);
        FotoPerfil = (ImageView)findViewById(R.id.ImagenPerfil);
        tiporutinat=(TextView)findViewById(R.id.tiporutina);
        Tipo_entrenamientot=(TextView)findViewById(R.id.tipoentrenamiento);
        fechait=(TextView)findViewById(R.id.fechai);
        fechaft=(TextView)findViewById(R.id.fechaf);
        mes=(TextView)findViewById(R.id.NombreMeslb);
        estatusDescripciont=(TextView)findViewById(R.id.estatusmesi);
        menubtn1=(Button)findViewById(R.id.menubtn);
        cuestionariobtn=(Button)findViewById(R.id.VerCuestionarioBtn);
        misrutinasbtn=(Button)findViewById(R.id.MisrutinasBtn);
        mismensaulidadesbtn=(Button)findViewById(R.id.MensualidadesBtn);
        rutinasrapidasbtn=(Button)findViewById(R.id.RutinasrapidasBtn);
        alimentacionbtn=(Button)findViewById(R.id.DietasBtn);
        editarperfilbtn=(Button)findViewById(R.id.EditarPerfilBtn);
        GetCliente();

        //imagen de usuario
        fotoperfilususario=(Button)findViewById(R.id.ActualizarfotoBtn);

        //perfil de la cuenta del usuario
        fotoperfilususario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearModal();
            }
        });

        //imagen de pago
        fotopago=(Button)findViewById(R.id.ActualizarPagobtn);

        fotopago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ModalPago();
            }
        });

        editarperfilbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this,EditarPerfil.class);
                startActivity(intent);
            }
        });

        menubtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenMenu();
            }
        });

        cuestionariobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this,Cuestionario.class);
                startActivity(intent);
            }
        });

        misrutinasbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this,Rutinas.class);
                startActivity(intent);
            }
        });

        mismensaulidadesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this,Mensualidad_menu.class);
                startActivity(intent);
            }
        });

        rutinasrapidasbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Perfil.this,"Proximamente...",Toast.LENGTH_SHORT).show();
            }
        });

        alimentacionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Perfil.this,"Proximamente...",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void GetCliente(){
        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        int Idcliente =preferences.getInt("Idcliente",0);
        Retrofit retrofit=new Retrofit.Builder().baseUrl(PaginaWeb)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ClienteApi clienteApi = retrofit.create(ClienteApi.class);
        Call<MensualidadModel> call = clienteApi.GetMensualidad(Idcliente);
        call.enqueue(new Callback<MensualidadModel>() {
            @Override
            public void onResponse(Call<MensualidadModel> call, Response<MensualidadModel> response) {
                try {
                    if(response.isSuccessful()){
                        MensualidadModel M = response.body();
                        if(M!=null){
                            MostrarDatos(M);
                        }
                        else{
                            SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putInt("Idcliente",0);
                            editor.commit();
                            Intent intent = new Intent(Perfil.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }
                    else{
                        Toast.makeText(Perfil.this,"No se realizo correctamente la conexion",Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception ex){
                    Toast.makeText(Perfil.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MensualidadModel> call, Throwable t) {
                Toast.makeText(Perfil.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde \r\n Error:"+t.getCause().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void MostrarDatos(MensualidadModel  mensualidad){
        Nombresclientet.setText(mensualidad.Cliente.Nombres.toUpperCase());
        tiporutinat.setText(mensualidad.Tiporutina.Tipo);
        Tipo_entrenamientot.setText(mensualidad.TipoEntrenamiento.Tipo_entrenamiento);
        fechait.setText(mensualidad.Fechainicio);
        fechaft.setText(mensualidad.Fechafin);
        estatusDescripciont.setText(mensualidad.Estatus.Descripcion);
        mes.setText(mensualidad.Mes.Mes);

        Url_Imagen=PaginaWeb+mensualidad.Cliente.Foto_perfil;
        Glide.with(getApplication()).load(Url_Imagen).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache (true).into(FotoPerfil);
        if(mensualidad.Id_mensualidad!=0){
            SharedPreferences preferences = getSharedPreferences("Sesion", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("IdMensualidad",mensualidad.Id_mensualidad);
            editor.putInt("IdEstatus",mensualidad.Estatus.Id_estatus);
            estatuspago = mensualidad.PagoMes.Id_pago;
            editor.commit();
            if ((mensualidad.Estatus.Id_estatus == 2 || mensualidad.Estatus.Id_estatus == 3) && mensualidad.PagoMes.Id_pago != 0){
                fotopago.setText("");
            }
            if(mensualidad.Estatus.Id_estatus==1) {
                if (mensualidad.PagoMes.Id_pago != 0) {
                    fotopago.setText("Su pago esta en revisión");
                }
            }
        }
    }
    //Actualizar Imagen
    public void UpdateImagen(){
        ByteArrayOutputStream byteperfilcuenta = new ByteArrayOutputStream();
        bitmapfotousuarioperfil.compress(Bitmap.CompressFormat.JPEG,75,byteperfilcuenta);
        byte[] byte64cuenta = byteperfilcuenta.toByteArray();
        String base64cuenta = Base64.encodeToString(byte64cuenta,Base64.DEFAULT);
        imagenes.ImagenPerfilCuenta = base64cuenta;
        imagenes.ImagenPerfil="";
        imagenes.ImagenFrontal="";
        imagenes.ImagenPosterior="";
        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        int Idcliente =preferences.getInt("Idcliente",0);
        Retrofit retrofit=new Retrofit.Builder().baseUrl(PaginaWeb)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ClienteApi clienteApi = retrofit.create(ClienteApi.class);
        Call<Boolean> call = clienteApi.UpdateClienteFoto(imagenes,Idcliente);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                try {
                    if(response.isSuccessful()){
                        Boolean c = response.body();
                        if(c==true){
                            Toast.makeText(Perfil.this,"Foto actualizada",Toast.LENGTH_SHORT).show();
                            cargando.ocultar();
                            dialog.dismiss();
                            GetCliente();
                        }
                        else{
                            cargando.cargardialogo();
                            Toast.makeText(Perfil.this,"Ocurrio un error al actualizar la foto intente mas tarde",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        cargando.cargardialogo();
                        Toast.makeText(Perfil.this,"No se realizo correctamente la conexion",Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception ex){
                    cargando.cargardialogo();
                    Toast.makeText(Perfil.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                cargando.cargardialogo();
                Toast.makeText(Perfil.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde \r\n Error:"+t.getCause().toString(),Toast.LENGTH_SHORT).show();
            }
        });
        dialog.dismiss();
    }

    //Cargar imagen de perfil
    public void CrearModal(){
        dialogbuilder = new AlertDialog.Builder(this);
        final View conetent = getLayoutInflater().inflate(R.layout.modalfoto,null);
        ImagenPerfilmodallocal = (ImageView) conetent.findViewById(R.id.ImagenPerfilmodal);
        actualizarfotobtn =(Button)conetent.findViewById(R.id.AceptarUpdatebtn);
        cancelbtn=(Button)conetent.findViewById(R.id.Cancelarbtn);
        dialogbuilder.setView(conetent);
        dialog = dialogbuilder.create();
        dialog.show();
        cfotoperfil=1;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,img_requestperfil);
        actualizarfotobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargando.cargardialogo();
                UpdateImagen();
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              dialog.dismiss();
            }
        });


    }

    public void ModalPago(){
        if (estatuspago == 0) {
            dialogbuilderpago = new AlertDialog.Builder(this);
            final View conetent = getLayoutInflater().inflate(R.layout.comprobantepago, null);
            Imagenpago = (ImageView) conetent.findViewById(R.id.ImagenPagomodal);
            monto = (EditText) conetent.findViewById(R.id.MontoTxt);
            descripcion = (EditText) conetent.findViewById(R.id.DescricionTxt);
            actualizarpago = (Button) conetent.findViewById(R.id.AceptarpagoBtn);
            cancelarpago = (Button) conetent.findViewById(R.id.Cancelarpagobtn);
            dialogbuilderpago.setView(conetent);
            dialogpago = dialogbuilderpago.create();
            dialogpago.show();
            cfotopago = 1;
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, img_requetspago);
            actualizarpago.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String dinero = monto.getText().toString().trim();
                    String concepto = descripcion.getText().toString().trim();
                    if(!dinero.isEmpty()){
                        if(!concepto.isEmpty()){
                            cargando.cargardialogo();
                            double money = Double.parseDouble(dinero);
                            UpdateImagenPago(money, concepto);
                        }
                        else{
                            Toast.makeText(Perfil.this,"Completa todos los campos",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(Perfil.this,"Completa todos los campos",Toast.LENGTH_SHORT).show();
                    }

                }
            });

            cancelarpago.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogpago.dismiss();
                }
            });
        }
    }

    public void UpdateImagenPago(double Mont,String Desc){
        ByteArrayOutputStream byteperfilcuenta = new ByteArrayOutputStream();
        bitmappago.compress(Bitmap.CompressFormat.JPEG,75,byteperfilcuenta);
        byte[] byte64cuenta = byteperfilcuenta.toByteArray();
        String base64cuenta = Base64.encodeToString(byte64cuenta,Base64.DEFAULT);
        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        int Idcliente =preferences.getInt("Idcliente",0);
        int Idmes =preferences.getInt("IdMensualidad",0);
        Imagenes imag = new Imagenes();
        imag.ImagenPerfilCuenta = "";
        imag.ImagenPerfil=base64cuenta;
        imag.ImagenFrontal="";
        imag.ImagenPosterior="";
        Retrofit retrofit=new Retrofit.Builder().baseUrl(PaginaWeb)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ClienteApi clienteApi = retrofit.create(ClienteApi.class);
        Call<AlertasModel> call = clienteApi.PagoMes(imag, Idcliente,  Idmes,  Mont,  Desc);
        call.enqueue(new Callback<AlertasModel>() {
            @Override
            public void onResponse(Call<AlertasModel> call, Response<AlertasModel> response) {
                try {
                    if(response.isSuccessful()){
                        AlertasModel c = response.body();
                        if(c.Result==true){
                            Toast.makeText(Perfil.this,c.Mensaje,Toast.LENGTH_SHORT).show();
                            cargando.ocultar();
                            dialogpago.dismiss();
                            GetCliente();
                        }
                        else{
                            dialogpago.dismiss();
                            Toast.makeText(Perfil.this,c.Mensaje + "\nOcurrio un error al actualizar el pago intente mas tarde",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        dialogpago.dismiss();
                        Toast.makeText(Perfil.this,"No se realizo correctamente la conexion",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex){
                    dialogpago.dismiss();
                    Toast.makeText(Perfil.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AlertasModel> call, Throwable t) {
                dialogpago.dismiss();
                Toast.makeText(Perfil.this,"No se conecto al servidor verifique su conexion \r\nintente mas tarde \r\n Error:"+t.getCause().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void OpenMenu(){
        dialogbuildermenu = new AlertDialog.Builder(this);
        final View conetent = getLayoutInflater().inflate(R.layout.modalmenu,null);
        salirmodalseccionbtn = (Button) conetent.findViewById(R.id.salirbtn);
        modalguiapagobtn = (Button) conetent.findViewById(R.id.guiapagobtn);
        dialogbuildermenu.setView(conetent);
        dialogmenu = dialogbuildermenu.create();
        dialogmenu.show();
        salirmodalseccionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(Perfil.this);
                builder.setMessage("¿Desea cerrar sesión?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putInt("Idcliente",0);
                                editor.commit();
                                Intent intent = new Intent(Perfil.this,MainActivity.class);
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
        modalguiapagobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogmenu.dismiss();
                ModalGuiaPago();
            }
        });
    }

    public void ModalGuiaPago(){
        dialogbuilderguiapago = new AlertDialog.Builder(this);
        final View conetent = getLayoutInflater().inflate(R.layout.modelmeotdopago,null);
        parrafoguia = (TextView)conetent.findViewById(R.id.parrafomodalpago);
        whatsappbtn1 = (Button)conetent.findViewById(R.id.whatsappbtn);
        String linkwhatsapp = "https://api.whatsapp.com/send?phone=+524428802842&message=Hola";
        String linkfacebook = "https://www.facebook.com/";
        String narracion = "- Una vez que hayas creado tu cuenta y asigando tus datos, tu entrenador recibe los datos y comienza con el desarrollo de la rutina.\n" +
                "\n- Después de tu parte queda realizar el pago de la mensualidad que te corresponde al mes.\n" +
                "\n- Los metodos pueden ser depositos, transferencias a un numero de cuenta que se te proporcionara via whatsapp.\n" +
                "\n- Puedes enviar mensaje via whatsapp a travez del boton que esta a lo ultimo.\n" +
                "\n- Una vez realizado el pago tendras un comprobante de ese pago haciendo referencia a la mensualidad la cual tendras que subir por la plataforma ya siendo enviado atravez via whatsapp o en el apartado de mensualidades | subir comprobante.\n" +
                "\n- Subiras la foto y anotar una descripcion y el monto que pagaste.\n" +
                "\n- Ya por finalizar se corroboraran los datos para hacer su valides y en 1 dia a 3 habiles se te hara llegar tu rutina lo mas antes posible dependiendo la demanda de rutinas que esten entramite.\n" +
                "\n- Nota: cada mes sera realizado asi hasta que la plataforma tenga la automatizacion de pagos con tarjeta, contamos con tu comprension.\n" +
                "\n- Si tienes mas dudas contacta al soprte tecnico via correo o whatsapp y te atenderemos lo antes posible.\n" +
                "\n Correo eléctronico: soportebysuperfitvalerio@gmail.com";
        parrafoguia.setText(narracion);
        dialogbuilderguiapago.setView(conetent);
        dialogguiapago = dialogbuilderguiapago.create();
        dialogguiapago.show();
        whatsappbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Uri uri = Uri.parse("smsto:"+"4428802842");
                Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                intent.setPackage("com.whatsapp");
                startActivity(intent);*/
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(linkwhatsapp));
                startActivity(intent);

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode==img_requestperfil || requestCode==img_requetspago ) && resultCode==RESULT_OK && data !=null){
            Uri pathperfilcuenta = data.getData();
            Uri pathcuenta = data.getData();
            try {
                if(cfotoperfil==1){
                    bitmapfotousuarioperfil = MediaStore.Images.Media.getBitmap(getContentResolver(),pathperfilcuenta);
                    ImagenPerfilmodallocal.setImageBitmap(bitmapfotousuarioperfil);
                    cfotoperfil=0;
                }
                if(cfotopago==1){
                    bitmappago = MediaStore.Images.Media.getBitmap(getContentResolver(),pathcuenta);
                    Imagenpago.setImageBitmap(bitmappago);
                    cfotopago=0;
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        else{
            if(cfotoperfil==1){
                dialog.dismiss();
            }
            if(cfotopago==1){
                dialogpago.dismiss();
            }
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