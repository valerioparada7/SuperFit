package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

public class Rutinas extends AppCompatActivity {

    Button Lunes,Martes,Miercoles,Jueves,Viernes,Sabado;
    String diassemana[]= {"Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
    ListView listdias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutinas);
        listdias =(ListView)findViewById(R.id.ListDias);
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.spinner_edittext_sexo,diassemana);
        listdias.setAdapter(adapter);

        listdias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                position=position+2;
                Bundle extras = new Bundle();
                extras.putInt("IdDia",position);
                Intent intent = new Intent(Rutinas.this,Sets.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
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