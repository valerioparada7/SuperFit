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

public class Perfil extends AppCompatActivity {
    // Job http://192.168.56.1:8081/
    // Home http://192.168.100.11:8081/
    // Pagina http://superfit.somee.com/
    // Pagina nueva http://valerioparada7-001-site1.etempurl.com/
    //Pagina Actual nueva https://www.bsite.net/valerioparada/
    String PaginaWeb ="https://www.bsite.net/valerioparada/";
    TextView Nombresclientet,tiporutinat,estatusDescripciont,Tipo_entrenamientot,fechait,fechaft,cuestionariotxt;
    Button salirtbtn1,cuestionariobtn;
    ListView menulist;
    ImageView FotoPerfil;

    //Cuestionario
    /*CheckBox Padece_enfermedad,lesiones,Fuma,Alcohol,Actividad_fisica;
    EditText Medicamento_prescrito_medico,Alguna_recomendacion_lesiones,Veces_semana_fuma,
            Veces_semana_alcohol,Tipo_ejercicios,Tiempo_dedicado,Horario_entreno,MetasObjetivos,
            Compromisos,Comentarios;
    TextView TipoejercicioL,TiempodedicadoL,HorarioentrenoL;
    LinearLayout liner;*/

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
        estatusDescripciont=(TextView)findViewById(R.id.estatusmesi);
        salirtbtn1=(Button)findViewById(R.id.salirbtn);
        cuestionariobtn=(Button)findViewById(R.id.VerCuestionarioBtn);
        GetCliente();

        salirtbtn1.setOnClickListener(new View.OnClickListener() {
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

        cuestionariobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this,Cuestionario.class);
                startActivity(intent);
            }
        });
/*
        liner = (LinearLayout)findViewById(R.id.LinerCuestionario);
        //Checkboxs
        Padece_enfermedad =(CheckBox)findViewById(R.id.Padece_enfermedadChk);
        lesiones =(CheckBox)findViewById(R.id.lesionesChk);
        Fuma =(CheckBox)findViewById(R.id.FumaChk);
        Alcohol =(CheckBox)findViewById(R.id.AlcoholChk);
        Actividad_fisica =(CheckBox)findViewById(R.id.Actividad_fisicaChk);

        //editetexp
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

        //TextView
        TipoejercicioL =(TextView)findViewById(R.id.TipoejercicioLabel);
        TiempodedicadoL =(TextView)findViewById(R.id.labelTiempo_dedicado);
        HorarioentrenoL =(TextView)findViewById(R.id.labelHorario_entreno);

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
        });*/
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

        Nombresclientet.setText(cliente.toUpperCase());
        tiporutinat.setText(tiporutina);
        Tipo_entrenamientot.setText(tipoentreno);
        fechait.setText(fechai);
        fechaft.setText(fechaf);
        estatusDescripciont.setText(estatus);
        String Url_Imagen=PaginaWeb+fotoperfil;
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