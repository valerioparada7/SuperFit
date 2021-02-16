package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Perfil extends AppCompatActivity {
    TextView Nombresclientet,tiporutinat,estatusDescripciont,Tipo_entrenamientot,fechait,fechaft;
    Button Rutinasb;
    String arraymenu[]= {"Mis rutinas","Mensualidad","Medidas","Cuestionario","Alimentacion"};
    ListView menulist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Nombresclientet=(TextView)findViewById(R.id.Nombrecliente);
        tiporutinat=(TextView)findViewById(R.id.tiporutina);
        Tipo_entrenamientot=(TextView)findViewById(R.id.tipoentrenamiento);
        fechait=(TextView)findViewById(R.id.fechai);
        fechaft=(TextView)findViewById(R.id.fechaf);
        menulist =(ListView)findViewById(R.id.ListMenu);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraymenu);
        menulist.setAdapter(adapter);

        menulist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Intent intent= new Intent(Perfil.this,Rutinas.class);
                    startActivity(intent);
                }
                else if(position==1){
                    Toast.makeText(Perfil.this,"Hola",Toast.LENGTH_LONG);
                }
            }
        });
        GetCliente();
    }

    public void GetCliente(){
        SharedPreferences preferences =getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        String mensualidad=preferences.getString("mensualidad","No asignado");
        String cliente=preferences.getString("Nombrescliente","");
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

    }
}