package com.example.superfit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Perfil extends AppCompatActivity {
    TextView Nombresclientet,tiporutinat,estatusDescripciont,Tipo_entrenamientot,fechait,fechaft;
    Button salirtbtn;
    String arraymenu[]= {"Mis rutinas","Mi Mensualidad","Mis Medidas","Cuestionario","Sobre Alimentacion","Rutinas extras"};
    ListView menulist;
    ImageView FotoPerfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Nombresclientet=(TextView)findViewById(R.id.Nombrecliente);
        FotoPerfil =(ImageView)findViewById(R.id.ImagenPerfil);
        tiporutinat=(TextView)findViewById(R.id.tiporutina);
        Tipo_entrenamientot=(TextView)findViewById(R.id.tipoentrenamiento);
        fechait=(TextView)findViewById(R.id.fechai);
        fechaft=(TextView)findViewById(R.id.fechaf);
        menulist =(ListView)findViewById(R.id.ListMenu);
        salirtbtn =(Button)findViewById(R.id.CerrarSesion);
        GetCliente();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_edittext_sexo, arraymenu);
        menulist.setAdapter(adapter);
        menulist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Intent intent= new Intent(Perfil.this,Rutinas.class);
                    startActivity(intent);
                }
                else if(position==1){
                    Intent intent= new Intent(Perfil.this,Mensualidad_menu.class);
                    startActivity(intent);
                }
            }
        });

        salirtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(Perfil.this);
                builder.setMessage("¿Desea cerrar sesión?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=preferences.edit();

                                editor.putString("UsuarioCliente","");
                                editor.putString("ContraseñaCliente","");
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
    }

    public void GetCliente(){
        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        String mensualidad=preferences.getString("mensualidad","No asignado");
        String cliente=preferences.getString("Nombrescliente","");
        String fotoperfil=preferences.getString("FotoCliente","");
        String tiporutina=preferences.getString("tiporutina","No asignado");
        String estatus=preferences.getString("estatusDescripcion","No asignado");
        String tipoentreno=preferences.getString("Tipo_entrenamiento","No asignado");
        String fechai=preferences.getString("fechai","No hay fecha asiganda");
        String fechaf=preferences.getString("fechaf","No hay fecha asiganda");
        Nombresclientet.setText(cliente);
        tiporutinat.setText(tiporutina);
        Tipo_entrenamientot.setText(tipoentreno);
        fechait.setText(fechai);
        fechaft.setText(fechaf);
        //web superfit.somee.com
        //http://192.168.56.1:8081/
        //String Url_Imagen="http://192.168.56.1:8081"+fotoperfil;
        String Url_Imagen="http://superfit.somee.com"+fotoperfil;
        Log.w("FOTO AQUI-->", Url_Imagen);
        Glide.with(getApplication()).load(Url_Imagen).into(FotoPerfil);
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