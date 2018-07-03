package com.example.malick.apk.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.malick.apk.Detail.Detail_Spbu;
import com.example.malick.apk.Detail.Detail_atm;
import com.example.malick.apk.Picasso.PicassoClient;
import com.example.malick.apk.R;
import com.example.malick.apk.UI.Spbu;

import java.util.List;

/**
 * Created by Malick on 3/27/2018.
 */

public class Adapter_Spbu  extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Spbu> spbus;
    //  ImageLoader imageLoader;

    public Adapter_Spbu(Activity activity, List<Spbu> spbus) {
        this.activity = activity;
        this.spbus = spbus;
    }

    @Override
    public int getCount() {
        return spbus.size();
    }

    @Override
    public Object getItem(int location) {
        return spbus.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.model_spbu, null);


        //<de.hdodenhof.circleimageview.CircleImageView
        ImageView gambar=(ImageView) convertView.findViewById(R.id.gambar);
        TextView nama = (TextView) convertView.findViewById(R.id.nama);
        TextView kode = (TextView) convertView.findViewById(R.id.kode);
        TextView jarak = (TextView) convertView.findViewById(R.id.jarak);

        final Spbu m = spbus.get(position);

        //thumbNail.setImageUrl(m.getGambar(), imageLoader);
        PicassoClient.downloadImage(activity,m.getGambar(),gambar);
        nama.setText(m.getNama_spbu());
        kode.setText(m.getKode_spbu());
        jarak.setText(m.getJarak() + " Km");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // openDetailActivity(m.getNm_masjid(),m.getAlamat(), m.getGambar(),m.getLat(),m.getLng(),m.getKet());
                //openDetailActivity(m.getNama_atm(),m.getAlamat(),m.getKet(),m.getGambar(),m.getLat(),m.getIng());
   openDetailActivity(m.getNama_spbu(), m.getAlamat(), m.getKode_spbu(), m.getJenis(), m.getGambar(), m.getLat(), m.getLng());




            }
        });

        return convertView;
    }

    private void openDetailActivity(String nama_spbu, String alamat, String kode_spbu, String jenis, String gambar, String lat, String lng) {

        Intent i = new Intent(activity,Detail_Spbu.class);
        //data

        i.putExtra("nama", nama_spbu);
        i.putExtra("alamat", alamat);
        i.putExtra("kode",kode_spbu);
        i.putExtra("jenis",jenis);
        i.putExtra("gambar", gambar);
        i.putExtra("lat",lat);
        i.putExtra("lng",lng);


        activity.startActivity(i);

    }
}


