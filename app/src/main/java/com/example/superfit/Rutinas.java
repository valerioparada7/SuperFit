package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Rutinas extends AppCompatActivity {

    Button Lunes,Martes,Miercoles,Jueves,Viernes,Sabado;
    String diassemana[]= {"Lunes","Martes","Medidas","Cuestionario","Alimentacion"};
    ListView listdias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutinas);
        listdias =(ListView)findViewById(R.id.ListDias);


        Bundle extras = new Bundle();
        extras.putInt("IdDia",2);
        Intent intent = new Intent(Rutinas.this,Sets.class);
        intent.putExtras(extras);
        startActivity(intent);


    }


}