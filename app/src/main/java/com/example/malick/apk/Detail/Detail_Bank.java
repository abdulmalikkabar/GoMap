package com.example.malick.apk.Detail;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malick.apk.Picasso.PicassoClient;
import com.example.malick.apk.R;
import com.github.clans.fab.FloatingActionButton;

public class Detail_Bank extends AppCompatActivity {

    FloatingActionButton telpon,website;
    ImageView rute;


    TextView kode,cabang,nama,alamat;
    ImageView gambar;

    String goolgeMap = "com.google.android.apps.maps"; // identitas package aplikasi google masps android
    Uri gmmIntentUri;
    Intent mapIntent;
    String koma=",";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__bank);


        gambar = (ImageView) findViewById(R.id.gambar);
        kode =(TextView)findViewById(R.id.kode);
        cabang =(TextView)findViewById(R.id.cabang);
        nama =(TextView)findViewById(R.id.nama);
        alamat =(TextView)findViewById(R.id.alamat);



        //Menerima data
        Intent i=this.getIntent();
        String kode1=i.getExtras().getString("kode_bank");
        String cabang1=i.getExtras().getString("cabang");
        String nama1=i.getExtras().getString("nama_bank");
        String alamat1=i.getExtras().getString("alamat");
        String Gambarurl=i.getExtras().getString("gambar");
        final String lat=i.getExtras().getString("lat");
        final String  lng=i.getExtras().getString("lng");


        final String telpon2=i.getExtras().getString("telp");
        final String web1=i.getExtras().getString("web");





        //BIND
        // namaTxt.setText(nama);
        kode.setText("Kode Bank : "+kode1);
        cabang.setText("Cabang : "+cabang1);
        nama.setText(nama1);
        alamat.setText(" "+alamat1);
        PicassoClient.downloadImage(this,Gambarurl, gambar);
        // thumb_image.setImageUrl(Gambar, imageLoader);




        //  materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        telpon = (FloatingActionButton) findViewById(R.id.telpon);
        website = (FloatingActionButton) findViewById(R.id.web);
        rute = (ImageView) findViewById(R.id.rute);

        telpon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+telpon2));
                startActivity(intent);



            }
        });



        website.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://"+web1));
                startActivity(intent);



            }
        });




        rute.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                // Buat Uri dari intent string. Gunakan hasilnya untuk membuat Intent.
                gmmIntentUri = Uri.parse("google.navigation:q=" + lat + koma + lng);
                mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage(goolgeMap);
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(Detail_Bank.this, "Google Maps Belum Terinstal. Install Terlebih dahulu.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });









    }
}
