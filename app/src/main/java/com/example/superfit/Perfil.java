package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Perfil extends AppCompatActivity {
    TextView nombre;
    Button Rutinasb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        nombre = findViewById(R.id.Nombrelabel);
        Rutinasb =findViewById(R.id.Rutinasbtn);

        nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Perfil.this,"Hola",Toast.LENGTH_LONG);
            }
        });
        Rutinasb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this,Rutinas.class);
                startActivity(intent);
            }
        });
    }
}