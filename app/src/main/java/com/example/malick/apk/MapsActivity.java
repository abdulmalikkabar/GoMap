package com.example.malick.apk;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.malick.apk.App.AppController;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    MarkerOptions markerOptions = new MarkerOptions();
    CameraPosition cameraPosition;
    LatLng center, latLng;
    String title;

    public static final String id_atm = "id_atm";
    public static final String nama_bank = "nama_bank";
    public static final String alamat = "alamat";
    public static final String jenis = "jenis";
    public static final String pecahan = "pecahan";
    public static final String lat = "lat";
    public static final String lng = "lng";
    public static final String gambar = "gambar";

    //  private String url = "http://wisatademak.dedykuncoro.com/Main/json_wisata";
    private String url = "https://gomaps.000webhostapp.com/json/marker.php";

    String tag_json_obj = "json_obj_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //gMap = googleMap;

        // Mengarahkan ke
        center = new LatLng(-0.94924, 100.35427);
        cameraPosition = new CameraPosition.Builder().target(center).zoom(10).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        getMarkers();




    }

    private void addMarker(LatLng latlng, final String title) {
        markerOptions.title(title);
        markerOptions.position(latlng);

        if(title.equals("BNI"))
          //  markerOptions.title(title).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_test));
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_test));

        else if (title.equals("BRI"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_test2));
            // mMap.addMarker(markerOptions().title(title).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_test2)));
           // mMap.addMarker(markerOptions);
            //markerOptions.title(title).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_test2));
            // markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_test2));

        else if (title.equals("Bank Danamon"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_test3));
                // mMap.addMarker(markerOptions().title(title).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_test2)));
               // mMap.addMarker(markerOptions);
               // markerOptions.title(title).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_test3));
                // markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_test2));

        else if (title.equals("Bank Mandiri"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_test4));
            // mMap.addMarker(markerOptions().title(title).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_test2)));
          //  mMap.addMarker(markerOptions);
            //markerOptions.title(title).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_test4));
            // markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_test2));

        else
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_test5));
           // mMap.addMarker(markerOptions);
            //markerOptions.title(title).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_test5));
            //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_test3));


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });



    }


    // Fungsi get JSON marker
    private void getMarkers() {
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String getObject = jObj.getString("tb_atm");
                    JSONArray jsonArray = new JSONArray(getObject);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        title = jsonObject.getString(nama_bank);
                        latLng = new LatLng(Double.parseDouble(jsonObject.getString(lat)), Double.parseDouble(jsonObject.getString(lng)));

                        // Menambah data marker untuk di tampilkan ke google map
                        addMarker(latLng, title);


                    }


                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                Toast.makeText(MapsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);


    }


}
