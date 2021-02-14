package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Rutinas extends AppCompatActivity {

    Button Lunes,Martes,Miercoles,Jueves,Viernes,Sabado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutinas);

            Lunes =findViewById(R.id.Lunesbtn);
            Martes =findViewById(R.id.Martesbtn);
            Miercoles =findViewById(R.id.Miercolesbtn);
            Jueves =findViewById(R.id.Juevesbtn);
            Viernes =findViewById(R.id.Viernesbtn);
            Sabado =findViewById(R.id.Sabadobtn);

            Lunes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Rutinas.this,Sets.class);
                    startActivity(intent);
                }
            });
    }


}