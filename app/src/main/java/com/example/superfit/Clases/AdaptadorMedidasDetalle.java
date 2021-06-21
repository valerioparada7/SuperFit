package com.example.superfit.Clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.superfit.R;
import com.example.superfit.models.AntropometriaModel;

import java.util.ArrayList;

public class AdaptadorMedidasDetalle extends BaseAdapter {
    public Context context;
    public ArrayList<Antropometria> listItems;

    public AdaptadorMedidasDetalle(Context context, ArrayList<Antropometria> listItems) {
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
       Antropometria  item = (Antropometria) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.activity_mensualidadmedidasdetalle,null);
        ImageView imgfrontal =(ImageView) convertView.findViewById(R.id.ImagenFrontal);
        ImageView imgperfil =(ImageView) convertView.findViewById(R.id.ImagenPerfilmedida);
        ImageView imgposterior =(ImageView) convertView.findViewById(R.id.ImagenPosterior);
        TextView fecha =(TextView)convertView.findViewById(R.id.Fecharabelm);
        TextView altura =(TextView)convertView.findViewById(R.id.Alturalabelm);
        TextView peso =(TextView)convertView.findViewById(R.id.Pesolabelm);
        TextView imc =(TextView)convertView.findViewById(R.id.IMClabelm);
        TextView bdr =(TextView)convertView.findViewById(R.id.Brazoderechorelajadolabelm);

        TextView bdf =(TextView)convertView.findViewById(R.id.Brazoderechofuerzalabelm);
        TextView bir =(TextView)convertView.findViewById(R.id.Brazoizquierdorelajadolabelm);
        TextView bif =(TextView)convertView.findViewById(R.id.Brazoizquierdofuerzalabelm);
        TextView cintura =(TextView)convertView.findViewById(R.id.Cinturalabelm);

        TextView cadera =(TextView)convertView.findViewById(R.id.Caderalabelm);
        TextView piernad =(TextView)convertView.findViewById(R.id.Piernaderechalabelm);
        TextView piernai =(TextView)convertView.findViewById(R.id.Piernaizquierdalabelm);
        TextView pantorrillad =(TextView)convertView.findViewById(R.id.Pantorrilladerechalabelm);
        TextView pantorrillai =(TextView)convertView.findViewById(R.id.Pantorrillaizquierdalabelm);


        Glide.with(context).load(item.getFotofrontal()).into(imgfrontal);
        Glide.with(context).load(item.getFotoperfil()).into(imgperfil);
        Glide.with(context).load(item.getFotoposterior()).into(imgposterior);

        fecha.setText(item.getFecharegistro());
        altura.setText(String.valueOf(item.getAltura()));
        peso.setText(String.valueOf(item.getPeso()));
        imc.setText(String.valueOf(item.getIMC()));
        bdr.setText(String.valueOf(item.getBrazoderechorelajado()));
        bdf.setText(String.valueOf(item.getBrazoderechofuerza()));
        bir.setText(String.valueOf(item.getBrazoizquierdorelajado()));
        bif.setText(String.valueOf(item.getBrazoizquierdofuerza()));
        cintura.setText(String.valueOf(item.getCintura()));
        cadera.setText(String.valueOf(item.getCadera()));
        piernad.setText(String.valueOf(item.getPiernaderecho()));
        piernai.setText(String.valueOf(item.getPiernaizquierda()));
        pantorrillad.setText(String.valueOf(item.getPantorrilladerecha()));
        pantorrillai.setText(String.valueOf(item.getPantorrillaizquierda()));

        return convertView;
    }
}
