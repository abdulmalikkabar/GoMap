package com.example.malick.apk.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.malick.apk.Detail.Detail_Bank;
import com.example.malick.apk.Picasso.PicassoClient;
import com.example.malick.apk.R;
import com.example.malick.apk.UI.Bank;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Malick on 3/19/2018.
 */

public class Adapter_Bank extends RecyclerView.Adapter<Adapter_Bank.ViewHolder> {


    //vars
    private ArrayList<Bank> banks;
    private Activity activity;

    public Adapter_Bank(Activity activity, List<Bank> banks) {
        this.activity = activity;
        this.banks = (ArrayList<Bank>) banks;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_bank, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(Adapter_Bank.ViewHolder holder, final int position) {

        final Bank m = banks.get(position);


        holder.nama.setText(m.getNama());
        holder.jarak.setText(m.getJarak() + " Km");
        holder.kode.setText(m.getKode());


        PicassoClient.downloadImage(activity,m.getGambar(),holder.gambar);



        holder.gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDetailActivity(m.getKode(),m.getCabang(),m.getNama(),m.getTelp(),m.getAlamat(),m.getWeb(),m.getLat(),m.getIng(),m.getGambar());

            }
        });


    }

    private void openDetailActivity(String kode, String cabang, String nama, String telp, String alamat, String web, String lat, String ing, String gambar) {

        Intent i = new Intent(activity,Detail_Bank.class);
        //data

        i.putExtra("kode_bank", kode);
        i.putExtra("cabang", cabang);
        i.putExtra("nama_bank",nama);
        i.putExtra("telp",telp);
        i.putExtra("alamat",alamat);
        i.putExtra("web",web);
        i.putExtra("lat",lat);
        i.putExtra("lng",ing);
        i.putExtra("gambar",gambar);


        activity.startActivity(i);

    }


    @Override
    public int getItemCount() {
        return banks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView gambar;
        TextView nama,jarak,kode;



        public ViewHolder(View itemView) {
            super(itemView);
            gambar = itemView.findViewById(R.id.gambar);
            nama = itemView.findViewById(R.id.nama);
            jarak = itemView.findViewById(R.id.jarak);
            kode = itemView.findViewById(R.id.kode);

        }
    }
}
