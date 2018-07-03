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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class Detail_Spbu extends AppCompatActivity {

    FloatingActionButton rute;
    private GoogleMap mMap;


    TextView kode,jenis,nama,alamat;
    ImageView gambar;

    String goolgeMap = "com.google.android.apps.maps"; // identitas package aplikasi google masps android
    Uri gmmIntentUri;
    Intent mapIntent;
    String koma=",";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__spbu);

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);



        gambar = (ImageView) findViewById(R.id.gambar);
        kode =(TextView)findViewById(R.id.kode);
        jenis =(TextView)findViewById(R.id.jenis);
        nama =(TextView)findViewById(R.id.nama);
        alamat =(TextView)findViewById(R.id.alamat);



        //Menerima data
        Intent i=this.getIntent();
        String kode1=i.getExtras().getString("kode");
        String jenis1=i.getExtras().getString("jenis");
        String nama1=i.getExtras().getString("nama");
        String alamat1=i.getExtras().getString("alamat");
        String Gambarurl=i.getExtras().getString("gambar");
        final String lat=i.getExtras().getString("lat");
        final String  lng=i.getExtras().getString("lng");



        //BIND
        // namaTxt.setText(nama);
        kode.setText("Kode Spbu : "+kode1);
        jenis.setText("Jenis Spbu : "+jenis1);
        nama.setText(nama1);
        alamat.setText(" "+alamat1);
        PicassoClient.downloadImage(this,Gambarurl, gambar);


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
                    Toast.makeText(Detail_Spbu.this, "Google Maps Belum Terinstal. Install Terlebih dahulu.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });


    }

//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//        nama =(TextView)findViewById(R.id.nama);
//        mMap = googleMap;
//        Intent i=this.getIntent();
//        final String lat=i.getExtras().getString("lat");
//        final String  lng=i.getExtras().getString("lng");
//
//        String nama1=i.getExtras().getString("nama");
//
//
//        nama.setText(nama1);
//        // Add a marker in Sydney and move the camera
//        LatLng marker = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
//
//        if (nama1.equals("SPBU UBAH")){
//
//            mMap.addMarker(new MarkerOptions().position(marker).title(nama1)).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_rute));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
//
//        }
//
//
//    }
//
//
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
}
