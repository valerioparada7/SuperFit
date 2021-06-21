package com.example.superfit;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class Cargando {
    Activity activity;
    AlertDialog dialog;

    public Cargando(Activity activity) {
        this.activity = activity;
    }

    public void cargardialogo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.iconoespera,null));
        builder.setCancelable(true);

        dialog= builder.create();
        dialog.show();
    }

    public void ocultar(){
        dialog.dismiss();
    }

}
