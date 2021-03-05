package com.example.superfit.Clases;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.superfit.R;

import java.util.ArrayList;

public class AdaptadorEjercicios extends BaseAdapter {
    public Context context;
    public ArrayList<Ejercicios> listItems;

    public AdaptadorEjercicios(Context context, ArrayList<Ejercicios> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Ejercicios item = (Ejercicios) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.activity_ejercicios,null);
        ImageView imgfotos =(ImageView) convertView.findViewById(R.id.Imgfoto);
        TextView titulo =(TextView)convertView.findViewById(R.id.Ejerciciolabel);
        TextView descripcion =(TextView)convertView.findViewById(R.id.Descripcionejercicio);
        TextView repeti =(TextView)convertView.findViewById(R.id.Repeticioneslb);
        TextView serie =(TextView)convertView.findViewById(R.id.Serieslb);

        Glide.with(context).load(item.getImgfoto()).into(imgfotos);
        titulo.setText(item.getTituloEjercicio());;
        descripcion.setText(item.getDescripcionEjercicio());;
        repeti.setText("Repeticiones "+item.getRepeticiones());
        serie.setText("Series "+item.getSeries());
        return convertView;
    }

}
