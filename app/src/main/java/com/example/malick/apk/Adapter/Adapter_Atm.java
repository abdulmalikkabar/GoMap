package com.example.malick.apk.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malick.apk.Detail.Detail_Bank;
import com.example.malick.apk.Detail.Detail_atm;
import com.example.malick.apk.Picasso.PicassoClient;
import com.example.malick.apk.R;
import com.example.malick.apk.UI.Atm;
import com.example.malick.apk.UI.Bank;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Malick on 3/16/2018.
 */

public class Adapter_Atm extends RecyclerView.Adapter<Adapter_Atm.ViewHolder> {


    //vars
    private ArrayList<Atm> atms;
    private Activity activity;

    public Adapter_Atm(Activity activity, List<Atm> atms) {
        this.activity = activity;
        this.atms = (ArrayList<Atm>) atms;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_atm, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Atm m = atms.get(position);
        holder.name.setText(m.getNama());
        holder.jarak.setText(m.getJarak() + " Km");
        holder.jenis.setText(m.getJenis());
        holder.pecahan.setText(m.getPecahan());


        PicassoClient.downloadImage(activity,m.getGambar(),holder.image);





        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDetailActivity(m.getNama(),m.getAlamat(),m.getJenis(),m.getPecahan(),m.getIng(),m.getLat(),m.getGambar());
            }
        });


    }

    private void openDetailActivity(String nama, String alamat, String jenis, String pecahan, String ing, String lat, String Gambar) {

        Intent i = new Intent(activity,Detail_atm.class);
        //data

        i.putExtra("nama", nama);
        i.putExtra("alamat", alamat);
        i.putExtra("pecahan",pecahan);
        i.putExtra("jenis",jenis);
        i.putExtra("gambar",Gambar);
        i.putExtra("lat",lat);
        i.putExtra("lng",ing);


        activity.startActivity(i);

    }




    @Override
    public int getItemCount() {
        return atms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView name,jarak,jenis,pecahan;


        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.gambar);
            name = itemView.findViewById(R.id.nama);
            jarak = itemView.findViewById(R.id.jarak);
            jenis = itemView.findViewById(R.id.jenis);
            pecahan = itemView.findViewById(R.id.pecahan);
        }
    }
}
