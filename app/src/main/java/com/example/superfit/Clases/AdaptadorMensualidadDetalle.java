package com.example.superfit.Clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.superfit.R;

import java.util.ArrayList;

public class AdaptadorMensualidadDetalle extends BaseAdapter {
    public Context context;
    public ArrayList<MensualidadDetalle> listItems;

    public AdaptadorMensualidadDetalle(Context context, ArrayList<MensualidadDetalle> listItems) {
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
        MensualidadDetalle item=(MensualidadDetalle)getItem(position);

        TextView tipolabelm =(TextView)convertView.findViewById(R.id.Tipolabelm);
        TextView descripcionlabelm =(TextView)convertView.findViewById(R.id.Descripcionlabelm);
        TextView tipo_entrenamientolabelm =(TextView)convertView.findViewById(R.id.Tipo_entrenamientolabelm);
        TextView meslabelm =(TextView)convertView.findViewById(R.id.Meslabelm);
        TextView estatus_menusalidadlabelm =(TextView)convertView.findViewById(R.id.Estatus_menusalidadlabelm);
        TextView fechaIlabelm =(TextView)convertView.findViewById(R.id.FechaIlabelm);
        TextView fechafinlabelm =(TextView)convertView.findViewById(R.id.Fechafinlabelm);

        tipolabelm.setText(item.getTipo());
        descripcionlabelm.setText(item.getDescripcionTiporutina());
        tipo_entrenamientolabelm.setText(item.getTipo_entrenamiento());
        meslabelm.setText(item.getMes());
        estatus_menusalidadlabelm.setText(item.getDescripcionEstatus());
        fechaIlabelm.setText(item.getFecha_inicio());
        fechafinlabelm.setText(item.getFecha_fin());
        return convertView;
    }
}




