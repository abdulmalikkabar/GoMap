package com.example.malick.apk.Detail;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malick.apk.Picasso.PicassoClient;
import com.example.malick.apk.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class Detail_atm extends AppCompatActivity {

    FloatingActionButton rute;
    TextView nama,pecahan,jenis,alamat;
    ImageView gambar;



    String goolgeMap = "com.google.android.apps.maps"; // identitas package aplikasi google masps android
    Uri gmmIntentUri;
    Intent mapIntent;
    String koma=",";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_atm);


        gambar = (ImageView) findViewById(R.id.gambar);
        nama =(TextView)findViewById(R.id.nama);
        jenis =(TextView)findViewById(R.id.jenis);
        pecahan =(TextView)findViewById(R.id.pecahan);
        alamat =(TextView)findViewById(R.id.alamat);



        //Menerima data
        Intent i=this.getIntent();
        String nama1=i.getExtras().getString("nama");
        String alamat1=i.getExtras().getString("alamat");
        String pecahan1=i.getExtras().getString("pecahan");
        String jenis1=i.getExtras().getString("jenis");
        String Gambarurl=i.getExtras().getString("gambar");
        final String lat=i.getExtras().getString("lat");
        final String  lng=i.getExtras().getString("lng");


        //BIND
        // namaTxt.setText(nama);
        nama.setText(nama1);
        pecahan.setText(pecahan1);
        jenis.setText(jenis1);
        alamat.setText(alamat1);
        PicassoClient.downloadImage(this,Gambarurl, gambar);
        // thumb_image.setImageUrl(Gambar, imageLoader);

      //  materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);

        rute = (FloatingActionButton) findViewById(R.id.rute);

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
                    Toast.makeText(Detail_atm.this, "Google Maps Belum Terinstal. Install Terlebih dahulu.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });









    }
}
